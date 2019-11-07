/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#include <cxxtest/TestListener.h>
#include <cxxtest/TestTracker.h>
#include <cxxtest/TestRunner.h>
#include <cxxtest/RealDescriptions.h>
#include <cxxtest/TestMain.h>
#include <cxxtest/ErrorPrinter.h>

int main( int argc, char *argv[] ) {
 int status;
    CxxTest::ErrorPrinter tmp;
    CxxTest::RealWorldDescription::_worldName = "cxxtest";
    status = CxxTest::Main< CxxTest::ErrorPrinter >( tmp, argc, argv );
    return status;
}
bool suite_test_lib2_init = false;
#include "/home/sandra/crypto/src/lib2/tests/test_lib2.h"

static test_lib2 suite_test_lib2;

static CxxTest::List Tests_test_lib2 = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_test_lib2( "src/lib2/tests/test_lib2.h", 8, "test_lib2", suite_test_lib2, Tests_test_lib2 );

static class TestDescription_suite_test_lib2_test_shamir_proto : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib2_test_shamir_proto() : CxxTest::RealTestDescription( Tests_test_lib2, suiteDescription_test_lib2, 11, "test_shamir_proto" ) {}
 void runTest() { suite_test_lib2.test_shamir_proto(); }
} testDescription_suite_test_lib2_test_shamir_proto;

static class TestDescription_suite_test_lib2_test_rsa : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib2_test_rsa() : CxxTest::RealTestDescription( Tests_test_lib2, suiteDescription_test_lib2, 12, "test_rsa" ) {}
 void runTest() { suite_test_lib2.test_rsa(); }
} testDescription_suite_test_lib2_test_rsa;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
