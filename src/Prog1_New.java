/**
 * Program Assignment: Prog1
 * Name: Liang Jiaming
 * Date: 7/5/2016
 * Description: A program to read a set of real numbers from keyboard, and calculate their mean and std. dev values.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog1_New
 *   Source code in Prog1_New.java:
 *     Prog1_New
 *       main(String[] args)
 *       mean(double[] numbers)
 *       std_dev(double[] numbers)
 */

import java.text.DecimalFormat;
import java.util.Scanner;

/* CLASS BEGIN: Prog1_New */
public class Prog1_New {
    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the numbers: ");
        String line = scanner.nextLine();

        // divide numbers into an array
        String[] numbers = line.split(" ");
        double[] numbersArray = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numbersArray[i] = Double.parseDouble(numbers[i]);
        }

        DecimalFormat dFormat = new DecimalFormat("#.##"); // format the output number
        System.out.println("The mean is: " + dFormat.format(mean(numbersArray)));
        System.out.println("The std. dev is: " + dFormat.format(std_dev(numbersArray)));
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
