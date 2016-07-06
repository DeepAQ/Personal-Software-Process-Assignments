/**
 * Program Assignment: Prog3
 * Name: Liang Jiaming
 * Date: 7/6/2016
 * Description: A program to calculate the regression parameters and the correlation coefficient of given data.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog3
 *   Source code in Prog3.java:
 *     Prog3
 *       main(String[] args)
 *       calculateBeta0(double[] xNumbers, double[] yNumbers)
 *       calculateBeta1(double[] xNumbers, double[] yNumbers)
 *       calculateCoefficient(double[] xNumbers, double[] yNumbers)
 *       mean(double[] numbers)
 *       sumMultiply(double[] xNumbers, double[] yNumbers)
 */

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

/* CLASS BEGIN: Prog3 */
public class Prog3 {
    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // input Xi numbers
        System.out.print("Enter Xi numbers: ");
        String line = scanner.nextLine();
        // divide numbers into an array
        String[] numbers = line.split(" ");
        double[] xNumbers = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            xNumbers[i] = Double.parseDouble(numbers[i]);
        }

        // input Yi numbers
        System.out.print("Enter Yi numbers: ");
        line = scanner.nextLine();
        // divide numbers into an array
        numbers = line.split(" ");
        double[] yNumbers = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            yNumbers[i] = Double.parseDouble(numbers[i]);
        }

        // input Xk value
        System.out.print("Enter Xk value: ");
        double xkNumber = scanner.nextDouble();

        // calculate and print results
        DecimalFormat dFormat = new DecimalFormat("0.0000");
        double beta0 = calculateBeta0(xNumbers, yNumbers);
        System.out.println("Beta-0 = " + dFormat.format(beta0));
        double beta1 = calculateBeta1(xNumbers, yNumbers);
        System.out.println("Beta-1 = " + dFormat.format(beta1));
        double coefficient = calculateCoefficient(xNumbers, yNumbers);
        System.out.println("r(x,y) = " + dFormat.format(coefficient));
        System.out.println("r^2 = " + dFormat.format(coefficient * coefficient));
        System.out.println("Y_k = " + dFormat.format(beta0 + beta1 * xkNumber));
    }
    /* METHOD END */

    /**
     * The program section calculates the regression parameters and the correlation coefficient.
     */

    /**
     * Reuse instructions
     *   double calculateBeta0(double[] xNumbers, double[] yNumbers)
     *     Purpose: to calculate the parameter Beta-0.
     *     Limitations: the numbers should be of double type, and the arrays should be the same length.
     *     Return: the parameter Beta-0.
     */
    /* METHOD BEGIN: calculateBeta0 */
    public static double calculateBeta0(double[] xNumbers, double[] yNumbers) {
        if (xNumbers.length != yNumbers.length) return 0.0;
        return mean(yNumbers) - calculateBeta1(xNumbers, yNumbers) * mean(xNumbers);
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double calculateBeta1(double[] xNumbers, double[] yNumbers)
     *     Purpose: to calculate the parameter Beta-1.
     *     Limitations: the numbers should be of double type, and the arrays should be the same length.
     *     Return: the parameter Beta-1.
     */
    /* METHOD BEGIN: calculateBeta1 */
    public static double calculateBeta1(double[] xNumbers, double[] yNumbers) {
        if (xNumbers.length != yNumbers.length) return 0.0;
        return (sumMultiply(xNumbers, yNumbers) - (xNumbers.length * mean(xNumbers) * mean(yNumbers)))
                / (sumMultiply(xNumbers, xNumbers) - (xNumbers.length * mean(xNumbers) * mean(xNumbers)));
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double calculateCoefficient(double[] xNumbers, double[] yNumbers)
     *     Purpose: to calculate the correlation coefficient.
     *     Limitations: the numbers should be of double type, and the arrays should be the same length.
     *     Return: the correlation coefficient.
     */
    /* METHOD BEGIN: calculateCoefficient */
    public static double calculateCoefficient(double[] xNumbers, double[] yNumbers) {
        if (xNumbers.length != yNumbers.length) return 0.0;
        double[] oneNumbers = new double[xNumbers.length];
        Arrays.fill(oneNumbers, 1.0);
        return (xNumbers.length * sumMultiply(xNumbers, yNumbers) - sumMultiply(xNumbers, oneNumbers) * sumMultiply(yNumbers, oneNumbers))
                / Math.sqrt(
                    (xNumbers.length * sumMultiply(xNumbers, xNumbers) - sumMultiply(xNumbers, oneNumbers) * sumMultiply(xNumbers, oneNumbers))
                     * (xNumbers.length * sumMultiply(yNumbers, yNumbers) - sumMultiply(yNumbers, oneNumbers) * sumMultiply(yNumbers, oneNumbers))
                  );
    }
    /* METHOD END */

    /**
     * The program section provides fundamental methods to calculate.
     */

    /**
     * Reuse instructions
     *   double mean(double[] numbers)
     *     Purpose: to calculate the mean value of a set of real numbers.
     *     Limitations: the real numbers should be of double type.
     *     Return: the mean value of the given numbers.
     */
    /* METHOD BEGIN: mean */
    public static double mean(double[] numbers) {
        double result = 0.0;
        for (double number : numbers) {
            result += number;
        }
        result /= numbers.length;
        return result;
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double sumMultiply(double[] xNumbers, double[] yNumbers)
     *     Purpose: to calculate the sum of (Xi * Yi).
     *     Limitations: the numbers should be of double type, and the arrays should be the same length.
     *     Return: the sum of (Xi * Yi).
     */
    /* METHOD BEGIN: sumMultiply */
    public static double sumMultiply(double[] xNumbers, double[] yNumbers) {
        double result = 0.0;
        if (xNumbers.length != yNumbers.length) return result;
        for (int i = 0; i < xNumbers.length; i++) {
            result += xNumbers[i] * yNumbers[i];
        }
        return result;
    }
    /* METHOD END */
}
/* CLASS END */