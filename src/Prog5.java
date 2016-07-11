/**
 * Program Assignment: Prog5
 * Name: Liang Jiaming
 * Date: 7/11/2016
 * Description: A program to numerically integrate t distribution function using Simpson's rule.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog5
 *   Source code in Prog5.java:
 *     Prog5
 *       main(String[] args)
 *       integrate(double x, double errorRange, double dof)
 *       tDistribution(double x, double dof)
 *       gamma(double x)
 */

import java.text.DecimalFormat;
import java.util.Scanner;

/* CLASS BEGIN: Prog5 */
public class Prog5 {
    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("x = ");
        double x = scanner.nextDouble();
        System.out.print("dof = ");
        double dof = scanner.nextDouble();
        // Format the output number
        DecimalFormat dFormat = new DecimalFormat("0.00000");
        System.out.println("Result is " + dFormat.format(integrate(x, 0.000001, dof)));
    }
    /* METHOD END */

    /**
     * The program section numerically integrates t distribution function using Simpson's rule.
     */

    /* METHOD BEGIN: integrate */
    public static double integrate(double x, double errorRange, double dof) {
        int numSeg = 2;
        double segWidth = x / numSeg;
        double oldResult = 0;
        double newResult;
        while (true) {
            newResult = 0;
            for (int i = 1; i <= numSeg - 1; i += 2)
                newResult += 4 * tDistribution(i * segWidth, dof);
            for (int i = 2; i < numSeg - 2; i += 2)
                newResult += 2 * tDistribution(i * segWidth, dof);
            newResult = segWidth / 3 * (tDistribution(0, dof) + newResult + tDistribution(x, dof));
            if (Math.abs(newResult - oldResult) < errorRange)
                return newResult;
            else {
                oldResult = newResult;
                numSeg *= 2;
                segWidth = x / numSeg;
            }
        }
    }
    /* METHOD END */

    /**
     * The program section provides t distribution function and Gamma function.
     */

    /**
     * Reuse instructions
     *   double tDistribution(double x, double dof)
     *     Purpose: to calculate the value of t distribution function F(x).
     *     Limitations: the x and degree of freedom should be of double type.
     *     Return: the value of t distribution function F(x).
     */
    /* METHOD BEGIN: tDistribution */
    public static double tDistribution(double x, double dof) {
        return gamma((dof + 1) / 2) / (Math.sqrt(dof * Math.PI) * gamma(dof / 2))
                * Math.pow(1 + x * x / dof, -(dof + 1) / 2);
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double gamma(double x)
     *     Purpose: to calculate the value of Gamma(x).
     *     Limitations: the x should be of double type.
     *     Return: the value of Gamma(x).
     */
    /* METHOD BEGIN: gamma */
    public static double gamma(double x) {
        if (x == 1) return 1.0;
        else if (x == 0.5) return Math.sqrt(Math.PI);
        else return (x - 1) * gamma(x - 1);
    }
    /* METHOD END */
}
/* CLASS END */