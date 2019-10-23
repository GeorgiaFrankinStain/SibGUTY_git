#ifndef TEST_LIB2
#define TEST_LIB2

#include <cxxtest/ErrorPrinter.h>

class test_lib2 : public CxxTest::TestSuite
{
public:
    void test_shamir_proto();
    void test_rsa();
};

#endif
