/**
 * Program Assignment: Prog7
 * Name: Liang Jiaming
 * Date: 7/13/2016
 * Description: A program to calculate the three-variable multiple-regression estimating parameters,
 *              make an estimate from user-supplied inputs, and determine the 70% prediction intervals for the estimate.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog7
 *   Source code in Prog7.java:
 *     Prog7
 *       main(String[] args)
 *       gaussian(double[][] matrix)
 *       calculateZ(double[] betas, double wk, double xk, double yk)
 *       calculateRange(double[] betas, double[] wi, double[] xi, double[] yi, double[] zi,
 *                      double wk, double xk, double yk)
 *       double mean(double[] numbers)
 *       double sum(double[] numbers)
 *       double sumMultiply(double[] xNumbers, double[] yNumbers)
 *       tDistribution(double x, double dof)
 *       gamma(double x)
 */

import java.text.DecimalFormat;

/* CLASS BEGIN: Prog7 */
public class Prog7 {
    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        double[] wi = {1142, 863, 1065, 554, 983, 256};
        double[] xi = {1060, 995, 3205, 120, 2896, 485};
        double[] yi = {325, 98, 23, 0, 120, 88};
        double[] zi = {201, 98, 162, 54, 138, 61};
        double wk = 650;
        double xk = 3000;
        double yk = 155;
        // calculate betas
        DecimalFormat dFormat = new DecimalFormat("0.0000");
        double[] betas = gaussian(new double[][]{
                {zi.length, sum(wi), sum(xi), sum(yi), sum(zi)},
                {sum(wi), sumMultiply(wi, wi), sumMultiply(wi, xi), sumMultiply(wi, yi), sumMultiply(wi, zi)},
                {sum(xi), sumMultiply(wi, xi), sumMultiply(xi, xi), sumMultiply(xi, yi), sumMultiply(xi, zi)},
                {sum(yi), sumMultiply(wi, yi), sumMultiply(xi, yi), sumMultiply(yi, yi), sumMultiply(yi, zi)}
        });
        System.out.println("Beta-0 = " + dFormat.format(betas[0]));
        System.out.println("Beta-1 = " + dFormat.format(betas[1]));
        System.out.println("Beta-2 = " + dFormat.format(betas[2]));
        System.out.println("Beta-3 = " + dFormat.format(betas[3]));
        // calculate zk
        dFormat = new DecimalFormat("0.0");
        double zk = calculateZ(betas, wk, xk, yk);
        System.out.println("Projected Hours = " + dFormat.format(zk));
        // calculate UPI and LPI
        double range = calculateRange(betas, wi, xi, yi, zi, wk, xk, yk);
        System.out.println("UPI (70%) = " + dFormat.format(zk + range));
        System.out.println("LPI (70%) = " + dFormat.format(zk - range));
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double[] gaussian(double[][] matrix)
     *     Purpose: to solve equation using Gaussian elimination procedure.
     *     Limitations: the matrix's size should be (n)*(n+1), in which n is the number of unknowns.
     *     Return: the Beta parameters of the equation.
     */
    /* METHOD BEGIN: gaussian */
    public static double[] gaussian(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = i+1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[max][i]))
                    max = j;
            }
            // swap
            double[] t = matrix[i];
            matrix[i] = matrix[max];
            matrix[max] = t;
            for (int j = i+1; j < n; j++) {
                double a = matrix[j][i] / matrix[i][i];
                matrix[j][n] -= a * matrix[i][n];
                for (int k = i; k < n; k++)
                    matrix[j][k] -= a * matrix[i][k];
            }
        }
        // substitution
        double[] x = new double[n];
        for (int i = n-1; i >= 0; i--) {
            double sum = 0;
            for (int j = i+1; j < n; j++)
                sum += matrix[i][j] * x[j];
            x[i] = (matrix[i][n] - sum) / matrix[i][i];
        }
        return x;
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double calculateZ(double[] betas, double wk, double xk, double yk)
     *     Purpose: to calculate the Z_k value from given Beta parameters.
     *     Limitations: the Beta parameters and W_k, X_k, Y_k should be of double type.
     *     Return: the Z_k value.
     */
    /* METHOD BEGIN: calculateZ */
    public static double calculateZ(double[] betas, double wk, double xk, double yk) {
        return betas[0] + wk * betas[1] + xk * betas[2] + yk * betas[3];
    }
    /* METHOD END */

    /**
     * Reuse instructions
     *   double calculateRange(double[] betas, double[] wi, double[] xi, double[] yi, double[] zi,
     *                         double wk, double xk, double yk)
     *     Purpose: to calculate the prediction range from given Beta parameters.
     *     Limitations: all the parameters should be of double type.
     *     Return: the prediction range value.
     */
    /* METHOD BEGIN: calculateRange */
    public static double calculateRange(double[] betas, double[] wi, double[] xi, double[] yi, double[] zi,
                                        double wk, double xk, double yk) {
        double dof = zi.length - 4;
        double sigma = 0, wSigma = 0, xSigma = 0, ySigma = 0;
        for (int i = 0; i < zi.length; i++) {
            sigma += Math.pow(zi[i] - betas[0] - betas[1] * wi[i] - betas[2] * xi[i] - betas[3] * yi[i], 2);
            wSigma += Math.pow(wi[i] - mean(wi), 2);
            xSigma += Math.pow(xi[i] - mean(xi), 2);
            ySigma += Math.pow(yi[i] - mean(yi), 2);
        }
        sigma = Math.round(calculateX(0.35, 0.00001, dof) * 1000) / 1000.0 * Math.sqrt(sigma / dof);
        return sigma * Math.sqrt(1 + 1.0 / zi.length
                + Math.pow(wk - mean(wi), 2) / wSigma
                + Math.pow(xk - mean(xi), 2) / xSigma
                + Math.pow(yk - mean(yi), 2) / ySigma
        );
    }
    /* METHOD END */

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
     *   double sum(double[] numbers)
     *     Purpose: to calculate the sum of an array of numbers.
     *     Limitations: the numbers should be of double type.
     *     Return: the sum of the array.
     */
    /* METHOD BEGIN: sum */
    public static double sum(double[] numbers) {
        double result = 0.0;
        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }
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

    /* METHOD BEGIN: calculateX */
    public static double calculateX(double p, double errorRange, double dof) {
        double x = 0;
        double d = 0.5;
        double error = integrate(x, errorRange, dof) - p;
        while (Math.abs(error) >= errorRange) {
            if (error < 0) x += d;
            else x -= d;
            double newError = integrate(x, errorRange, dof) - p;
            // check if the sign of error changes
            if (Math.signum(newError) != Math.signum(error))
                d /= 2;
            error = newError;
        }
        return x;
    }
    /* METHOD END */

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
