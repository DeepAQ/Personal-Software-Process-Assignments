import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by adn55 on 16/7/4.
 */
public class Prog1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the numbers: ");
        String line = scanner.nextLine();
        String[] numbers = line.split(" ");
        double[] numbersArray = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numbersArray[i] = Double.parseDouble(numbers[i]);
        }
        DecimalFormat dFormat = new DecimalFormat("#.##");
        System.out.println("The mean is: " + dFormat.format(mean(numbersArray)));
        System.out.println("The std. dev is: " + dFormat.format(std_dev(numbersArray)));
    }

    public static double mean(double[] numbers) {
        double result = 0.0;
        for (double number : numbers) {
            result += number;
        }
        result /= numbers.length;
        return result;
    }

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
}
