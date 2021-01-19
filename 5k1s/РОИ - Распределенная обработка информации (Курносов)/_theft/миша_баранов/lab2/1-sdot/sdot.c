#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <pmmintrin.h>
#include <immintrin.h>
#include <sys/time.h>

enum { n = 1000003 };

float sdot(float *x, float *y, int n)
{
    float s = 0;
    for (int i = 0; i < n; i++)
        s += x[i] * y[i];
    return s;
}

float sdot_sse(float *x, float *y, int n)
{
//    TODO
   float t[4] __attribute__ ((aligned (16)));
    __m128 *xx = (__m128 *)x;
    __m128 *yy = (__m128 *)y;
    __m128 sum = _mm_setzero_ps();

   int k = n / 4;
   for (int i = 0; i < k; i++)
    {
	__m128 zz = _mm_mul_ps(xx[i],yy[i]);
	sum = _mm_add_ps(sum, zz);
    }

   _mm_store_ps(t, sum);
   float s = t[0] + t[1] + t[2] + t[3];


   for (int i = k * 4; i < n; i++)
   {
	s += x[i] * y[i];
   }
   return s;
}

double ddot_sse(double *x, double *y, int n)
{
//    TODO
   double t[2] __attribute__ ((aligned (8)));
    __m128d *xx = (__m128d *)x;
    __m128d *yy = (__m128d *)y;
    __m128d sum = _mm_setzero_pd();

   int k = n / 2;
   for (int i = 0; i < k; i++)
    {
	__m128d zz = _mm_mul_pd(xx[i],yy[i]);
	sum = _mm_add_pd(sum, zz);
    }

   _mm_store_pd(t, sum);
   double s = t[0] + t[1];


   for (int i = k * 2; i < n; i++)
   {
	s += x[i] * y[i];
   }
   return s;
}

float sdot_avx(float *x, float *y, int n)
{
//    TODO
    float t[8] __attribute__ ((aligned (32)));
    __m256 *xx = (__m256 *)x;
    __m256 *yy = (__m256 *)y;
    __m256 sum = _mm256_setzero_ps();

   int k = n / 8;
   for (int i = 0; i < k; i++)
   {
	__m256 zz = _mm256_mul_ps(xx[i],yy[i]);
	sum = _mm256_add_ps(sum, zz);
   }

   sum = _mm256_hadd_ps(sum, sum);
   __m256 sum_permuted_1 = _mm256_permute2f128_ps(sum, sum, 1);
   sum = _mm256_hadd_ps(sum_permuted_1, sum_permuted_1);
   __m256 sum_permuted_2 = _mm256_permute2f128_ps(sum, sum, 2);
   sum = _mm256_add_ps(sum_permuted_2, sum);

   _mm256_store_ps(t, sum);
   float s = t[0];
   return s;
}

