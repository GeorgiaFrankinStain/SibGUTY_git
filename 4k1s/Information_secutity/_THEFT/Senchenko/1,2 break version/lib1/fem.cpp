#include <iostream>
#include <stdint.h>

uint64_t fem(uint64_t a, uint64_t x, uint64_t p)
{
    uint64_t result = 1;
    for (; x; x >>= 1) {
        if (x & 1)
            result = (result * a) % p;
        a = (a * a) % p;
    }
    return result;
}
