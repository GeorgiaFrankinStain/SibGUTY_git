import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    public static double f(double x) {
        if (x >= 1 && x <= 3) return (x * x * x - 1) / 18;
        else return 0;
    }

    public static void rejection(int max_n) throws IOException {
        double a = 1, b = 3, c = f(b), xsi1, xsi2;
        String str = null;
        FileWriter file = new FileWriter("file1.txt");
        for (int i = 0; i < max_n; i++) {
            xsi1 = new SplittableRandom().nextDouble(0, max_n);
            xsi2 = new SplittableRandom().nextDouble(0, max_n);
            double def = a + ((b - a) * xsi1);
            if (def > (c * xsi2)) {
                double fabs_fun = abs(f(def));
                str += def + " " + fabs_fun;
                file.write(str);
            }
        }
        file.close();
    }

    public static void with_return(int max_n, int n) throws IOException {
        double[] probability = new double[n];
        double[] hit_to_int = new double[n];
        double chance_to_minus = 1;
        for (int i = 0; i < n; i++) probability[i] = 1 / n;
        for (int i = 0; i < n; i++) {
            double rand_num1 = new SplittableRandom().nextDouble(0, max_n);
            probability[i] = abs((rand_num1 % 1));
            chance_to_minus -= probability[i];
        }
        probability[n - 1] = chance_to_minus;
        for (int i = 0; i < n; i++) hit_to_int[i] = 0;
        for (int i = 0; i < max_n * 100; i++) {
            double rand_num2 = new SplittableRandom().nextDouble(0, max_n);
            double summa = 0.0;
            for (int j = 0; j < n; j++) {
                summa += probability[j];
                if (rand_num2 < summa) {
                    hit_to_int[j] += 1;
                    break;
                }
            }
        }
        String str = null;
        FileWriter file = new FileWriter("file2.txt");
        for (int i = 0; i < n; i++) {
            str += i + 1 + " " + i + 1.5 + max_n * 100 * probability[i] + " " + hit_to_int[i];
            file.write(str);
        }
        file.close();
    }

    public static void without_return(int max_n, int n) throws IOException {
        TreeMap<Integer, Integer> test = new TreeMap<Integer, Integer>();


        List<Integer> array_num1 = new ArrayList<Integer>();
        List<Integer> array_num2 = new ArrayList<Integer>();
        int k = 3 * n / 4, max_n_to_def = (max_n * 100 / k) + 1;
        double[] hit_to_int = new double[n];
        for (int i = 0; i < n; i++) {
            hit_to_int[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            array_num2.add(i);
        }
        for (int i = 0; i < max_n_to_def; i++) {
            array_num1 = array_num2;
            if (i == max_n_to_def - 1) k = (max_n * 100) % k;
            for (int j = 0; j < k; j++) {
                float p = (float) (1.0 / (n - j));
                float num_rand = (float) new SplittableRandom().nextDouble(0, max_n * 100);
                int num_rand_to = (int) (num_rand / p);
                hit_to_int[array_num1.get(num_rand_to)] += 1;
                array_num1.remove(num_rand_to);
            }
        }
        String str = null;
        FileWriter file = new FileWriter("file3.txt");
        for (int i = 0; i < n; i++) {
            str += i + 1 + " " + i + 1.5 + " " + hit_to_int[i] + " " + max_n * 100 / 20;
            file.write(str);
        }
        file.close();
    }

    public static void main(String[] args) throws IOException {
        int max_n = 5000, n = 10;
        rejection(max_n);
        with_return(max_n, n);
        without_return(max_n, n);
    }
}