double ddot_avx(double *x, double *y, int n)
{
//    TODO
    double t[4] __attribute__ ((aligned (16)));
    __m256d *xx = (__m256d *)x;
    __m256d *yy = (__m256d *)y;
    __m256d sum = _mm256_setzero_pd();

   int k = n / 4;
   for (int i = 0; i < k; i++)
   {
	__m256d zz = _mm256_mul_pd(xx[i],yy[i]);
	sum = _mm256_add_pd(sum, zz);
   }

   sum = _mm256_hadd_pd(sum, sum);
   __m256d sum_permuted_1 = _mm256_permute2f128_pd(sum, sum, 1);
   sum = _mm256_hadd_pd(sum_permuted_1, sum_permuted_1);
   __m256d sum_permuted_2 = _mm256_permute2f128_pd(sum, sum, 2);
   sum = _mm256_add_pd(sum_permuted_2, sum);

   _mm256_store_pd(t, sum);
   double s = t[0];
   return s;
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
    float *x = xmalloc(sizeof(*x) * n);
    float *y = xmalloc(sizeof(*y) * n);
    for (int i = 0; i < n; i++) {
        x[i] = 2.0;
        y[i] = 3.0;
    }

    double t = wtime();
    float res = sdot(x, y, n);
    t = wtime() - t;

    float valid_result = 2.0 * 3.0 * n;
    printf("Result (scalar): %.6f err = %f\n", res, fabsf(valid_result - res));
    printf("Elapsed time (scalar): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

double run_vectorized_sdot_sse()
{
    float *x = _mm_malloc(sizeof(*x) * n, 16);  // 16?
    float *y = _mm_malloc(sizeof(*y) * n, 16);
    for (int i = 0; i < n; i++) {
        x[i] = 2.0;
        y[i] = 3.0;
    }

    double t = wtime();
    float res = sdot_sse(x, y, n);
    t = wtime() - t;

    float valid_result = 2.0 * 3.0 * n;
    printf("Result (vectorized SSE_FLOAT): %.6f err = %f\n", res, fabsf(valid_result - res));
    printf("Elapsed time (vectorized SSE_FLOAT): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

double run_vectorized_ddot_sse()
{
    double *x = _mm_malloc(sizeof(*x) * n, 8);  // 8 ?
    double *y = _mm_malloc(sizeof(*y) * n, 8);
    for (int i = 0; i < n; i++) {
        x[i] = 2.0;
        y[i] = 3.0;
    }

    double t = wtime();
    double res = ddot_sse(x, y, n);
    t = wtime() - t;

    float valid_result = 2.0 * 3.0 * n;
    printf("Result (vectorized SSE_FLOAT): %.6f err = %f\n", res, fabs(valid_result - res));
    printf("Elapsed time (vectorized SSE_FLOAT): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

double run_vectorized_sdot_avx()
{
    float *x = _mm_malloc(sizeof(*x) * n, 32);
    float *y = _mm_malloc(sizeof(*y) * n, 32);
    for (int i = 0; i < n; i++) {
        x[i] = 2.0;
        y[i] = 3.0;
    }

    double t = wtime();
    float res = sdot_avx(x, y, n);
    t = wtime() - t;

    float valid_result = 2.0 * 3.0 * n;
    printf("Result (vectorized SDOT_AVX_FLOAT): %.6f err = %f\n", res, fabsf(valid_result - res));
    printf("Elapsed time (vectorized SDOT_AVX_FLOAT): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

double run_vectorized_ddot_avx()
{
    double *x = _mm_malloc(sizeof(*x) * n, 32);
    double *y = _mm_malloc(sizeof(*y) * n, 32);
    for (int i = 0; i < n; i++) {
        x[i] = 2.0;
        y[i] = 3.0;
    }

    double t = wtime();
    double res = ddot_avx(x, y, n);
    t = wtime() - t;

    float valid_result = 2.0 * 3.0 * n;
    printf("Result (vectorized SDOT_AVX_FLOAT): %.6f err = %f\n", res, fabs(valid_result - res));
    printf("Elapsed time (vectorized SDOT_AVX_FLOAT): %.6f sec.\n", t);
    free(x);
    free(y);
    return t;
}

int main(int argc, char **argv)
{
    printf("SDOT: n = %d\n", n);
    float tscalar = run_scalar();
    float tvec_sse_float = run_vectorized_sdot_sse();
    float tvec_sse_double = run_vectorized_ddot_sse();

    float tvec_avx_float = run_vectorized_sdot_avx();
    float tvec_avx_double =run_vectorized_ddot_avx();

    printf("Speedup SDOT SSE FLOAT: %.2f\n", tscalar / tvec_sse_float);
    printf("Speedup SDOT SSE DOUBLE: %.2f\n", tscalar/ tvec_sse_double);

    printf("Speedup SDOT AVX FLOAT: %.2f\n", tscalar / tvec_avx_float);
    printf("Speedup SDOT AVX DOUBLE: %.2f\n", tscalar / tvec_avx_double);
    return 0;
}
