package compiler.lab_1;

import myLibrary.ConditionsCicleFor;

import java.io.IOException;
import java.io.PrintWriter;

public class GrathicLab1 {
    private double lambda;
    private int N;
    private int m;
    private String nameFile;
    private ConditionsCicleFor conditionCicle1;
    private ConditionsCicleFor conditionCicle2;
    private ConditionsCicleFor conditionCicle3;


    private String standartFolder = "D:\\SibGUTY_git\\4k2s\\ТФРВС\\labs\\labs_ij_idea_TFRVC\\out_data\\";


    public void outputGrahicStandartFolder() throws IOException {
        PrintWriter file = createFile();


        file.println("Number n of elementary machines in base subsystem");
        for (int i = conditionCicle1.getStart(); conditionCicle1.continueCicle(i); i = conditionCicle1.next(i)) {
            file.print("\"i = " + i + " 1/hours\"    ");
        }
        file.println();

        for (int n = conditionCicle2.getStart(); conditionCicle2.continueCicle(n); n = conditionCicle2.next(n)) {
            file.print(n + "    ");
            for (int i = conditionCicle3.getStart(); conditionCicle3.continueCicle(i); n = conditionCicle3.next(i)) {
                file.print(theta(lambda, i, n, N, m) + "    ");
            }
            file.println();
        }
        file.close();
    }





    // <start> <private_methods>
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

    private PrintWriter createFile() throws IOException {

        PrintWriter f = new PrintWriter(this.standartFolder + this.nameFile, "UTF-8");

        return f;
    }
    // <end> <private_methods>
}
