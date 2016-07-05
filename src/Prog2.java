/**
 * Program Assignment: Prog2
 * Name: Liang Jiaming
 * Date: 7/5/2016
 * Description: A program to read codes from a file and count the number of classes, methods and LOCs.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Prog2
 *   Source code in Prog2.java:
 *     Prog2
 *       main(String[] args)
 *       countCode(String code)
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* CLASS BEGIN: Prog2 */
public class Prog2 {
    private static int methodLoc = 0, classLoc = 0, totalLoc = 0;
    private static int methodNum = 0, classNum = 0, methodNumTotal = 0;
    private static boolean inClass = false, inMethod = false, inComment = false;
    private static String className = "", methodName = "";

    /* METHOD BEGIN: main */
    public static void main(String[] args) {
        // Read code from file
        try {
            FileInputStream inputStream = new FileInputStream("Prog1_New.java");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String code = new String(buffer);
            countCode(code);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading from file error!");
        }
    }
    /* METHOD END */

    /**
     * The program section counts the number of classes, methods and LOCs in the code.
     */

    /**
     * Reuse instructions
     *   void countCode(String code)
     *     Purpose: to count the number of classes, methods and LOCs in the code.
     *     Limitations: the code should match the coding standard.
     *     Return: no return values.
     */
    /* METHOD BEGIN: countCode */
    public static void countCode(String code) {
        System.out.println("Program start");
        String[] lines = code.split("\\n"); // divide code into lines
        for (int i = 0; i < lines.length; i++) { // count each line
            String line = lines[i].trim();

            // parse class/method begin/end marks
            if (line.startsWith("/* CLASS BEGIN:")) {
                inClass = true;
                methodNum = 0;
                classLoc = 0;
                classNum++;
                className = line.substring(15, line.length() - 2).trim();
                System.out.println("\tClass " + className + " start");
            } else if (line.startsWith("/* CLASS END")) {
                inClass = false;
                methodNumTotal += methodNum;
                System.out.println("\tClass subtotal: " + methodNum + " methods, " + classLoc + " LOCs");
            } else if (line.startsWith("/* METHOD BEGIN:")) {
                inMethod = true;
                methodLoc = 0;
                methodNum++;
                methodName = line.substring(16, line.length() - 2).trim();
                System.out.print("\t\tMethod " + methodName);
            } else if (line.startsWith("/* METHOD END")) {
                inMethod = false;
                System.out.println(" : " + methodLoc + " LOCs");
            }

            // exclude comment lines and blank lines
            if (line.startsWith("/*")) {
                inComment = true;
                if (!line.contains("*/")) continue;
            }
            if (line.contains("*/") && line.indexOf("/*") < line.indexOf("*/")) {
                inComment = false;
                if (line.lastIndexOf("*/") == line.length() - 2) continue;
            }
            if (line.startsWith("//") || line.isEmpty() || inComment) continue;

            // count LOCs
            totalLoc++;
            if (inClass) classLoc++;
            if (inMethod) methodLoc++;
        }
        System.out.println("Program total: " + classNum + " classes, " + methodNumTotal + " methods, " + totalLoc + " LOCs");
    }
    /* METHOD END */
}
/* CLASS END */

