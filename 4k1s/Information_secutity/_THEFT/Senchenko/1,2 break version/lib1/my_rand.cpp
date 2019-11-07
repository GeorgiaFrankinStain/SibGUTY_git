#include <iostream>
#include <random>

#define MY_RAND_MAX 0x7ffffffff

uint64_t myrand()
{
    static std::random_device rd;
    static std::mt19937_64 mersenne(rd());

    return mersenne() % MY_RAND_MAX;
}