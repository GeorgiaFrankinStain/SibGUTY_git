#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <float.h>
#include <immintrin.h>
#include <sys/time.h>

#define EPS 1E-6

enum {
    n = 1000007
};

void compute_sqrt(float *in, float *out, int n)
{
    for (int i = 0; i < n; i++) {
        if (in[i] > 0)
            out[i] = sqrtf(in[i]);
        else
            out[i] = 0.0;
    }
}

void compute_sqrt_avx_float(float *in, float *out, int n)
{
	/* TODO */
	__m256 *in_vec = (__m256 *)in;
	__m256 *out_vec = (__m256 *)out;
	int k = n / 8;

	__m256 zero = _mm256_setzero_ps();
	for (int i = 0; i < k; i++)
	{
		__m256 v = _mm256_load_ps((float *)&in_vec[i]);
		__m256 sqrt_vec = _mm256_sqrt_ps(v);
		__m256 mask = _mm256_cmp_ps(v, zero, _CMP_GT_OQ);
		__m256 gtzero_vec = _mm256_and_ps(mask, sqrt_vec);
		__m256 lezero_vec = _mm256_andnot_ps(mask, zero);
		out_vec[i] = _mm256_or_ps(gtzero_vec, lezero_vec);
	}

	for (int i = k * 8; i < n; i++)
		out[i] = in[i] > 0 ? sqrtf(in[i]) : 0.0;
}

void compute_sqrt_avx_float_blend(float *in, float *out, int n)
{
	/* TODO */
	__m256 *in_vec = (__m256 *)in;
	__m256 *out_vec = (__m256 *)out;
	int k = n / 8;

	__m256 zero = _mm256_setzero_ps();
	for (int i = 0; i < k; i++)
	{
		__m256 v = _mm256_load_ps((float *)&in_vec[i]);
		__m256 sqrt_vec = _mm256_sqrt_ps(v);
		__m256 mask = _mm256_cmp_ps(v, zero, _CMP_GT_OQ);
		out_vec[i] = _mm256_blendv_ps(zero, sqrt_vec, mask);
	}

	for (int i = k * 8; i < n; i++)
		out[i] = in[i] > 0 ? sqrtf(in[i]) : 0.0;
}

void compute_sqrt_avx_double(double *in, double *out, int n)
{
	__m256d *in_vec = (__m256d *)in;
	__m256d *out_vec = (__m256d *)out;
	int k = n / 4;

	__m256d zero = _mm256_setzero_pd();
	for (int i = 0; i < k; i++)
	{
		__m256d v = _mm256_load_pd((double *)&in_vec[i]);
		__m256d sqrt_vec = _mm256_sqrt_pd(v);
		__m256d mask = _mm256_cmp_pd(v, zero, _CMP_GT_OQ);
		__m256d gtzero_vec = _mm256_and_pd(mask, sqrt_vec);
		__m256d lezero_vec = _mm256_andnot_pd(mask, zero);
		out_vec[i] = _mm256_or_pd(gtzero_vec, lezero_vec);
	}

	for (int i = k * 4; i < n; i++)
		out[i] = in[i] > 0 ? sqrt(in[i]) : 0.0;
}

void compute_sqrt_avx_double_blend(double *in, double *out, int n)
{
	__m256d *in_vec = (__m256d *)in;
	__m256d *out_vec = (__m256d *)out;
	int k = n / 4;

	__m256d zero = _mm256_setzero_pd();
	for (int i = 0; i < k; i++)
	{
		__m256d v = _mm256_load_pd((double *)&in_vec[i]);
		__m256d sqrt_vec = _mm256_sqrt_pd(v);
		__m256d mask = _mm256_cmp_pd(v, zero, _CMP_GT_OQ);
		out_vec[i] = _mm256_blendv_pd(zero, sqrt_vec, mask);
	}

	for (int i = k * 4; i < n; i++)
		out[i] = in[i] > 0 ? sqrt(in[i]) : 0.0;
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
    float *in = xmalloc(sizeof(*in) * n);
    float *out = xmalloc(sizeof(*out) * n);
    srand(0);
    for (int i = 0; i < n; i++) {
        in[i] = rand() > RAND_MAX / 2 ? 0 : rand() / (float)RAND_MAX * 1000.0;
    }
    double t = wtime();
    compute_sqrt(in, out, n);
    t = wtime() - t;

#if 0
    for (int i = 0; i < n; i++)
        printf("%.4f ", out[i]);
    printf("\n");
#endif

    printf("Elapsed time (scalar): %.6f sec.\n\n", t);
    free(in);
    free(out);
    return t;
}

