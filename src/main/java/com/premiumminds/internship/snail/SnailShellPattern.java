package com.premiumminds.internship.snail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aamado on 05-05-2023.
 */
class SnailShellPattern implements ISnailShellPattern {

  private ExecutorService executor = Executors.newSingleThreadExecutor();


  /**
   * Method to get snailshell pattern of a matrix (recursive).
   * 
   * @param matrix Matrix of numbers to go through
   * @param matrix_layer Current layer of the matrix
   * @param init_n Initial size of the matrix (n*n)
   * @param curr Current array of values
   * @param curr_ix Current index of the array
   * @return Array of values that represent a snail shell pattern
   */
  private int[] getSnailShellAux(int[][] matrix, int matrix_layer, int init_n, int[] curr, int curr_len, int curr_ix) {
    int n = init_n;

    while (true) {

      // If the matrix is odd and we are in the middle, we must add the
      // last value to our array and break the loop.
      if (init_n%2 == 1 && matrix_layer == init_n/2) {
        curr[curr_ix] = matrix[matrix_layer][matrix_layer];
        break;
      }

      // Add the first row of the matrix to our array.
      for (int i = matrix_layer; i < n; i++) {
        curr[curr_ix] = matrix[matrix_layer][i];
        curr_ix++;
      }
  
      // After adding any row or column, we must check if we have
      // reached the end of the array.
      if (curr_ix == curr_len) {
        break;
      }
  
      // Add the last column of the matrix to our array.
      for (int i = matrix_layer+1; i < n; i++) {
        curr[curr_ix] = matrix[i][n-1];
        curr_ix++;
      }
  
      if (curr_ix == curr_len) {
        break;
      }
  
      // Add the last row of the matrix to our array.
      for (int i = n-2; i >= matrix_layer; i--) {
        curr[curr_ix] = matrix[n-1][i];
        curr_ix++;
      }
  
      if (curr_ix == curr_len) {
        break;
      }
  
      // Add the first column of the matrix to our array.
      for (int i = n-2; i > matrix_layer; i--) {
        curr[curr_ix] = matrix[i][matrix_layer];
        curr_ix++;
      }

      if (curr_ix == curr_len) {
        break;
      }

      matrix_layer++;
      n --;
    }

    return curr;
  }


  /**
   * Method to get snailshell pattern
   * 
   * @param matrix Matrix of numbers to go through
   * @return Order array of values thar represent a snail shell pattern
   */
  public Future<int[]> getSnailShell(int[][] matrix) {
    // Since all the matrixes are square, we only need the length
    // of the first row.
    int n = matrix[0].length;

    // Checks if the matrix is empty.
    if (n == 0) {
      return executor.submit(() -> {
        return new int[0];
      });
    }
    
    // Checks if the matrix is valid (is square).
    if (matrix.length != n) {
      throw new IllegalArgumentException("Matrix must be square");
    }

    int array_size = n*n;
    int[] result = new int[array_size];

    int matrix_layer = 0;
    int ix = 0;
    
    final int[] res = getSnailShellAux(matrix, matrix_layer, n, result, array_size, ix);

    return executor.submit(() -> {
      return res;
    });
  
  };
}
