#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <xmmintrin.h>
#include <immintrin.h>
#include <sys/time.h>
#include <assert.h>


#define EPS 1E-6

enum {
    n = 1000003
};

void saxpy(float *x, float *y, float a, int n)
{
    for (int i = 0; i < n; i++)
        y[i] = a * x[i] + y[i];
}

void saxpy_sse_f(float * restrict x, float * restrict y, float a, int n)
{
    __m128 *xx = (__m128 *)x;
    __m128 *yy = (__m128 *)y;

    int bit_size_float = 32;
    int number_float_in_m128 = 128 / bit_size_float;
    assert(number_float_in_m128 == 4);
    int number_packages_sse = n / number_float_in_m128;
    __m128 aa = _mm_set1_ps(a);
    for (int i = 0; i < number_packages_sse; i++) {
        __m128 z = _mm_mul_ps(aa, xx[i]);
        yy[i] = _mm_add_ps(z, yy[i]);
    }

    int number_items_processed_sse = number_packages_sse * number_float_in_m128;
    for (int i = number_items_processed_sse; i < n; i++)
	y[i] = a * x[i] + y[i];
}

void daxpy_sse_d(double * restrict x, double * restrict y, double a, int n)
{
	__m128d *xx = (__m128d *)x;
	__m128d *yy = (__m128d *)y;

    int bit_size_double = 64;
    int number_double_in_m128 = 128 / bit_size_double;
    assert(number_double_in_m128 == 2);
	int number_packages_sse = n / number_double_in_m128;
	__m128d aa = _mm_set1_pd(a);
	for (int i = 0; i < number_packages_sse; i++)
	{
	     __m128d z = _mm_mul_pd(aa, xx[i]);
	     yy[i] = _mm_add_pd(z, yy[i]);
	}
    int number_items_processed_sse = number_packages_sse * number_double_in_m128;
	for (int i = number_items_processed_sse; i < n; i++)
		y[i] = a * x[i] + y[i];
}


void saxpy_avx(float * restrict x, float * restrict y, float a, int n)
{
    __m256 *xx = (__m256 *)x;
    __m256 *yy = (__m256 *)y;



    int bit_size_float = 32;
    int number_float_in_m256 = 256 / bit_size_float;
    assert(number_float_in_m256 == 8);
    int number_packages_sse = n / number_float_in_m256;
    __m256 aa = _mm256_set1_ps(a);
    for (int i = 0; i < number_packages_sse; i++) {
        __m256 z = _mm256_mul_ps(aa, xx[i]);
        yy[i] = _mm256_add_ps(z, yy[i]);
    }

    /* Loop reminder (n % 8 != 0) ? */
    for (int i = number_packages_sse * 8; i < n; i++)
	y[i] = a * x[i] + y[i];
}


void *xmalloc(size_t size)
{
    void *p = malloc(size);
    if (!p) {
        fprintf(stderr, "malloc failed\n");
        exit(EXIT_FAILURE);
    }
    return p;
}

double wtime()
{
    struct timeval t;
    gettimeofday(&t, NULL);
    return (double)t.tv_sec + (double)t.tv_usec * 1E-6;
}

double run_scalar()
{
    float *x, *y, a = 2.0;

    x = xmalloc(sizeof(*x) * n);
    y = xmalloc(sizeof(*y) * n);
    for (int i = 0; i < n; i++) {
        x[i] = i * 2 + 1.0;
        y[i] = i;
    }

    double t = wtime();
    saxpy(x, y, a, n);
    t = wtime() - t;

    /* Verification */
    for (int i = 0; i < n; i++) {
        float xx = i * 2 + 1.0;
        float yy = a * xx + i;
        if (fabs(y[i] - yy) > EPS) {
            fprintf(stderr, "run_scalar: verification failed (y[%d] = %f != %f)\n", i, y[i], yy);
            break;
        }
    }

    printf("Elapsed time (scalar): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

double run_vectorized_saxpy_sse()
{
    float *x, *y, a = 2.0;

    x = _mm_malloc(sizeof(*x) * n, 16);
    y = _mm_malloc(sizeof(*y) * n, 16);
    for (int i = 0; i < n; i++) {
        x[i] = i * 2 + 1.0;
        y[i] = i;
    }

    double t = wtime();
    saxpy_sse_f(x, y, a, n);
    t = wtime() - t;

    /* Verification */
    for (int i = 0; i < n; i++) {
        float xx = i * 2 + 1.0;
        float yy = a * xx + i;
        if (fabs(y[i] - yy) > EPS) {
            fprintf(stderr, "run_vectorized_sse: verification failed (y[%d] = %f != %f)\n", i, y[i], yy);
            break;
        }
    }

    printf("Elapsed time (vectorized SAXPY SSE): %.6f sec.\n", t);
    _mm_free(x);
    _mm_free(y);
    return t;
}

double run_vectorized_daxpy_sse()
{
    double *x, *y, a = 2.0;

    x = _mm_malloc(sizeof(*x) * n, 16);
    y = _mm_malloc(sizeof(*y) * n, 16);
    for (int i = 0; i < n; i++) {
        x[i] = i * 2 + 1.0;
        y[i] = i;
    }

    double t = wtime();
    daxpy_sse_d(x, y, a, n);
    t = wtime() - t;

    /* Verification */
    for (int i = 0; i < n; i++) {
        double xx = i * 2 + 1.0;
        double yy = a * xx + i;
        if (fabs(y[i] - yy) > EPS) {
            fprintf(stderr, "run_vectorized_daxpy_sse: verification failed (y[%d] = %f != %f)\n", i, y[i], yy);
            break;
        }
    }

    printf("Elapsed time (vectorized DAXPY SSE): %.6f sec.\n", t);
    _mm_free(x);
    _mm_free(y);
    return t;
}

double run_vectorized_saxpy_avx()
{
    float *x, *y, a = 2.0;

    x = _mm_malloc(sizeof(*x) * n, 32);
    y = _mm_malloc(sizeof(*y) * n, 32);
    for (int i = 0; i < n; i++) {
        x[i] = i * 2 + 1.0;
        y[i] = i;
    }

    double t = wtime();
    saxpy_avx(x, y, a, n);
    t = wtime() - t;

    /* Verification */
    for (int i = 0; i < n; i++) {
        float xx = i * 2 + 1.0;
        float yy = a * xx + i;
        if (fabs(y[i] - yy) > EPS) {
            fprintf(stderr, "run_vectorized_sse: verification failed (y[%d] = %f != %f)\n", i, y[i], yy);
            break;
        }
    }

    printf("Elapsed time (vectorized SAXPY AVX): %.6f sec.\n", t);
    _mm_free(x);
    _mm_free(y);
    return t;
}

int main(int argc, char **argv)
{
    printf("SAXPY (y[i] = a * x[i] + y[i]; n = %d)\n", n);
    double tscalar = run_scalar();
    double tvec_sse_saxpy = run_vectorized_saxpy_sse();
    double tvec_sse_daxpy = run_vectorized_daxpy_sse();
    double tvec_avx_saxpy = run_vectorized_saxpy_avx();
    printf("Speedup SAXPY SSE: %.2f\n", tscalar / tvec_sse_saxpy);
    printf("Speedup DAXPY SSE: %.2f\n", tscalar / tvec_sse_daxpy);
    printf("Speedup SAXPY AVX: %.2f\n", tscalar / tvec_avx_saxpy);

    return 0;
}
