package compiler.lab_1;

import GFSLibrary.ConditionsCicleFor;
import GFSLibrary.ConditionsCicleForStandartConditionLess;
import GFSLibrary.CombinatorCicle;
import GFSLibrary.CombinatorCicleClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Lab1Grathics {

    private String outputFolderStandtart = "D:\\SibGUTY_git\\4k2s\\ТФРВС\\labs\\out_data\\lab_1\\";



    public void task2p1() throws IOException {

        int N = 65536;
        double lambda = 0.00001;
        int m = 1;


        int nStart = 65527;
        int nEnd = 65537;
        ConditionsCicleFor.NextIndexLambda nNextFunction = (i) -> i + 1;

        int muStart = 1;
        int muEnd = 1001;
        ConditionsCicleFor.NextIndexLambda muNextFunction = (i) -> i * 10;








        Map<String, Double> staticData = new HashMap<String, Double>();
        staticData.put("N", (double) N);
        staticData.put("lambda", lambda);
        staticData.put("m", (double) m);

        Map<String, ConditionsCicleFor> combinationData = new HashMap<String, ConditionsCicleFor>();
        combinationData.put("mu", new ConditionsCicleForStandartConditionLess(muStart, muEnd, muNextFunction));
        combinationData.put("n", new ConditionsCicleForStandartConditionLess(nStart, nEnd, nNextFunction));








        PrintWriter output = createFile("output_2.1.txt");
        CombinatorCicle combinatorCicle = new CombinatorCicleClass(new ThetaLambdaWriter(output));

        combinatorCicle.callFunctionAllCombinationCicle(staticData, combinationData);

        output.close();
    }

    private class ThetaLambdaWriter implements CombinatorCicleClass.CombinerFunction {
        private PrintWriter output;



        public ThetaLambdaWriter(PrintWriter output) {
            this.output = output;

            output.println("                    averageUptimeTheta        lambda       mu       n       N         m");
        }

        @Override
        public void print(Map<String, Double> staticData) {
            double result = averageUptimeTheta(
                    staticData.get("lambda"),
                    staticData.get("mu"),
                    staticData.get("n").intValue(),
                    staticData.get("N").intValue(),
                    staticData.get("m").intValue()
            );


            output.printf(
                    "%7d %40.4f ",
                    staticData.get("n").intValue(),
                    result
            );
            output.println();


/*            output.printf(
                    "%40.4f %10.4f %10.4f %7d %7d %7d",
                    result,
                    staticData.get("lambda"),
                    staticData.get("mu"),
                    staticData.get("n").intValue(),
                    staticData.get("N").intValue(),
                    staticData.get("m").intValue()
            );
            output.println();*/

        }
    }

    ;

    private static double averageUptimeTheta(double lambda, double mu, int n, int N, int m) {
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

    private PrintWriter createFile(String path) throws IOException {
        PrintWriter f = new PrintWriter(this.outputFolderStandtart + path, "UTF-8");

        return f;
    }
    // <end> <private_methods>
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