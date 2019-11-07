#ifndef TEST_LIB1
#define TEST_LIB1

#include <cxxtest/ErrorPrinter.h>
#include "../../include/lib1.h"

class test_lib1 : public CxxTest::TestSuite
{
public:
    void test_fem();
    void test_gcd();
    void test_dhprotocol();
    void test_bsgs();
};

#endif