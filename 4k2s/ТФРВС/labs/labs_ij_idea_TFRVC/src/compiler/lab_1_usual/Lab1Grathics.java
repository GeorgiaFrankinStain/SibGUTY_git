package compiler.lab_1_usual;

import GFSLibrary.StepModificator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Lab1Grathics {

    public enum Formula {
        averageTimeRecoveryTau,
        averageUptimeTheta
    }


    private String outputFolderStandtart = "D:\\SibGUTY_git\\4k2s\\ТФРВС\\labs\\out_data\\lab_1\\";


    public void task2p1() throws IOException {


        int nFrom = 65527;
        int nTo = 65537;

        int muFromIntensityFloodRecovery = 1;
        int muTo = 1001;
        StepModificator stepModificator = (i) -> i * 10;


        int NAllCalculator = 65536;
        double lambdalambdaIntendityFloodFailuresMachine = 0.00001;
        int m = 1;


        String generalTitleFile = "outputAverageUptimeTheta_2.1";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int mu = muFromIntensityFloodRecovery; mu < muTo; mu = stepModificator.next(mu)) {

            String titleLine = String.valueOf(mu);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageUptimeTheta);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambdalambdaIntendityFloodFailuresMachine, mu, n, NAllCalculator, m);
            }

            formulaWriterFile.close();
        }

    }

    public void task2p2() throws IOException {


        int nFrom = 65527;
        int nTo = 65537;

        int lambdaPowFrom = -9;
        int lambdaPowTo = -4;


        int N = 65536;
        int mu = 1;
        int m = 1;


        String generalTitleFile = "outputAverageUptimeTheta_2.2";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int iPow = lambdaPowFrom; iPow < lambdaPowTo; iPow++) {

            double lambda = Math.pow(10, iPow);

            String titleLine = String.valueOf(lambda);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageUptimeTheta);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambda, mu, n, N, m);
            }

            formulaWriterFile.close();
        }

    }

    public void task2p3() throws IOException {


        int nFrom = 65527;
        int nTo = 65537;

        int mFrom = 1;
        int mTo = 5;


        double lambda = Math.pow(10, -5);
        int N = 65536;
        int mu = 1;


        String generalTitleFile = "outputAverageUptimeTheta_2.3";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int m = mFrom; m < mTo; m++) {


            String titleLine = Integer.toString(m);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageUptimeTheta);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambda, mu, n, N, m);
            }

            formulaWriterFile.close();
        }

    }

    public void task3p1() throws IOException {
        int nFrom = 900;
        int nTo = 1001;

        int muFromIntensityFloodRecovery = 1;
        int muTo = 7;
        StepModificator stepModificator = (i) -> {
            if (i == 1)
                return i + 1;
            else
                return i + 2;
        };


        int NAllCalculator = 1000;
        double lambdalambdaIntendityFloodFailuresMachine = 0.001;
        int m = 1;


        String generalTitleFile = "outputAverageTimeRecoveryTau_3.1";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int mu = muFromIntensityFloodRecovery; mu < muTo; mu = stepModificator.next(mu)) {

            String titleLine = String.valueOf(mu);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageTimeRecoveryTau);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambdalambdaIntendityFloodFailuresMachine, mu, n, NAllCalculator, m);
            }

            formulaWriterFile.close();
        }

    }


    public void task3p2() throws IOException {
        int nFrom = 8092;
        int nTo = 8193;

        int lambdaPowFrom = -8;
        int lambdaPowTo = -4;
        StepModificator stepModificator = (i) -> i + 1;


        int mu = 1;
        int NAllCalculator = 8192;
        int m = 1;


        String generalTitleFile = "outputAverageTimeRecoveryTau_3.2";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int lambdaPow = lambdaPowFrom; lambdaPow < lambdaPowTo; lambdaPow = stepModificator.next(lambdaPow)) {

            double lambdalambdaIntendityFloodFailuresMachine = Math.pow(10, lambdaPow);
            String titleLine = String.valueOf(lambdalambdaIntendityFloodFailuresMachine);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageTimeRecoveryTau);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambdalambdaIntendityFloodFailuresMachine, mu, n, NAllCalculator, m);
            }

            formulaWriterFile.close();
        }

    }


    public void task3p3() throws IOException {
        int nFrom = 8092;
        int nTo = 8193;

        int mFrom = 1;
        int mTo = 5;


        double lambda = Math.pow(10, -5);
        int N = 8192;
        int mu = 1;


        String generalTitleFile = "outputAverageTimeRecoveryTau_3.3";
        CreatorFormulaWriterFile creatorFormulaWriterFile = new CreatorFormulaWriterFile(generalTitleFile);
        for (int m = mFrom; m < mTo; m++) {


            String titleLine = Integer.toString(m);
            FormulaWriterFile formulaWriterFile =
                    creatorFormulaWriterFile.createThetaWriterInfrastructure(titleLine, Formula.averageTimeRecoveryTau);

            for (int n = nFrom; n < nTo; n++) {
                formulaWriterFile.print(lambda, mu, n, N, m);
            }

            formulaWriterFile.close();
        }

    }


    // <start> <private_methods>
    private class CreatorFormulaWriterFile {
        private int numberLine = 1;
        private String generalTitleFile;

        public CreatorFormulaWriterFile(String generalTitleFile) {
            this.generalTitleFile = generalTitleFile;
        }


        public FormulaWriterFile createThetaWriterInfrastructure(String titleLine, Formula formula) throws IOException {
            PrintWriter output = createFile(generalTitleFile + "-" + numberLine + ".txt");
            FormulaWriterFile formulaWriterFile = new FormulaWriterFile(output, titleLine, formula);

            this.numberLine++;

            return formulaWriterFile;
        }
    }


    private class FormulaWriterFile {
        private PrintWriter output;
        Formula formula;


        public FormulaWriterFile(PrintWriter output) {
            this.output = output;
            this.formula = Formula.averageUptimeTheta;
        }


        public FormulaWriterFile(PrintWriter output, String title, Formula formula) {
            this.output = output;
            this.formula = formula;

            this.output.println(title);
        }

        public void print(double lambda, double mu, int n, int N, int m) {


            double result;
            if (this.formula == Formula.averageUptimeTheta)
                result = averageUptimeTheta(lambda, mu, n, N, m);
            else if (this.formula == Formula.averageTimeRecoveryTau)
                result = averageTimeRecoveryTau(lambda, mu, n, N, m);
            else {
                assert (false);
                result = -13;
            }


            output.printf(
                    "%7d %40.4f ",
                    n,
                    result
            );
            output.println();
            Random test;
        }

        public void close() {
            this.output.close();
        }
    }


    private double averageUptimeTheta(
            double lambdaIntendityFloodFailuresMachine,
            double muIntensityFloodRecovery,
            int nCountCalculator,
            int NAllCalculator,
            int mCountReserverCalculator
    ) {
        double totalSum = 0.0f;
        double totalMultiplication = 1.0f;
        for (int j = nCountCalculator + 1; j <= NAllCalculator; ++j) {
            double multiplication;


            boolean jMinus1IncludedInTheRange =
                    NAllCalculator - mCountReserverCalculator <= j - 1 && j - 1 <= NAllCalculator;
            if (jMinus1IncludedInTheRange) {
                multiplication = (NAllCalculator - (j - 1)) * muIntensityFloodRecovery;
            } else {
                multiplication = mCountReserverCalculator * muIntensityFloodRecovery;
            }
            totalMultiplication *= multiplication / ((j - 1) * lambdaIntendityFloodFailuresMachine);
            totalSum += totalMultiplication / (j * lambdaIntendityFloodFailuresMachine);
        }
        return totalSum + 1 / (nCountCalculator * lambdaIntendityFloodFailuresMachine);
    }

    private double averageTimeRecoveryTau(double lambda, double mu, int n, int N, int m) {
        if (n == 1)
            return m * mu;

        double firstItemMultiplicable = 1 / mu;
        for (int l = 1; l < n; l++) {
            firstItemMultiplicable *= lambda / mu;
        }


        double totalSumSecondItem = 0;
        for (int j = 1; j < n; j++) {
            double multiplicable = 1 / (j * lambda);

            for (int l = j; l < n; l++)
                multiplicable *= l * lambda / mu;


            totalSumSecondItem += multiplicable;
        }

        return firstItemMultiplicable + totalSumSecondItem;



/*        if (n == 1)
            return m * mu;

        double totalMul = 1.0f;
        for (int l = 1; l <= n - 1; ++l)
            totalMul *= l * lambda / (mu * l);

        double totalSum = 0.0f;
        for (int j = 1; j <= n - 1; ++j) {
            double totalMul2 = 1.0f;
            for (int l = j; l <= n - 1; ++l) {
                double mul = (l >= N - m && l <= N) ? (N - l) * mu : m * mu;
                totalMul2 *= l * lambda / mul;
            }
            totalSum += totalMul2 / (j * lambda);
        }
        return totalMul + totalSum;*/

/*        if (nCountCalculator == 1)
            return mCountReserverCalculator * muIntensityFloodRecovery;

        double totalMul = 1.0f;
        for (int l = 1; l <= nCountCalculator - 1; ++l)
            totalMul *= l * lambdaIntendityFloodFailuresMachine / (muIntensityFloodRecovery * l);

        double totalSum = 0.0f;
        for (int j = 1; j <= nCountCalculator - 1; ++j) {
            double totalMultiplicable = 1.0f;
            for (int l = j; l <= nCountCalculator - 1; ++l) {
                boolean oneInludedInTheRange = NAllCalculator - mCountReserverCalculator <= l && l <= NAllCalculator;
                double multiplicate;
                if (oneInludedInTheRange) {
                    multiplicate = (NAllCalculator - l) * muIntensityFloodRecovery;
                } else {
                    multiplicate = mCountReserverCalculator * muIntensityFloodRecovery;
                }

                totalMultiplicable *= l * lambdaIntendityFloodFailuresMachine / multiplicate;
            }
            totalSum += totalMultiplicable / (j * lambdaIntendityFloodFailuresMachine);
        }
        return totalMul + totalSum;*/
    }

    private PrintWriter createFile(String path) throws IOException {

        PrintWriter f = new PrintWriter(this.outputFolderStandtart + path, "UTF-8");

        return f;
    }
    // <end> <private_methods>
}
