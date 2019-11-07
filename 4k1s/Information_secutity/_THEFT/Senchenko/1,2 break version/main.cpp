#include <iostream>
#include "include/lib1.h"
#include "include/lib2.h"

void task1();
void task2();
void task3();
int task4();

void test_task2(const char*);
void task_shamir_proto();
void task_rsa();
void task_elgamal();
void task_vernam();

void test_task3(const char*);

void test_gmp();
void test_gmp_and_cryptopp(const char* file);

#include <gmp.h>

int main(int argc, const char** argv)
{
    // test_gmp();
    // test_gmp_and_cryptopp(argv[1]);

    #if 0
    mpz_t tmp;
    mpz_init(tmp);
    uint64_t x = 0xffffffffffffffff;

    // mpz_import(tmp, 1, -1, sizeof(uint64_t), 0, 0, &x);
    mpz_set_ui(tmp, x);
    gmp_printf("%Zx\n", tmp);

    mpz_clear(tmp);
    #endif

    #if 0
    std::string str("A123");
    const char* const_char = str.c_str();
    uint32_t* a = (uint32_t*) const_char;
    std::cout << str << std::endl;
    std::cout << std::hex << *a << std::endl;
    #endif

    test_task3(argv[1]);

    // test_task2(argv[1]);

    return 0;
}
