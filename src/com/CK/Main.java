package com.CK;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

class Solution {
    int res = 0;

    public int totalNQueens(int n) {
        char[][] chess = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chess[i][j] = '.';
            }
        }
        solve(chess, 0);
        return res;
    }

    private void solve(char[][] chess, int row) {
        if (row == chess.length) {
            res++;
            return;
        }
        for (int col = 0; col < chess.length; col++) {
            if (valid(chess, row, col)) {
                chess[row][col] = 'Q';
                solve(chess, row + 1);
                chess[row][col] = '.';
            }
        }
    }

    private boolean valid(char[][] chess, int row, int col) {
        // check all cols
        for (int i = 0; i < row; i++) {
            if (chess[i][col] == 'Q') {
                return false;
            }
        }
        //check 45 degree
        for (int i = row - 1, j = col + 1; i >= 0 && j < chess.length; i--, j++) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        //check 135
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }
}

// Backtracking via bitmap
class Solution2 {
    public int backtrack(int row, int hills, int next_row, int dales, int count, int n) {
        /**
         row: current row to place the queen
         hills: "hill" diagonals occupation [1 = taken, 0 = free]
         next_row: free and taken slots for the next row [1 = taken, 0 = free]
         dales: "dale" diagonals occupation [1 = taken, 0 = free]
         count: number of all possible solutions
         */

        // all columns available for this board,
        // i.e. n times '1' in binary representation
        // bin(cols) = 0b1111 for n = 4, bin(cols) = 0b111 for n = 3
        // [1 = available]
        int columns = (1 << n) - 1;

        if (row == n)   // if all n queens are already placed
            count++;  // we found one more solution
        else {
            // free columns in the current row
            // ! 0 and 1 are inversed with respect to hills, next_row and dales
            // [0 = taken, 1 = free]
            int free_columns = columns & ~(hills | next_row | dales);

            // while there's still a column to place next queen
            while (free_columns != 0) {
                // the first bit '1' in a binary form of free_columns
                // on this column we will place the current queen
                int curr_column = - free_columns & free_columns;

                // place the queen
                // and exclude the column where the queen is placed
                free_columns ^= curr_column;

                count = backtrack(row + 1,
                        (hills | curr_column) << 1,
                        next_row | curr_column,
                        (dales | curr_column) >> 1,
                        count, n);
            }
        }

        return count;
    }
    public int totalNQueens(int n) {
        return backtrack(0, 0, 0, 0, 0, n);
    }
}