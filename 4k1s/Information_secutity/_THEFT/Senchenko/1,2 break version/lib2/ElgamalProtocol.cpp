#include <fstream>
#include <iostream>
#include "../include/lib1.h"
#include "../include/lib3.h"
#include "../include/ElgamalProtocol.h"

#include <cryptopp/cryptlib.h>
#include <cryptopp/files.h>     // for FileSource
#include <cryptopp/channels.h>  // for ChannelSwitch
#include <cryptopp/filters.h>   // for HashFilter, StringSink
#include <cryptopp/hex.h>       // for HexEncoder, StringSink
#include <cryptopp/sha3.h>      // for SHA3_256
#include <cryptopp/sha.h>

#include <gmp.h>

ElgamalProtocol::ElgamalProtocol(const char* key_pub)
{
    uint64_t p, q, g;
    
    do {
        gen_pqg(&p, &q, &g);
    } while (p <= 0xffffff);

    init(p, g, key_pub);
}

ElgamalProtocol::ElgamalProtocol(uint64_t p, uint64_t g, const char* key_pub)
{
    init(p, g, key_pub);
}

void ElgamalProtocol::init(uint64_t _p, uint64_t _g, const char* key_pub)
{
    this->p = _p;
    this->g = _g;

    if (this->p != _p)
        std::cout << "Error" << std::endl;

    this->x = gen_x(p);
    this->y = fem(this->g, this->x, this->p);

    std::ofstream output(key_pub, std::ios::trunc);
    if (!output) {
        std::cerr << "Error: unable to open input file: " << key_pub << std::endl;
    }

    output << this->y;
    output.close();
}

int ElgamalProtocol::file_signatur(const char* file)
{
    uint64_t r, k;
    std::string digest_str = get_digest_str(file);

    gen_kr_for_signature(&r, &k, p);
    std::ofstream output("r.pub", std::ios::binary | std::ios::trunc);
    output << r;
    output.close();

    encrypt_signature(digest_str, r, k);
}

int ElgamalProtocol::signature_verification(const char* file, const char* file_signature,
                            const char* r_pub, const char* key_pub)
{
    uint64_t y, r;
    get_pub_key(&y, key_pub);
    get_pub_key(&r, r_pub);

    std::string digest_str = get_digest_str(file);
    std::string signature = get_signature_str(file_signature);

    decrypt_signature(signature, digest_str, y, r);

    return 0;
}

int ElgamalProtocol::encrypt_signature(std::string digest_str, uint64_t r, uint64_t k)
{
    const char* digest = digest_str.c_str();
    const char* ptr;
    uint64_t* part_ptr;
    uint64_t part, s, _k;

    mpz_t tmp, big_u, big_p, big__k;

    mpz_inits(tmp, big_u, big__k, NULL);
    mpz_init_set_ui(big_p, p - 1);

    mpz_import(tmp, 1, -1, sizeof(x), 0, 0, &x);
    mpz_mul_ui(tmp, tmp, r);

    _k = inversion(k, p - 1);
    mpz_import(big__k, 1, -1, sizeof(_k), 0, 0, &_k);

    std::ofstream out("digest", std::ios::binary | std::ios::trunc);

    for (int i = 0; i < 32; i += 3) {
        part_ptr = (uint64_t*) ((uint32_t*) &digest[i]);
        part = *part_ptr & 0xffffff;

        mpz_import(big_u, 1, -1, sizeof(part), 0, 0, &part);
        
        mpz_sub(big_u, big_u, tmp);
        mpz_mod(big_u, big_u, big_p);

        mpz_mul(big_u, big_u, big__k);
        mpz_mod(big_u, big_u, big_p);
        mpz_export(&s, 0, -1, sizeof(s), 0, 0, big_u);

        ptr = (const char*) &s;
        out.write(reinterpret_cast<char*>(&s), 4);
    }

    out.close();

    mpz_inits(tmp, big_u, big__k, big_p, NULL);
}

int ElgamalProtocol::decrypt_signature(std::string signature, std::string digest_str, uint64_t y, uint64_t r)
{
    const char* signature_char = signature.c_str();
    const char* digest_char = digest_str.c_str();
    uint64_t *part_ptr, *part_digest_ptr;
    uint64_t part, part_digest;

    mpz_t mul1, mul2;
    mpz_t big_y, big_r, big_p, big_g;
    mpz_t big_part, big_part_digest;
    mpz_t big_expected, big_result;

    mpz_inits(big_expected, big_result, big_part_digest, big_part,
                big_y, big_r, big_p, big_g, mul1, mul2, NULL);

    mpz_import(big_p, 1, -1, sizeof(p), 0, 0, &p);
    mpz_import(big_y, 1, -1, sizeof(y), 0, 0, &y);
    mpz_import(big_r, 1, -1, sizeof(r), 0, 0, &r);
    mpz_import(big_g, 1, -1, sizeof(g), 0, 0, &g);

    for (int i = 0, j = 0; i < 44; i += 4, j += 3) {
        part_ptr = (uint64_t*) ((uint32_t*) &signature_char[i]);
        part_digest_ptr = (uint64_t*) ((uint32_t*) &digest_char[j]);
        part = *part_ptr & 0xffffffff;
        part_digest = *part_digest_ptr & 0xffffff;

        my_mpz_fem(big_y, big_r, big_p, mul1);

        mpz_import(big_part, 1, -1, sizeof(part), 0, 0, &part);
 
        my_mpz_fem(big_r, big_part, big_p, mul2);

        mpz_mul(mul1, mul1, mul2);
        mpz_mod(big_result, mul1, big_p);

        mpz_import(big_part_digest, 1, -1, sizeof(part_digest), 0, 0, &part_digest);        
        my_mpz_fem(big_g, big_part_digest, big_p, big_expected);

        if (mpz_cmp(big_result, big_expected) != 0) {
            std::cout << "Signature does not match" << std::endl;
            mpz_clears(big_expected, big_result, big_part_digest, big_part,
                big_y, big_r, big_p, big_g, mul1, mul2, NULL);
            return -1;
        }
    }

    mpz_clears(big_expected, big_result, big_part_digest, big_part,
                big_y, big_r, big_p, big_g, mul1, mul2, NULL);

    std::cout << "Ok" << std::endl;

    return 0;
}

