#include <iostream>
#include <stdint.h>
#include <unordered_map>
#include <cmath>

typedef std::unordered_map<size_t, uint64_t> my_map;

int count_iter;

uint64_t fem(uint64_t, uint64_t, uint64_t);

int bsgs(uint64_t a, uint64_t p, uint64_t y, uint64_t* x)
{
    if (y >= p)
        return -1;

    count_iter = 0;
    uint64_t m, k;
    uint64_t buf;
    uint64_t i, j;
    
    my_map first_sequence;

    k = sqrt(p);
    m = k++;

    buf = y % p;
    first_sequence.insert(my_map::value_type(buf, 0));
    count_iter++;

    for (j = 1; j < m; ++j) {
        buf = (buf * a) % p;
        first_sequence.insert(my_map::value_type(buf, j));
        count_iter++;
    }

    for (i = 1; i <= k; ++i) {
        buf = fem(a, i * m, p);
        count_iter += 2 * log2(i * m);

        try {
            j = first_sequence.at(buf);
            *x = i * m - j;
            if (fem(a, *x, p) == y)
                return 0;
        }
        catch (const std::out_of_range& oor) {}
    }

    #ifdef DEBUG
    std::cout << "i=" << i << std::endl;
    std::cout << "j=" << j << std::endl;
    std::cout << "x=" << x << std::endl;
    #endif

    return -1;
}
