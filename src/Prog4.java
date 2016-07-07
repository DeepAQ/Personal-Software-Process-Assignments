/**
 * Program Assignment: Prog4
 * Name: Liang Jiaming
 * Date: 7/7/2016
 * Description: A program to calculate relative size ranges for very small, small, medium, large,
 *              and very large ranges using standard deviation.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog4
 *   Source code in Prog4.java:
 *     Prog4
 *       main(String[] args)
 *       calculateRanges(double[] data)
 *       mean(double[] numbers)
 *       std_dev(double[] numbers)
 */

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

/* CLASS BEGIN: Prog4 */
public class Prog4 {
    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sizes data: ");
        String line = scanner.nextLine();
        // divide numbers into an array
        String[] numbers = line.split(" ");
        double[] data = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            data[i] = Double.parseDouble(numbers[i]);
        }

        System.out.print("Enter quantities (empty if no need): ");
        line = scanner.nextLine();
        // divide numbers into an array
        numbers = line.split(" ");
        double[] quantities = new double[numbers.length];
        Arrays.fill(quantities, 1.0);
        for (int i = 0; i < numbers.length; i++) {
            try {
                quantities[i] = Double.parseDouble(numbers[i]);
            } catch (Exception ignored) {}
        }

        // calculate real datas
        for (int i = 0; i < data.length; i++) {
            if (i < quantities.length) data[i] /= quantities[i];
        }
        // calculate relative ranges
        double[] results = calculateRanges(data);
        DecimalFormat dFormat = new DecimalFormat("0.0000");
        System.out.println("Very small: " + dFormat.format(results[0]));
        System.out.println("Small: " + dFormat.format(results[1]));
        System.out.println("Medium: " + dFormat.format(results[2]));
        System.out.println("Large: " + dFormat.format(results[3]));
        System.out.println("Very large: " + dFormat.format(results[4]));
    }
    /* METHOD END */

    /**
     * The program section calculates the relative size ranges.
     */

    /**
     * Reuse instructions
     *   double[] calculateRanges(double[] numbers)
     *     Purpose: to calculate relative size ranges of given data.
     *     Limitations: the data should be numbers of double type.
     *     Return: a double type array contains very small, small, medium, large, and very large ranges.
     */
    /* METHOD BEGIN: calculateRanges */
    public static double[] calculateRanges(double[] data) {
        double[] results = new double[5];
        // log-normally transform the data
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.log(data[i]);
        }
        // calculate the average and std. dev of logarithmic values
        double average = mean(data);
        double variance = std_dev(data);
        // calculate relative size ranges
        results[0] = Math.exp(average - 2 * variance); // very small
        results[1] = Math.exp(average - variance); // small
        results[2] = Math.exp(average); // medium
        results[3] = Math.exp(average + variance); // large
        results[4] = Math.exp(average + 2 * variance); // very large
        return results;
    }
    /* METHOD END */

    /**
     * The program section calculates the mean value and the std. dev value.
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
     *   double std_dev(double[] numbers)
     *     Purpose: to calculate the std. dev value of a set of real numbers.
     *     Limitations: the real numbers should be of double type.
     *     Return: the std. dev value of the given numbers.
     */
    /* METHOD BEGIN: std_dev */
    public static double std_dev(double[] numbers) {
        double avg = mean(numbers);
        double result = 0.0;
        for (double number : numbers) {
            result += (number - avg) * (number - avg);
        }
        result /= (numbers.length - 1);
        result = Math.sqrt(result);
        return result;
    }
    /* METHOD END */
}
/* CLASS END */