std::string ElgamalProtocol::get_digest_str(const char* file)
{
    std::string digest_str;
    CryptoPP::SHA3_256 hash;
    
    CryptoPP::HashFilter filter(hash, new CryptoPP::StringSink(digest_str));
    
    CryptoPP::ChannelSwitch cs(filter);
    
    CryptoPP::FileSource(file, true, new CryptoPP::Redirector(cs));

    return digest_str;
}

std::string ElgamalProtocol::get_digest_hex(const char* file)
{
    std::string digest_hex;
    CryptoPP::SHA3_256 hash;
    
    CryptoPP::HashFilter filter(hash, new CryptoPP::HexEncoder(new CryptoPP::StringSink(digest_hex)));
    
    CryptoPP::ChannelSwitch cs(filter);
    
    CryptoPP::FileSource(file, true, new CryptoPP::Redirector(cs));
    
    return digest_hex;
}

std::string ElgamalProtocol::get_signature_str(const char* file_signature)
{
    std::string signature_str;
    CryptoPP::FileSource(file_signature, true, new CryptoPP::StringSink(signature_str));
    return signature_str;
}

int ElgamalProtocol::encrypt(const char* filedata, const char* key_pub)
{
    uint64_t msg = 0;
    uint64_t e = 0;
    uint64_t k, r;
    int end_file;
    int size_read = 3;
    uint64_t y;

    std::ifstream input(filedata, std::ios::binary);
    std::ofstream output("data/elgamal/e", std::ios::binary | std::ios::trunc);

    get_pub_key(&y, key_pub);

    if (!input) {
        std::cerr << "Encrypt error: unable to open input file: " << filedata << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Encrypt error: unable to open input file: data/rsa/e" << std::endl;
        input.close();
        return -1;
    }

    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(0, input.beg);

    gen_kr(&r, &k, p);
    output << r;

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += size_read, msg = 0) {
        input.read(reinterpret_cast<char*>(&msg), size_read);

        e = (msg * fem(y, k, p)) % p;

        output.write(reinterpret_cast<char*>(&e), sizeof(e));
    }

    input.close();
    output.close();

    return 0;
}

int ElgamalProtocol::decrypt(const char* file)
{
    uint64_t e = 0;
    uint64_t _m = 0;
    uint64_t r;
    
    int size_write = 3;
    int end_file, curr_ptr;
    
    std::ifstream input(file, std::ios::binary);
    std::ofstream output("data/elgamal/_data", std::ios::binary | std::ios::trunc);

    if (!input) {
        std::cerr << "Decrypt error: unable to open input file: " << file << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Decrypt error: unable to open input file: data/elgamal/_data" << std::endl;
        input.close();
        return -1;
    }

    input >> r;

    curr_ptr = input.tellg();
    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(curr_ptr, input.beg);

    for (int ptr_file = curr_ptr; ptr_file < end_file; ptr_file += sizeof(e), e = 0) {
        input.read(reinterpret_cast<char*>(&e), sizeof(e));

        _m = (e * fem(r, -x + p - 1, p)) % p;

        if (ptr_file + sizeof(e) == end_file)
            skeep_leading_zeros(_m, &size_write);

        output.write(reinterpret_cast<char*>(&_m), size_write);
    }

    input.close();
    output.close();

    return 0;
}

int ElgamalProtocol::get_pub_key(uint64_t *y, const char* key_pub)
{
    std::ifstream input(key_pub, std::ios::binary);
    if (!input) {
        std::cerr << "Error: unable to open input file: " << key_pub << std::endl;
        return -1;
    }

    input >> *y;
    input.close();
    return 0;
}

int ElgamalProtocol::gen_kr(uint64_t *r, uint64_t *k, uint64_t p)
{
    do {
        *k = myrand() % (p - 1);
    } while(*k <= 1);

    *r = fem(g, *k, p);

    return 0;
}

int ElgamalProtocol::gen_kr_for_signature(uint64_t* r, uint64_t* k, uint64_t p)
{
    do {
        *k = gen_coprime_number(p - 1);
    } while (*k <= 1 || *k >= p - 1);

    *r = fem(g, *k, p);

    return 0;
}

int ElgamalProtocol::skeep_leading_zeros(uint64_t msg, int *size_write)
{
    uint64_t tmp = msg;
    int max = *size_write;
    for (int i = 0; i < max; i++, (*size_write)--) {
        if (tmp >> 8 * ((max - 1) - i) & 0xff)
            break;
    }
    return 0;
}

uint64_t ElgamalProtocol::get_p() { return this->p; }

uint64_t ElgamalProtocol::get_g() { return this->g; }
