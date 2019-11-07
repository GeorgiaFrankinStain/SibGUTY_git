#include <iostream>

int gcd(uint64_t, uint64_t, uint64_t *, int64_t *, int64_t *);

uint64_t inversion(uint64_t c, uint64_t p)
{
    uint64_t result;
    int64_t x, y;

    gcd(p, c, &result, &x, &y);
    
    return (y < 0) ? p + y : y;
}
