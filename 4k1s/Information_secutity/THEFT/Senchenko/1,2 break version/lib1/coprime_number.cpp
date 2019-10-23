#include <iostream>

uint64_t myrand();
int gcd(uint64_t, uint64_t, uint64_t *, int64_t *, int64_t *);

uint64_t gen_coprime_number(uint64_t p)
{
    uint64_t coprime_num, result;
    int64_t x, y;
    do {
        coprime_num = myrand();
        gcd(p, coprime_num, &result, &x, &y);
    } while (result != 1);
    return coprime_num;
}
