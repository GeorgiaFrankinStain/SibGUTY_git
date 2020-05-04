import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;

import static java.lang.Math.pow;

public class GeneratorTester {
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

    public void testing() {


        double sum = 0;
        double sumSquare = 0;
        for (int i = 0; i < totalNumberOfGeneratedNumbers; i++) {
            arrayRandomNumbers[i] = randomGenerator.nextDouble();

            sum += arrayRandomNumbers[i];
            sumSquare += arrayRandomNumbers[i] * arrayRandomNumbers[i];
            int indexInterval = (int) (arrayRandomNumbers[i] / (1.0 / countInterval));
            fallingNumbersInInterval[indexInterval]++;
        }


        System.out.println("ГПСЧ: " + randomGenerator.getClass());
        System.out.println("N = " + totalNumberOfGeneratedNumbers + ", k = " + countInterval);





        System.out.println("Хи-квадрат: " + hiSquare());






        //Автокорреляция
        double mathematicalExpectation, S, autocorelation = 0.0;
        mathematicalExpectation = sum / totalNumberOfGeneratedNumbers;
        S = (sumSquare / totalNumberOfGeneratedNumbers) - (mathematicalExpectation * mathematicalExpectation);


        for (int offset = 1; offset < 25; offset++) {

            for (int i = 0; i < totalNumberOfGeneratedNumbers - offset; i++) {
                autocorelation += (arrayRandomNumbers[i] - mathematicalExpectation) * (arrayRandomNumbers[i + offset] - mathematicalExpectation);
            }


            autocorelation /= (totalNumberOfGeneratedNumbers - offset) * S;
            System.out.print("τ = " + offset);
            System.out.printf(" autocorelation = %.10f\n", autocorelation);
        }
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
