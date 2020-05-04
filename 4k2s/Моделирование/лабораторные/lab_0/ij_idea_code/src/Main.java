import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {

        int totalNumberOfGeneratedNumbers = 1000000;
        int countInterval = 1000;


        Random random = new Random();
        GeneratorTester.RandomGenerator randomGenerator = new GeneratorTester.SplittableRandomShell();
//        RandomGenerator randomGenerator = new RandomShell();
//        RandomGenerator randomGenerator = new SecureRandomShell();

        GeneratorTester generatorTester = new GeneratorTester(totalNumberOfGeneratedNumbers, countInterval, randomGenerator);
    }


}
