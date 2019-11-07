#include <iostream>
#include <stdint.h>

int gcd(uint64_t u, uint64_t v, uint64_t* result, int64_t* x, int64_t* y)
{
    int64_t ux = 1, uy = 0;
    int64_t vx = 0, vy = 1;

    uint64_t t, q;

    while (v) {
        q = u / v;

        t = u - q * v;
        *x = ux - vx * q;
        *y = uy - vy * q;

        #ifdef DEBUG
        printf("U %lu %lli %lli\n", u, ux, uy);
        printf("V %lu %lli %lli\n", v, vx, vy);
        printf("T %lu %lli %lli\n\n", t, *x, *y);
        #endif

        u = v;
        ux = vx;
        uy = vy;

        v = t;
        vx = *x;
        vy = *y;
    }

    *result = u;
    *x = ux;
    *y = uy;

    return 0;
}
