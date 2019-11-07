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
bool suite_test_lib1_init = false;
#include "/home/sandra/crypto/src/lib1/tests/test_lib1.h"

static test_lib1 suite_test_lib1;

static CxxTest::List Tests_test_lib1 = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_test_lib1( "src/lib1/tests/test_lib1.h", 3, "test_lib1", suite_test_lib1, Tests_test_lib1 );

static class TestDescription_suite_test_lib1_test_fem : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib1_test_fem() : CxxTest::RealTestDescription( Tests_test_lib1, suiteDescription_test_lib1, 6, "test_fem" ) {}
 void runTest() { suite_test_lib1.test_fem(); }
} testDescription_suite_test_lib1_test_fem;

static class TestDescription_suite_test_lib1_test_gcd : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib1_test_gcd() : CxxTest::RealTestDescription( Tests_test_lib1, suiteDescription_test_lib1, 7, "test_gcd" ) {}
 void runTest() { suite_test_lib1.test_gcd(); }
} testDescription_suite_test_lib1_test_gcd;

static class TestDescription_suite_test_lib1_test_dhprotocol : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib1_test_dhprotocol() : CxxTest::RealTestDescription( Tests_test_lib1, suiteDescription_test_lib1, 8, "test_dhprotocol" ) {}
 void runTest() { suite_test_lib1.test_dhprotocol(); }
} testDescription_suite_test_lib1_test_dhprotocol;

static class TestDescription_suite_test_lib1_test_bsgs : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_test_lib1_test_bsgs() : CxxTest::RealTestDescription( Tests_test_lib1, suiteDescription_test_lib1, 9, "test_bsgs" ) {}
 void runTest() { suite_test_lib1.test_bsgs(); }
} testDescription_suite_test_lib1_test_bsgs;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