double run_vectorized_avx_float()
{
    float *in = _mm_malloc(sizeof(*in) * n, 32);
    float *out = _mm_malloc(sizeof(*out) * n, 32);
    srand(0);
    for (int i = 0; i < n; i++) {
        in[i] = rand() > RAND_MAX / 2 ? 0 : rand() / (float)RAND_MAX * 1000.0;
    }
    double t = wtime();
    compute_sqrt_avx_float(in, out, n);
    t = wtime() - t;

#if 0
    for (int i = 0; i < n; i++)
        printf("%.4f ", out[i]);
    printf("\n");
#endif

    for (int i = 0; i < n; i++) {
        float r = in[i] > 0 ? sqrtf(in[i]) : 0.0;
        if (fabs(out[i] - r) > EPS) {
            fprintf(stderr, "(AVX_FLOAT)Verification: FAILED at out[%d] = %f != %f\n", i, out[i], r);
            break;
        }
    }

    printf("Elapsed time (vectorized AVX_FLOAT): %.6f sec.\n\n", t);
    free(in);
    free(out);
    return t;
}

double run_vectorized_avx_float_blend()
{
    float *in = _mm_malloc(sizeof(*in) * n, 32);
    float *out = _mm_malloc(sizeof(*out) * n, 32);
    srand(0);
    for (int i = 0; i < n; i++) {
        in[i] = rand() > RAND_MAX / 2 ? 0 : rand() / (float)RAND_MAX * 1000.0;
    }
    double t = wtime();
    compute_sqrt_avx_float_blend(in, out, n);
    t = wtime() - t;

#if 0
    for (int i = 0; i < n; i++)
        printf("%.4f ", out[i]);
    printf("\n");
#endif

    for (int i = 0; i < n; i++) {
        float r = in[i] > 0 ? sqrtf(in[i]) : 0.0;
        if (fabs(out[i] - r) > EPS) {
            fprintf(stderr, "(AVX_FLOAT_BLEND)Verification: FAILED at out[%d] = %f != %f\n", i, out[i], r);
            break;
        }
    }

    printf("Elapsed time (vectorized AVX_FLOAT_BLEND): %.6f sec.\n\n", t);
    free(in);
    free(out);
    return t;
}

double run_vectorized_avx_double()
{
    double *in = _mm_malloc(sizeof(*in) * n, 32);
    double *out = _mm_malloc(sizeof(*out) * n, 32);
    srand(0);
    for (int i = 0; i < n; i++) {
        in[i] = rand() > RAND_MAX / 2 ? 0 : rand() / (float)RAND_MAX * 1000.0;
    }
    double t = wtime();
    compute_sqrt_avx_double(in, out, n);
    t = wtime() - t;

#if 0
    for (int i = 0; i < n; i++)
        printf("%.4f ", out[i]);
    printf("\n");
#endif

    for (int i = 0; i < n; i++) {
        float r = in[i] > 0 ? sqrt(in[i]) : 0.0;
        if (fabs(out[i] - r) > EPS) {
            fprintf(stderr, "(AVX_DOUBLE)Verification: FAILED at out[%d] = %f != %f\n", i, out[i], r);
            break;
        }
    }

    printf("Elapsed time (vectorized AVX_DOUBLE): %.6f sec.\n\n", t);
    free(in);
    free(out);
    return t;
}

double run_vectorized_avx_double_blend()
{
    double *in = _mm_malloc(sizeof(*in) * n, 32);
    double *out = _mm_malloc(sizeof(*out) * n, 32);
    srand(0);
    for (int i = 0; i < n; i++) {
        in[i] = rand() > RAND_MAX / 2 ? 0 : rand() / (float)RAND_MAX * 1000.0;
    }
    double t = wtime();
    compute_sqrt_avx_double_blend(in, out, n);
    t = wtime() - t;

#if 0
    for (int i = 0; i < n; i++)
        printf("%.4f ", out[i]);
    printf("\n");
#endif

    for (int i = 0; i < n; i++) {
        float r = in[i] > 0 ? sqrt(in[i]) : 0.0;
        if (fabs(out[i] - r) > EPS) {
            fprintf(stderr, "(AVX_DOUBLE_BLEND)Verification: FAILED at out[%d] = %f != %f\n", i, out[i], r);
            break;
        }
    }

    printf("Elapsed time (vectorized AVX_DOUBLE_BLEND): %.6f sec.\n\n", t);
    free(in);
    free(out);
    return t;
}

int main(int argc, char **argv)
{
    printf("Tabulate sqrt: n = %d\n\n", n);
    double tscalar = run_scalar();
    double tvec_avx_float = run_vectorized_avx_float();
    double tvec_avx_float_blend = run_vectorized_avx_float_blend();
    double tvec_avx_double = run_vectorized_avx_double();
    double tvec_avx_double_blend = run_vectorized_avx_double_blend();

    printf("Speedup AVX_FLOAT: %.2f\n", tscalar / tvec_avx_float);
    printf("Speedup AVX_FLOAT_BLEND: %.2f\n\n", tscalar / tvec_avx_float_blend);
    printf("Speedup AVX_DOUBLE: %.2f\n", tscalar / tvec_avx_double);
    printf("Speedup AVX_DOUBLE_BLEND: %.2f\n", tscalar / tvec_avx_double_blend);
    return 0;
}
