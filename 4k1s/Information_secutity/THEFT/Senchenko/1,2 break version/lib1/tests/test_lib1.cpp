#include "test_lib1.h"
#include <cmath>

void test_lib1::test_fem()
{
    TS_ASSERT_EQUALS(fem(13, 17, 15), 13);
    TS_ASSERT_EQUALS(fem(13, 7, 15), 7);
}

void test_lib1::test_gcd()
{
    uint64_t a, b;
    uint64_t result;
    int64_t x, y;
    
    for (int i = 0; i < 100; ++i) {
        a = myrand();
        b = myrand();
        gcd(a, b, &result, &x, &y);

        TS_ASSERT_EQUALS(a * x + b * y, result);
    }
}

void test_lib1::test_dhprotocol()
{
    uint64_t p, q, g, Xa, Xb, Ya, Yb, Zab, Zba;
    
    for (int i = 0; i < 100; ++i) {
        gen_pqg(&p, &q, &g);

        // std::cout << std::endl;
        // std::cout << "p=" << p << std::endl;
        // std::cout << "q=" << q << std::endl;
        // std::cout << "g=" << g << std::endl;

        Xa = gen_x(p);
        Xb = gen_x(p);

        Ya = fem(g, Xa, p);
        Yb = fem(g, Xb, p);

        Zab = fem(Yb, Xa, p);
        Zba = fem(Ya, Xb, p);

        TS_ASSERT_EQUALS(Zab, Zba);
    }
}

void test_lib1::test_bsgs()
{
    uint64_t a, src_x, find_x, p, y;
    extern int count_iter;
    auto f = [](uint64_t p) { return sqrt(p) * pow(log2(p), 2); };

    // при данных значениях решения нет
    a = 47; p = 23; y = 16;
    TS_ASSERT_EQUALS(bsgs(a, p, y, &find_x), -1);

    // y >= p
    a = 13; p = 15; y = 16;
    TS_ASSERT_EQUALS(bsgs(a, p, y, &find_x), -1);

    a = 13; p = 15; y = 15;
    TS_ASSERT_EQUALS(bsgs(a, p, y, &find_x), -1);

    a = 13; p = 15; src_x = 17;
    bsgs(a, p, fem(a, src_x, p), &find_x);
    TS_ASSERT_EQUALS(fem(a, find_x, p), fem(a, src_x, p));
    TS_ASSERT_LESS_THAN_EQUALS(count_iter, f(p));

    a = 13; p = 15; src_x = 7;
    bsgs(a, p, fem(a, src_x, p), &find_x);
    TS_ASSERT_EQUALS(fem(a, find_x, p), fem(a, src_x, p));
    TS_ASSERT_LESS_THAN_EQUALS(count_iter, f(p));

    for (int i = 0; i < 10; ++i) {
        a = myrand();
        p = myrand();
        src_x = myrand();

        if (bsgs(a, p, fem(a, src_x, p), &find_x) != -1) {
            TS_ASSERT_EQUALS(fem(a, find_x, p), fem(a, src_x, p));
            TS_ASSERT_LESS_THAN_EQUALS(count_iter, f(p));
        }
    }
}
