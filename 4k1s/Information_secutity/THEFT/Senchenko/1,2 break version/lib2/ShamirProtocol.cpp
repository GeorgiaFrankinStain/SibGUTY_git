#include <fstream>
#include <iostream>
#include "../include/lib1.h"
#include "../include/ShamirProtocol.h"

ShamirProtocol::ShamirProtocol()
{
    uint64_t p;
    do {
        p = gen_prime_number(0xffffffff);
    } while (p <= 0xfffff);

    init(p);
}

ShamirProtocol::ShamirProtocol(uint64_t _p) { init(_p); }

void ShamirProtocol::init(uint64_t _p)
{
    p = _p;
    c = gen_coprime_number(p - 1);
    d = inversion(c, p - 1);
}

int ShamirProtocol::encrypt(enum status _stat, const char* filedata, const char* outfile)
{
    uint64_t msg = 0;
    uint64_t x = 0;
    int end_file;
    stat = _stat;

    int size_read = (stat == src) ? 3 : sizeof(x);

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

    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(0, input.beg);

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += size_read, msg = 0) {
        input.read(reinterpret_cast<char*>(&msg), size_read);

        x = fem(msg, c, p);

        output.write(reinterpret_cast<char*>(&x), sizeof(x));
    }

    input.close();
    output.close();

    return 0;
}

int ShamirProtocol::decrypt(const char* filein, const char* fileout)
{
    uint64_t x = 0;
    uint64_t _m = 0;
    
    int size_write = (stat == dst) ? 3 : sizeof(_m);
    int end_file;
    
    std::ifstream input(filein, std::ios::binary);
    std::ofstream output(fileout, std::ios::binary | std::ios::trunc);

    if (!input) {
        std::cerr << "Decrypt error: unable to open input file: " << filein << std::endl;
        return -1;
    }

    if (!output) {
        std::cerr << "Decrypt error: unable to open input file: " << fileout << std::endl;
        input.close();
        return -1;
    }

    input.seekg(0, input.end);
    end_file = input.tellg();
    input.seekg(0, input.beg);

    for (int ptr_file = 0; ptr_file < end_file; ptr_file += sizeof(x), x = 0) {
        input.read(reinterpret_cast<char*>(&x), sizeof(x));

        _m = fem(x, d, p);

        if (stat == dst && (ptr_file + sizeof(x)) == end_file)
            skeep_leading_zeros(_m, &size_write);

        output.write(reinterpret_cast<char*>(&_m), size_write);
    }

    input.close();
    output.close();
    return 0;
}

int ShamirProtocol::skeep_leading_zeros(uint64_t msg, int *size_write)
{
    uint64_t tmp = msg;
    int max = *size_write;
    for (int i = 0; i < max; i++, (*size_write)--) {
        if (tmp >> 8 * ((max - 1) - i) & 0xff)
            break;
    }
    return 0;
}

uint64_t ShamirProtocol::get_p() { return p; }

uint64_t ShamirProtocol::get_d() { return d; }

