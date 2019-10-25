
// Title:           Ice.Java
// Purpose:         A program designed to use linear regression to predict the number of days
//                  Lake Mendota will be frozen.
// Author:          Bilal Yassine

import java.util.Random;

public class Ice {

    private static final int[] x = new int[2017 - 1855 + 1];
    private static final int[] y = new int[] {118, 151, 121, 96, 110, 117, 132, 104, 125, 118, 125,
            123, 110, 127, 131, 99, 126, 144, 136, 126, 91, 130, 62, 112, 99, 161, 78, 124, 119, 124,
            128, 131, 113, 88, 75, 111, 97, 112, 101, 101, 91, 110, 100, 130, 111, 107, 105, 89, 126,
            108, 97, 94, 83, 106, 98, 101, 108, 99, 88, 115, 102, 116, 115, 82, 110, 81, 96, 125, 104,
            105, 124, 103, 106, 96, 107, 98, 65, 115, 91, 94, 101, 121, 105, 97, 105, 96, 82, 116, 114,
            92, 98, 101, 104, 96, 109, 122, 114, 81, 85, 92, 114, 111, 95, 126, 105, 108, 117, 112, 113,
            120, 65, 98, 91, 108, 113, 110, 105, 97, 105, 107, 88, 115, 123, 118, 99, 93, 96, 54, 111,
            85, 107, 89, 87, 97, 93, 88, 99, 108, 94, 74, 119, 102, 47, 82, 53, 115, 21, 89, 80, 101,
            95, 66, 106, 97, 87, 109, 57, 87, 117, 91, 62, 65, 94};
    private static double mean(int[] arr) {
        return (double) sum(arr) / arr.length;
    }
    private static int sum(int[] arr) {
        int sum = 0;
        for (int x : arr) {
            sum += x;
        }
        return sum;
    }
    private static double standardDeviation(int[] arr) {
        double mean = mean(arr);
        double standardDeviation = 0;
        int n = arr.length;
        for (double x : arr) {
            standardDeviation += Math.pow((x - mean), 2);
        }
        standardDeviation *= 1.0 / (n - 1);
        standardDeviation = Math.sqrt(standardDeviation);
        return standardDeviation;
    }
    private static double intMSE(int[] x, int[] y, double beta0, double beta1) {
        int n = x.length;
        double MSE = 0;
        for (int i = 0; i < n; i++) {
            MSE += Math.pow(beta0 + beta1 * x[i] - y[i], 2);
        }
        MSE *= 1.0 / n;
        return MSE;
    }
    private static double[] intGradient(int[] x, int[] y, double beta0, double beta1) {
        int n = x.length;
        double grad0 = 0;
        double grad1 = 0;
        for (int i = 0; i < n; i++) {
            grad0 += beta0 + beta1 * x[i] - y[i];
            grad1 += (beta0 + beta1 * x[i] - y[i]) * x[i];
        }
        grad0 *= 2.0 / n;
        grad1 *= 2.0 / n;
        return new double[] {grad0, grad1};
    }
    private static double doubleMSE(double[] x, int[] y, double beta0, double beta1) {
        int n = x.length;
        double MSE = 0;
        for (int i = 0; i < n; i++) {
            MSE += Math.pow(beta0 + beta1 * x[i] - y[i], 2);
        }
        MSE *= 1.0 / n;
        return MSE;
    }
    private static double[] doubleGradient(double[] x, int[] y, double beta0, double beta1) {
        int n = x.length;
        double grad0 = 0;
        double grad1 = 0;
        for (int i = 0; i < n; i++) {
            grad0 += beta0 + beta1 * x[i] - y[i];
            grad1 += (beta0 + beta1 * x[i] - y[i]) * x[i];
        }
        grad0 *= 2.0 / n;
        grad1 *= 2.0 / n;
        return new double[] {grad0, grad1};
    }
    private static double[] standardize(int[] arr) {
        int n = arr.length;
        double[] std = new double[n];
        double mean = mean(arr);
        double sd = standardDeviation(arr);
        for (int i = 0; i < n; i++) {
            std[i] = (x[i] - mean) / sd;
        }
        return std;
    }
    private static double[] beta(int[] x, int[] y) {
        double beta0 = 0;
        double beta1 = 0;
        double xMean = mean(x);
        double yMean = mean(y);
        int n = x.length;
        double numerator = 0;
        double denominator = 0;
        for (int i = 0; i < n; i++) {
            numerator += (x[i] - xMean) * (y[i] - yMean);
            denominator += (x[i] - xMean) * (x[i] - xMean);
        }
        beta1 = numerator / denominator;
        beta0 = yMean - beta1 * xMean;
        return new double[] {beta0, beta1};
    }
    private static double[] stochasticGradientDescent(double[] x, int[] y, double beta0, double beta1, int rand) {
        double grad0 = 2*(beta0 + beta1*x[rand] - y[rand]);
        double grad1 = 2*(beta0 + beta1*x[rand] - y[rand])*x[rand];
        return new double[] {grad0, grad1};
    }
    public static void main(String[] args) {
        for (int i = 1855; i <= 2017; i++) {
            x[i - 1855] = i;
        }
        int n = x.length;
        try {
            int flag = Integer.parseInt(args[0]);
            if (flag == 100) {
                for (int i = 0; i < n; i++) {
                    System.out.printf("%d %d\n", x[i], y[i]);
                }
            } else if (flag == 200) {
                System.out.printf("%d\n", n);
                System.out.printf("%.2f\n", mean(y));
                System.out.printf("%.2f\n", standardDeviation(y));
            } else if (flag == 300) {
                double beta0 = Double.parseDouble(args[1]);
                double beta1 = Double.parseDouble(args[2]);
                System.out.printf("%.2f\n", intMSE(x, y, beta0, beta1));
            } else if (flag == 400) {
                double beta0 = Double.parseDouble(args[1]);
                double beta1 = Double.parseDouble(args[2]);
                double[] grad = intGradient(x, y, beta0, beta1);
                System.out.printf("%.2f\n", grad[0]);
                System.out.printf("%.2f\n", grad[1]);
            } else if (flag == 500) {
                double eta = Double.parseDouble(args[1]);
                int time = Integer.parseInt(args[2]);
                double beta0 = 0;
                double beta1 = 0;
                for (int i = 1; i <= time; i++) {
                    double[] intGradient = intGradient(x, y, beta0, beta1);
                    beta0 = beta0 - eta * intGradient[0];
                    beta1 = beta1 - eta * intGradient[1];
                    System.out.printf("%d %.2f %.2f %.2f\n", i, beta0, beta1,
                            intMSE(x, y, beta0, beta1));
                }
            } else if (flag == 600) {
                double[] betaArray = beta(x, y);
                System.out.printf("%.2f %.2f %.2f\n", betaArray[0], betaArray[1],
                        intMSE(x, y, betaArray[0], betaArray[1]));
            } else if (flag == 700) {
                double[] betaArray = beta(x, y);
                System.out.printf("%.2f\n", betaArray[0] + betaArray[1] * Integer.parseInt(args[1]));
            } else if (flag == 800) {
                double eta = Double.parseDouble(args[1]);
                int time = Integer.parseInt(args[2]);
                double[] standardized = standardize(x);
                double beta0 = 0;
                double beta1 = 0;
                for (int i = 1; i <= time; i++) {
                    double[] doubleGradient = doubleGradient(standardized, y, beta0, beta1);
                    beta0 = beta0 - eta * doubleGradient[0];
                    beta1 = beta1 - eta * doubleGradient[1];
                    System.out.printf("%d %.2f %.2f %.2f\n", i, beta0, beta1,
                            doubleMSE(standardized, y, beta0, beta1));
                }
            } else if (flag == 900) {
                Random random = new Random();
                double eta = Double.parseDouble(args[1]);
                int time = Integer.parseInt(args[2]);
                double[] standardized = standardize(x);
                double beta0 = 0;
                double beta1 = 0;
                for (int i = 1; i <= time; i++) {
                    double[] doubleGradient = stochasticGradientDescent(standardized, y, beta0,
                            beta1, random.nextInt(n));
                    beta0 = beta0 - eta * doubleGradient[0];
                    beta1 = beta1 - eta * doubleGradient[1];
                    System.out.printf("%d %.2f %.2f %.2f\n", i, beta0, beta1,
                            doubleMSE(standardized, y, beta0, beta1));
                }
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (Exception e) {
            System.out.println("Unsupported Operation Exception! Adjust your flag arguments.");
        }
    }
}