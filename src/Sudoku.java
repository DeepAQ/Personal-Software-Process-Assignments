/**
 * Program Assignment: Sudoku
 * Name: Liang Jiaming
 * Date: 7/14/2016
 * Description: A program to solve Sudoku puzzle.
 */

/**
 * Listing Contents:
 *   Reuse instructions
 *   Modification instructions
 *   Compilation instructions
 *   Includes
 *   Class declarations:
 *     Sudoku
 *   Source code in Sudoku.java:
 *     Sudoku
 *       main(String[] args)
 *       trySolve(int[][] puzzle)
 *       canPut(int[][] puzzle, int x, int y, int number)
 */

import java.util.Scanner;

/* CLASS BEGIN: Sudoku */
public class Sudoku {
    /* METHOD BEGIN: main */
    public static void main(String args[]) {
        int[][] puzzle = new int[9][9];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the puzzle(9x9), use 0 in blanks:");
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                puzzle[i][j] = scanner.nextInt();
        if (!trySolve(puzzle)) {
            System.out.println("No Solutions!");
        } else {
            System.out.println("Solution:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    System.out.print(puzzle[i][j] + " ");
                System.out.println();
            }
        }
    }
    /* METHOD END */

    /* METHOD BEGIN: trySolve */
    public static boolean trySolve(int[][] puzzle) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (puzzle[i][j] == 0) {
                    for (int k = 1; k <= 9; k++)
                        if (canPut(puzzle, i, j, k)) {
                            // try k
                            puzzle[i][j] = k;
                            if (trySolve(puzzle)) return true;
                            // no solve with k, restore
                            puzzle[i][j] = 0;
                        }
                    // no solve with any number
                    return false;
                }
        return true;
    }
    /* METHOD END */

    /* METHOD BEGIN: canPut */
    public static boolean canPut(int[][] puzzle, int x, int y, int number) {
        for (int i = 0; i < 9; i++) {
            if (puzzle[x][i] == number) return false;
            if (puzzle[i][y] == number) return false;
        }
        int blockX = x / 3;
        int blockY = y / 3;
        for (int i = blockX * 3; i < blockX * 3 + 3; i++)
            for (int j = blockY * 3; j < blockY * 3 + 3; j++)
                if (puzzle[i][j] == number) return false;
        return true;
    }
    /* METHOD END */
}
/* CLASS END */