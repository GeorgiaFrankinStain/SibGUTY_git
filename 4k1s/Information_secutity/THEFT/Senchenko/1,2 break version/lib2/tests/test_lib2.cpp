#include "test_lib2.h"
#include "../../include/lib1.h"
#include "../../include/lib2.h"
#include <cmath>

void test_lib2::test_shamir_proto()
{
    uint64_t p, m;
    uint64_t Ca, Da, Cb, Db;
    uint64_t x1, x2, x3, x4;

    for (int i = 0; i < 100; i++) {
        m = myrand() % 10000;

        p = gen_p(m);
        
        Ca = gen_coprime_number(p - 1);
        Da = inversion(Ca, p - 1);

        Cb = gen_coprime_number(p - 1);
        Db = inversion(Cb, p - 1);

        x1 = fem(m, Ca, p);
        x2 = fem(x1, Cb, p);
        x3 = fem(x2, Da, p);
        x4 = fem(x3, Db, p);

        TS_ASSERT_EQUALS(x4, m);
    }
}

void test_lib2::test_rsa()
{
    uint64_t Pb, Qb, fi_b, Cb;
    uint64_t Nb, Db;

    uint64_t m, e, _m;

    for (int i = 0; i < 100; i++) {
        Pb = gen_prime_number();

        do {
            Qb = gen_prime_number();
        } while (Qb == Pb);

        Nb = Pb * Qb;

        fi_b = (Pb - 1) * (Qb - 1);

        do {
            Db = gen_coprime_number(fi_b);
        } while (Db >= fi_b);
        
        Cb = inversion(Db, fi_b);

        m = Nb / 2;

        e = fem(m, Db, Nb);
        _m = fem(e, Cb, Nb);

        TS_ASSERT_EQUALS(m, _m);
    }
}
