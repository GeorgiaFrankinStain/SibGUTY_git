#include <stdlib.h>
#include <iostream>
#include <math.h>
#include <stdint.h>

uint64_t fem(uint64_t, uint64_t, uint64_t);
uint64_t myrand();

static bool is_prime(int p)
{
    if (p <= 1) return false;

    int b = (int) pow(p, 0.5);

    for (int i = 2; i <= b; ++i)
        if ((p % i) == 0) return false;

    return true;
}

uint64_t gen_prime_number(uint64_t range)
{
    uint64_t x;
    // int range = 1000000000;
    // int range = 10000; // for rsa

    do {
        x = myrand() % range;
    } while (is_prime(x) == false);

    return x;
}

void gen_pqg(uint64_t* p, uint64_t* q, uint64_t* g)
{
    do {
        *q = gen_prime_number(1000000000);
        // *q = gen_prime_number(10000000000); // for elgamal signature
        *p = 2 * *q + 1;
    } while (!is_prime(*p));

    do {
        *g = myrand() % *p;
    } while ((*g <= 1) && fem(*g, *q, *p) != 1);

    #ifdef DEBUG
    std::cout << "p="<< *p << std::endl;
    std::cout << "q="<< *q << std::endl;
    std::cout << "g="<< *g << std::endl << std::endl;
    #endif
}

uint64_t gen_x(uint64_t p)
{
    uint64_t X;
    do {
        X = myrand() % (p - 1);
    } while ((X == 0) || (X == 1));
    return X;
}
