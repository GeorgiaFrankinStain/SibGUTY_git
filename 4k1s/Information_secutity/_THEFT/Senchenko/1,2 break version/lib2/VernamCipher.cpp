#include <fstream>
#include <iostream>
#include <random>
#include "../include/VernamCipher.h"

VernamCipher::VernamCipher() {}

int VernamCipher::encrypt(const char* filedata, const char* outfile)
{
    uint64_t msg = 0;
    uint64_t e = 0;
    uint64_t k;
    int end_file;

    std::ifstream input(filedata, std::ios::binary);
    std::ofstream output(outfile, std::ios::binary | std::ios::trunc);

    if (!input) {
        std::cerr << "Encrypt error: unable to open input file: " << filedata << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Encrypt error: unable to open input file: " << outfile << std::endl;
        input.close();
        return -1;
    }

    static std::random_device rd;
    static std::mt19937_64 mersenne(rd());

    k = mersenne() % 0xffffffffffffffff;

    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(0, input.beg);

    output << k;

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += sizeof(msg), msg = 0) {
        input.read(reinterpret_cast<char*>(&msg), sizeof(msg));

        e = msg ^ k;

        output.write(reinterpret_cast<char*>(&e), sizeof(e));
    }

    input.close();
    output.close();

    return 0;
}

int VernamCipher::decrypt(const char* filedata, const char* outfile)
{
    uint64_t e = 0;
    uint64_t _m = 0;
    uint64_t k;
    
    int size_write = sizeof(_m);
    int end_file, curr_ptr;
    
    std::ifstream input(filedata, std::ios::binary);
    std::ofstream output(outfile, std::ios::binary | std::ios::trunc);

    if (!input) {
        std::cerr << "Decrypt error: unable to open input file: " << filedata << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Decrypt error: unable to open input file: " << outfile << std::endl;
        input.close();
        return -1;
    }

    input >> k;

    curr_ptr = input.tellg();
    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(curr_ptr, input.beg);

    for (int ptr_file = curr_ptr; ptr_file < end_file; ptr_file += sizeof(e), e = 0) {
        input.read(reinterpret_cast<char*>(&e), sizeof(e));

        _m = e ^ k;

        if ((ptr_file + sizeof(e)) == end_file)
            skeep_leading_zeros(_m, &size_write);

        output.write(reinterpret_cast<char*>(&_m), size_write);
    }

    input.close();
    output.close();
    return 0;
}

int VernamCipher::skeep_leading_zeros(uint64_t msg, int *size_write)
{
    uint64_t tmp = msg;
    int max = *size_write;
    for (int i = 0; i < max; i++, (*size_write)--) {
        if (tmp >> 8 * ((max - 1) - i) & 0xff)
            break;
    }
    return 0;
}
