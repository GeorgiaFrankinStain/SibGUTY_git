#include <gmp.h>
#include <gmpxx.h>
#include <string>
#include <iostream>

#include <cryptopp/cryptlib.h>
#include <cryptopp/files.h>     // for FileSource
#include <cryptopp/channels.h>  // for ChannelSwitch
#include <cryptopp/filters.h>   // for HashFilter, StringSink
#include <cryptopp/hex.h>       // for HexEncoder, StringSink
#include <cryptopp/sha3.h>      // for SHA3_256
#include <cryptopp/sha.h>

#include "include/lib3.h"

void gmp_gen_prime(mpz_t rand, gmp_randstate_t state, mp_bitcnt_t size, int reps)
{
    do {
        mpz_urandomb(rand, state, size);
    } while (mpz_probab_prime_p(rand, reps) < 1);
}

void gmp_gen_coprime_number(mpz_t d, mp_bitcnt_t size, gmp_randstate_t state, mpz_t p)
{
    // uint64_t coprime_num, result;
    // int64_t x, y;
    // do {
    //     coprime_num = myrand();
    //     gcd(p, coprime_num, &result, &x, &y);
    // } while (result != 1);
    // return coprime_num;

    mpz_t result;
    mpz_init(result);

    do {
        mpz_urandomb(d, state, size);
        mpz_gcd(result, d, p);
    } while (mpz_cmp_ui(result, 1) != 0);

    mpz_clear(result);
}

void test_gmp()
{
    mpz_t num;
    mpz_init(num);

    std::string hex("A5");
    mpz_set_str(num, hex.c_str(), 16);
    gmp_printf("%Zx\n", num);

    mpz_add_ui(num, num, 5); // num = num + 5
    gmp_printf("%Zx\n", num);

    mpz_mul_2exp(num, num, 4); // num = num << 4;
    gmp_printf("%Zx\n", num);

    mpz_tdiv_q_2exp(num, num, 4); // num = num >> 4;
    gmp_printf("%Zx\n", num);

    mpz_add_ui(num, num, 1);
    gmp_printf("%Zx\n", num);

    mpz_t tmp;
    mpz_init_set_ui(tmp, 1);
    mpz_and(num, num, tmp); // num = num & tmp
    gmp_printf("%Zx\n", num);
    mpz_clear(tmp);

    mpz_t x, p, res;

    mpz_init_set_ui(x, 2);
    mpz_init_set_ui(p, 4);
    mpz_init_set_ui(num, 11);
    gmp_printf("%Zx\n", num);

    my_mpz_fem(num, x, p, res);

    gmp_printf("res = %Zx\n", res);
    gmp_printf("%Zx\n", num);

    mpz_clear(x);
    mpz_clear(p);
    mpz_clear(res);

    mpz_clear(num);
}

void test_gmp_and_cryptopp(const char* file)
{
    using namespace CryptoPP;
    std::string digest_str;
    SHA3_256 hash;
    
    HashFilter filter(hash, new HexEncoder(new StringSink(digest_str)));
    
    ChannelSwitch cs(filter);
    
    FileSource(file, true, new Redirector(cs));

    std::cout << digest_str << std::endl;

    mpz_t digest;
    mpz_init2(digest, 256);

    mpz_set_str(digest, digest_str.c_str(), 16);
    gmp_printf("%Zx\n", digest);


    /////////////
    gmp_randstate_t state;
    gmp_randinit_mt(state);
    
    mpz_t p, q, n, fi, d, c;
    mpz_init2(p, 129);
    mpz_init2(q, 129);
    mpz_init2(n, 258);
    mpz_init2(fi, 258);
    mpz_init2(d, 256);
    mpz_init2(c, 64);

    do {
        gmp_gen_prime(p, state, 129, 25);

        do { 
            gmp_gen_prime(q, state, 129, 25);
        } while (mpz_cmp(p, q) == 0);
        
        mpz_mul(n, p, q);
    } while (mpz_cmp(n, digest) <= 0);

    gmp_printf("%Zx\n", p);
    gmp_printf("%Zx\n", q);
    gmp_printf("%Zx\n", n);

    gmp_gen_coprime_number(d, 256, state, fi);
    gmp_printf("%Zx\n", d);


    ////////////////

    mpz_clear(d);
    mpz_clear(c);
    mpz_clear(fi);
    mpz_clear(n);
    mpz_clear(p);
    mpz_clear(q);
    gmp_randclear(state);
    mpz_clear(digest);
}
