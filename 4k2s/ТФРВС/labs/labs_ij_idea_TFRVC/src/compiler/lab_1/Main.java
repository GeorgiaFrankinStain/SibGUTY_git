package compiler.lab_1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Math.pow;

public class Main {


    private static double theta(double lambda, double mu, int n, int N, int m) {
        double totalSum = 0.0f;
        double totalMul = 1.0f;
        for (int j = n + 1; j <= N; ++j) {
            double mul;
            if ((j - 1 >= N - m && j - 1 <= N)) {
                mul = (N - (j - 1)) * mu;
            } else {
                mul = m * mu;
            }
            totalMul *= mul / ((j - 1) * lambda);
            totalSum += totalMul / (j * lambda);
        }
        return totalSum + 1 / (n * lambda);
    }


    public static void main(String[] args) throws IOException {

        double lambda = 0.00001;
        int N = 65536;
        int m = 1;

        {
            PrintWriter file = createFile("1_dat.txt");


            file.println("Number n of elementary machines in base subsystem");
            for (int mu = 1; mu < 10000; mu *= 10) {
                file.print("\"mu = " + mu + " 1/hours\"    ");
            }
            file.println();

            for (int n = 65527; n <= N; ++n) {
                file.print(n + "    ");
                for (int mu = 1; mu != 10000; mu *= 10) {

                    file.print(theta(lambda, mu, n, N, m) + "    ");
                }
                file.println();
            }
            file.close();
        }

        {
            double mu = 1.0;
            PrintWriter file = createFile("2_dat.txt");


            file.println("Number n of elementary machines in base subsystem");
            for (int i = 5; i < 10; i++) {
                file.print("Lambda = 1E-" + i + " 1/hours");
            }
            file.println();

            for (int n = 65527; n <= N; ++n) {
                file.print(n + "    ");
                for (int i = -5; i >= -9; i--) {
                    file.print(theta(pow(10.0, i), mu, n, N, m) + "    ");
                }
                file.println();
            }
            file.close();
        }

        {

            double mu = 1.0;
            PrintWriter file = createFile("3_dat.txt");


            file.println("Number n of elementary machines in base subsystem");
            for (int i = 1; i < 5; i++) {
                file.print("m = " + i);
            }
            file.println();

            for (int n = 65527; n <= N; ++n) {
                file.print(n + "    ");
                for (int i = 1; i <= 4; i++) {
                    file.print(theta(lambda, mu, n, N, m) + "    ");
                }
                file.println();
            }
            file.close();
        }

        {
            fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
            for (int i = 1; i != 5; ++i)
                fprintf(f_out, "\"m = %d\"    ", i);
            fprintf(f_out, "\n");

            for (int n = 65527; n <= N; ++n) {
                fprintf(f_out, "%d    ", n);
                for (int i = 1; i <= 4; ++i)
                    fprintf(f_out, "%.6f    ", Theta(lambda, mu, n, N, i));
                fprintf(f_out, "\n");
            }
            fclose(f_out);
        }
    }


    private static PrintWriter createFile(String path) throws IOException {

        PrintWriter f = new PrintWriter("D:\\SibGUTY_git\\4k2s\\ТФРВС\\labs\\labs_ij_idea_TFRVC\\out_data\\" + path, "UTF-8");


        return f;
    }
}



/*
double Tau(double lambda, double mu, int n, int N, int m)
{
	if (n == 1)
		return m * mu;

	double totalMul = 1.0f;
	for (int l = 1; l <= n - 1; ++l)
		totalMul *= l * lambda / (mu * l);

	double totalSum = 0.0f;
	for (int j = 1; j <= n - 1; ++j) {
		double totalMul = 1.0f;
		for (int l = j; l <= n - 1; ++l) {
			double mul = (l >= N - m && l <= N) ? (N - l) * mu : m * mu;
			totalMul *= l * lambda / mul;
		}
		totalSum += totalMul / (j * lambda);
	}
	return totalMul + totalSum;
}

int main(int argc, char* argv[])
{
	double lambda = 0.00001;
	int N = 65536;
	int m = 1;

	FILE* f_out = fopen("1.dat", "w");
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int mu = 1; mu != 10000; mu *= 10)
			fprintf(f_out, "\"mu = %d 1/hours\"    ", mu);
		fprintf(f_out, "\n");

		for (int n = 65527; n <= N; ++n) {
			fprintf(f_out, "%d    ", n);
			for (int mu = 1; mu != 10000; mu *= 10)
				fprintf(f_out, "%.6f    ", Theta(lambda, mu, n, N, m));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}

	f_out = fopen("2.dat", "w");
	double mu = 1.0;
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int i = 5; i != 10; ++i)
			fprintf(f_out, "\"Lambda = 1E-%d 1/hours\"    ", i);
		fprintf(f_out, "\n");

		for (int n = 65527; n <= N; ++n) {
			fprintf(f_out, "%d    ", n);
			for (int i = -5; i >= -9; --i)
				fprintf(f_out, "%.6f    ", Theta(pow(10.0, i), mu, n, N, m));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}

	f_out = fopen("3.dat", "w");
	lambda = 0.00001;
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int i = 1; i != 5; ++i)
			fprintf(f_out, "\"m = %d\"    ", i);
		fprintf(f_out, "\n");

		for (int n = 65527; n <= N; ++n) {
			fprintf(f_out, "%d    ", n);
			for (int i = 1; i <= 4; ++i)
				fprintf(f_out, "%.6f    ", Theta(lambda, mu, n, N, i));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}

	f_out = fopen("4.dat", "w");
	lambda = 0.001;
	N = 1000;
	int mus[] = { 1, 2, 4, 6 };
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int i = 0; i != 5; ++i)
			fprintf(f_out, "\"Lambda = %d hours^-1\"    ", mus[i]);
		fprintf(f_out, "\n");

		for (int n = 900; n <= N; n += 10) {
			fprintf(f_out, "%d    ", n);
			for (int i = 0; i < 4; ++i)
				fprintf(f_out, "%.6f    ", Tau(lambda, mus[i], n, N, m));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}

	f_out = fopen("5.dat", "w");
	mu = 1.0;
	N = 8192;
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int i = 5; i != 10; ++i)
			fprintf(f_out, "\"Lambda = 1E-%d 1/hours\"    ", i);
		fprintf(f_out, "\n");

		for (int n = 8092; n <= N; n += 10) {
			fprintf(f_out, "%d    ", n);
			for (int i = -5; i>= -9; --i)
				fprintf(f_out, "%.6f    ", Tau(pow(10.0, i), mu, n, N, m));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}

	f_out = fopen("6.dat", "w");
	lambda = 0.00001;
	N = 8202;
	N = 8192;
	if (f_out) {
		fprintf(f_out, "\"Number n of elementary machines in base subsystem\"    ");
		for (int i = 1; i != 5; ++i)
			fprintf(f_out, "\"m = %d\"    ", i);
		fprintf(f_out, "\n");

		for (int n = 8092; n <= N; n += 10) {
			fprintf(f_out, "%d    ", n);
			for (int i = 1; i <= 4; ++i)
				fprintf(f_out, "%.6f    ", Tau(lambda, mu, n, N, i));
			fprintf(f_out, "\n");
		}
		fclose(f_out);
	}
	return EXIT_SUCCESS;
}

* */