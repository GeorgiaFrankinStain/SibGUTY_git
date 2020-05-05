import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        cicleTestingGenerator(new GeneratorTester.SplittableRandomShell());
        cicleTestingGenerator(new GeneratorTester.RandomShell());
        cicleTestingGenerator(new GeneratorTester.SecureRandomShell());
    }

    private static void cicleTestingGenerator(GeneratorTester.RandomGenerator randomGenerator) throws FileNotFoundException {
        for (
                int totalNumberOfGeneratedNumbers = 100000;
                totalNumberOfGeneratedNumbers < 1000001;
                totalNumberOfGeneratedNumbers *= 10
        ) {
            for (int countInterval = 100; countInterval < 1001; countInterval *= 10) {
                GeneratorTester generatorTester =
                        new GeneratorTester(totalNumberOfGeneratedNumbers, countInterval, randomGenerator);
                generatorTester.testing();

            }
        }
    }



}
