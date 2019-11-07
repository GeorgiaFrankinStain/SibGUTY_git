#include <fstream>
#include <iostream>
#include "../include/RSA.h"
#include "../include/lib1.h"

#include <cryptopp/cryptlib.h>
#include <cryptopp/files.h>     // for FileSource
#include <cryptopp/channels.h>  // for ChannelSwitch
#include <cryptopp/filters.h>   // for HashFilter, StringSink
#include <cryptopp/hex.h>       // for HexEncoder, StringSink
#include <cryptopp/sha3.h>      // for SHA3_256
#include <cryptopp/sha.h>

RSA::RSA(const char* key_pub)
{
    uint64_t fi;

    do {
        p = gen_prime_number(10000);

        do {
            q = gen_prime_number(10000);
        } while (q == p);

        n = p * q;
    } while (n <= 0xffffff);

    fi = (p - 1) * (q - 1);

    // do {
        d = gen_coprime_number(fi);
    // } while (d >= fi);
    
    c = inversion(d, fi);

    std::ofstream output(key_pub, std::ios::trunc);
    if (!output) {
        std::cerr << "Error: unable to open input file: " << key_pub << std::endl;
    }

    output << n << " " << d;
    output.close();
}

int RSA::file_signatur(const char* filename)
{
    std::string digest_str = get_digest_str(filename);

    encrypt_signature(digest_str);

    return 0;
}

int RSA::signature_verification(const char* file, const char* file_signature, const char* key_pub)
{
    uint64_t n, d;
    get_pub_key(&n, &d, key_pub);

    std::string digest = get_digest_hex(file);
    std::string signature = get_signature_str(file_signature);

    decrypt_signature(signature, n, d);

    std::string expected_digest = get_signature_hex("expected_digest");

    compare_signature(digest, expected_digest);
}

void RSA::compare_signature(std::string diegest, std::string expected_digest)
{
    if (diegest == expected_digest) {
        std::cout << "Ok" << std::endl;
    } else {
        std::cout << "Error" << std::endl;
    }
}

int RSA::encrypt_signature(std::string digest_str)
{
    const char* digest = digest_str.c_str();
    const char* ptr;
    uint64_t* part_ptr;
    uint64_t part, result;

    std::ofstream out("digest", std::ios::binary | std::ios::trunc);

    for (int i = 0; i < 32; i += 3) {
        part_ptr = (uint64_t*) ((uint32_t*) &digest[i]);
        part = *part_ptr & 0xffffff;

        result = fem(part, c, n);

        ptr = (const char*) &result;
        out.write(reinterpret_cast<char*>(&result), 4);
    }

    out.close();
}

int RSA::decrypt_signature(std::string signature, uint64_t n, uint64_t d)
{
    const char* signature_char = signature.c_str();
    const char* ptr;
    uint64_t* part_ptr;
    uint64_t part, result;

    std::ofstream exp_digest("expected_digest", std::ios::binary | std::ios::trunc);

    for (int i = 0; i < 44; i += 4) {
        part_ptr = (uint64_t*) ((uint32_t*) &signature_char[i]);
        part = *part_ptr & 0xffffffff;

        result = fem(part, d, n);

        ptr = (const char*) &result;
        exp_digest << std::hex << ptr;
    }

    exp_digest.close();
}

std::string RSA::get_digest_str(const char* file)
{
    std::string digest_str;
    CryptoPP::SHA3_256 hash;
    
    CryptoPP::HashFilter filter(hash, new CryptoPP::StringSink(digest_str));
    
    CryptoPP::ChannelSwitch cs(filter);
    
    CryptoPP::FileSource(file, true, new CryptoPP::Redirector(cs));

    return digest_str;
}

std::string RSA::get_digest_hex(const char* file)
{
    std::string digest_hex;
    CryptoPP::SHA3_256 hash;
    
    CryptoPP::HashFilter filter(hash, new CryptoPP::HexEncoder(new CryptoPP::StringSink(digest_hex)));
    
    CryptoPP::ChannelSwitch cs(filter);
    
    CryptoPP::FileSource(file, true, new CryptoPP::Redirector(cs));
    
    return digest_hex;
}

std::string RSA::get_signature_str(const char* file_signature)
{
    std::string signature_str;
    CryptoPP::FileSource(file_signature, true, new CryptoPP::StringSink(signature_str));
    return signature_str;
}

std::string RSA::get_signature_hex(const char* file_signature)
{
    std::string signature_hex;
    CryptoPP::FileSource(file_signature, true, new CryptoPP::HexEncoder(new CryptoPP::StringSink(signature_hex)));
    return signature_hex;
}

int RSA::encrypt(const char* filename, const char* key_pub)
{
    uint64_t msg = 0;
    uint64_t e = 0;
    int end_file;
    int size_read = 3;
    uint64_t n, d;

    std::ifstream input(filename, std::ios::binary);
    std::ofstream output("data/rsa/e", std::ios::binary | std::ios::trunc);

    get_pub_key(&n, &d, key_pub);

    if (!input) {
        std::cerr << "Encrypt error: unable to open input file: " << filename << std::endl;
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

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += size_read, msg = 0) {
        input.read(reinterpret_cast<char*>(&msg), size_read);

        e = fem(msg, d, n);

        output.write(reinterpret_cast<char*>(&e), sizeof(e));
    }

    input.close();
    output.close();

    return 0;
}

int RSA::decrypt(const char* filename, const char* outfile)
{
    uint64_t e = 0;
    uint64_t _m = 0;
    int size_write = 3;
    int end_file;
    
    std::ifstream input(filename, std::ios::binary);
    std::ofstream output(outfile, std::ios::binary | std::ios::trunc);

    if (!(input = std::ifstream(filename, std::ios::binary))) {
        std::cerr << "Decrypt error: unable to open input file: " << filename << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Decrypt error: unable to open input file: " << outfile << std::endl;
        input.close();
        return -1;
    }

    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(0, input.beg);

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += sizeof(e), e = 0) {
        input.read(reinterpret_cast<char*>(&e), sizeof(e));
        
        _m = fem(e, c, n);

        if (ptr_file + sizeof(e) == end_file)
            skeep_leading_zeros(_m, &size_write);

        output.write(reinterpret_cast<char*>(&_m), size_write);
    }

    input.close();
    output.close();

    return 0;
}

int RSA::get_pub_key(uint64_t *n, uint64_t *d, const char* key_pub)
{
    std::ifstream input(key_pub, std::ios::binary);
    if (!input) {
        std::cerr << "Error: unable to open input file: " << key_pub << std::endl;
        return -1;
    }

    input >> *n >> *d;
    input.close();
    return 0;
}

int RSA::skeep_leading_zeros(uint64_t msg, int *size_write)
{
    uint64_t tmp = msg;
    int max = *size_write;
    for (int i = 0; i < max; i++, (*size_write)--) {
        if (tmp >> 8 * ((max - 1) - i) & 0xff)
            break;
    }
    return 0;
}

uint64_t RSA::get_n() { return n; }

uint64_t RSA::get_d() { return d; }
