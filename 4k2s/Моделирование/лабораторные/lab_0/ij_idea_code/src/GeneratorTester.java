import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;

import static java.lang.Math.pow;

public class GeneratorTester {
    private static int numberFile = 0;
    int totalNumberOfGeneratedNumbers;
    int countInterval;
    RandomGenerator randomGenerator;

    double[] arrayRandomNumbers;
    double[] fallingNumbersInInterval;

    public GeneratorTester(int totalNumberOfGeneratedNumbers, int countInterval, RandomGenerator randomGenerator) {
        this.totalNumberOfGeneratedNumbers = totalNumberOfGeneratedNumbers;
        this.countInterval = countInterval;
        this.randomGenerator = randomGenerator;

        this.arrayRandomNumbers = new double[totalNumberOfGeneratedNumbers];
        this.fallingNumbersInInterval = new double[countInterval];
    }
//----------------------------------------------------------------------------------------------------------------------
    public void testing() throws FileNotFoundException {


        double sum = 0;
        double sumPow2RandomNumber = 0;
        for (int i = 0; i < totalNumberOfGeneratedNumbers; i++) {
            arrayRandomNumbers[i] = randomGenerator.nextDouble();

            sum += arrayRandomNumbers[i];
            sumPow2RandomNumber += arrayRandomNumbers[i] * arrayRandomNumbers[i];
            int indexInterval = (int) (arrayRandomNumbers[i] / (1.0 / countInterval));
            fallingNumbersInInterval[indexInterval]++;
        }


        System.out.println("Random Generator: " + randomGenerator.getClass());
        System.out.println("N = " + totalNumberOfGeneratedNumbers + ", k = " + countInterval);





        System.out.println("hiSquare: " + hiSquare());






        //Автокорреляция
        double mathematicalExpectationRandomNumber = sum / totalNumberOfGeneratedNumbers;
        double mathematicalExpectationRandomNumberInPow2 =
                mathematicalExpectationRandomNumber * mathematicalExpectationRandomNumber;

        double mathematicalExpectationOfPow2RandomNumber = (sumPow2RandomNumber / totalNumberOfGeneratedNumbers);


        double sampleVarianceSInPow2 =
                mathematicalExpectationOfPow2RandomNumber - mathematicalExpectationRandomNumberInPow2;


        double autocorelation = 0.0;
        PrintWriter printWriter = new PrintWriter(
                "D:\\SibGUTY_git\\4k2s\\Моделирование\\лабораторные\\lab_0\\output_data\\"
                + "a_" + numberFile + ".txt"
        );
        numberFile++;
        printWriter.println(
                "countInterval-" + countInterval
                        + "-N-" + totalNumberOfGeneratedNumbers
        );
        for (int offset = 1; offset < 30; offset++) {

            for (int i = 0; i < totalNumberOfGeneratedNumbers - offset; i++) {
                autocorelation +=
                        (arrayRandomNumbers[i] - mathematicalExpectationRandomNumber)
                                * (arrayRandomNumbers[i + offset] - mathematicalExpectationRandomNumber);
            }


            autocorelation /= (totalNumberOfGeneratedNumbers - offset) * sampleVarianceSInPow2;


            printWriter.print(offset + "    " + autocorelation + "\n");
//            printWriter.printf("%.10f\n", autocorelation);
//            printWriter.printf("%f\n", 0.5);
        }
        printWriter.close();
    }

    public interface RandomGenerator {
        public double nextDouble();
    }
    public static class SplittableRandomShell implements RandomGenerator {

        SplittableRandom random = new SplittableRandom();


        @Override
        public double nextDouble() {
            return random.nextDouble();
        }
    }
    public static class RandomShell implements RandomGenerator {

        Random random = new Random();


        @Override
        public double nextDouble() {
            return random.nextDouble();
        }
    }
    public static class SecureRandomShell implements RandomGenerator {

        SecureRandom random = new SecureRandom();


        @Override
        public double nextDouble() {
            return random.nextDouble();
        }
    }
    // <start> <private_method>

    private double hiSquare() {
        double hiSqare = 0;
        double theoreticalProbabilityOfNumbersFallingInOneInterval = 1.0 / countInterval;

        for (int i = 0; i < countInterval; i++) {
            hiSqare += pow(fallingNumbersInInterval[i], 2) / theoreticalProbabilityOfNumbersFallingInOneInterval;
        }
        hiSqare = (hiSqare / totalNumberOfGeneratedNumbers) - totalNumberOfGeneratedNumbers;
        return hiSqare;
    }

    // <end> <private_method>
}
