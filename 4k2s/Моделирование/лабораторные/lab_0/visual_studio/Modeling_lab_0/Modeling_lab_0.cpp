#include <stdio.h>
#include <time.h>
#include<stdlib.h>
#include <math.h>
#include<iostream>
#include <iomanip>
#include <random>
#include <ctime>
#include <cmath>
#include <chrono>
#define N 1000
using namespace std;
const float RAND_MAX_F = RAND_MAX;
float get_rand() {
	return rand() / RAND_MAX_F;
}
float get_rand_range(const float min, const float max) {
	return get_rand() * (max - min) + min;
}
int main() {
	int k = 5; int count[k]; int index = 0;
	float numeric[N];
	mt19937 gen(time(0));
	uniform_real_distribution<> urd(0, 1);
	unsigned seed =
		std::chrono::system_clock::now().time_since_epoch().count(); std::uniform_real_distribution<float> dist(0, 1); std::mt19937_64 rng(seed);
	8
		for (int i = 0; i < k; i++)
		{
			count[i] = 0;
		}
	srand(time(NULL)); float Q = 0.0;
	float sum = 0, sum_kvdr = 0;
	for (int i = 0; i < N; i++)
	{
		//float gch = get_rand_range(0, 1);
		//float gch = urd(gen);
		float gch = dist(rng);
		numeric[i] = gch;
		sum += gch;
		sum_kvdr += gch * gch;
		Q = 0.0;
		for (index = 0; index < k; index++) {
			if (gch > Q && gch <= Q + (float)1 / k) {
				count[index]++;
				break;
			}
			Q += (float)1 / k
		}
	}
	cout << "N = " << N << "\nk = " << k << "\n\n"; int interv = 1;
	for (int i = 0; i < k; i++) {
		interv++;
	}
	float HI = 0.0;
	for (int i = 0; i < k; i++) {
		HI += (float)count[i] * count[i] / (1 / (float)k);
	}
	HI /= (float)N; HI -= (float)N;
	cout << "\n" << "Хи-квадрат: " << HI << "\n"; float Ex = 0.0;
	Ex = sum / (float)N;
	float S = 0.0;
	S = (sum_kvdr / (float)N) - (Ex * Ex); float autoc = 0.0;
	for (int taaay = 1; taaay < 500; taaay++) {
		for (int i = 0; i < N - taaay; i++)
		{
			autoc += (float)((numeric[i] - Ex) * (float)(numeric[i + taaay] - Ex));
		}
		autoc /= (float)(N - taaay) * S;
	}
	cout << "Автокорреляция: " << autoc << "\n";
	return 0;
}