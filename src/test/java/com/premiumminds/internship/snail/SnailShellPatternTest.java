package com.premiumminds.internship.snail;

import static org.junit.Assert.assertArrayEquals;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by aamado on 05-05-2023.
 */
@RunWith(JUnit4.class)
public class SnailShellPatternTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public SnailShellPatternTest() {
  };


  /**
   * Chooses the value that will be used to fill both the test matrix and
   * its corresponding expected array, based on the 'randomFill' flag.
   * 
   * @param count Current count
   * @param randomFill Flag that chooses if the value returned will be random or not
   * @return 'count+1' if randomFill is false, or a random value (between 0 and 1000) otherwise
   */
  private int valueToFill(int count, boolean randomFill) {
    if (randomFill) {
      return (int) (Math.random() * 1000);
    }
    else {
      return count+1;
    }
  }


  /**
   * (Helper method)
   * - Fills the given matrix of size n*n and the 'expected' array with
   *   the values from 1 to n*n.
   * 
   * @param matrix Matrix to fill
   * @param expected Array to fill (array expected after performing the snail shell pattern alorithm)
   * @param expectedLen Length of the expected array
   * @param n Size of the matrix (n*n)
   * @param randomFill If true, fills the matrix and the expected array with random numbers between 0 and 1000
   */
  private void createNByNTestMatrix(int[][] matrix, int[] expected, int expectedLen, int n, boolean randomFill) {
    int count = 0, val;

    int newN = n;
    int layer = 0;

    while (true) {

      // If the matrix is odd and we are in the middle, we must add the
      // last value to our array and break the loop.
      if (n%2 == 1 && layer == n/2) {
        val = valueToFill(count, randomFill);

        matrix[layer][layer] = val;
        expected[count] = val;

        break;
      }

      // Fill the first matrix row (for the current layer), and add
      // the values to the expected array.
      for (int i = layer; i < newN; i++) {
        val = valueToFill(count, randomFill);

        matrix[layer][i] = val;
        expected[count] = val;
        count++;
      }

      // After adding any values to the expected array, we must
      // check if we have already filled it.
      if (count == expectedLen) {
        break;
      }

      // Fill the last matrix column (for the current layer), and add
      // the values to the expected array.
      for (int i = layer+1; i < newN; i++) {
        val = valueToFill(count, randomFill);

        matrix[i][newN-1] = val;
        expected[count] = val;
        count++;
      }

      if (count-1 == expectedLen) {
        break;
      }

      // Fill the last matrix row (for the current layer), and add
      // the values to the expected array.
      for (int i = newN-2; i >= layer; i--) {
        val = valueToFill(count, randomFill);

        matrix[newN-1][i] = val;
        expected[count] = val;
        count++;
      }

      if (count-1 == expectedLen) {
        break;
      }

      // Fill the first matrix column (for the current layer), and add
      // the values to the expected array.
      for (int i = newN-2; i > layer; i--) {
        val = valueToFill(count, randomFill);

        matrix[i][layer] = val;
        expected[count] = val;
        count++;
      }

      if (count == expectedLen) {
        break;
      }

      layer++;
      newN--;
    }

    return;
  }


  /**
   * (Helper method)
   *  - Creates a matrix of size n by n as well as the expected array after
   *    the snail shell algorithm is applied.
   *  
   *  Tests the snail shell algorithm on the matrix.
   * 
   * @param n The size of the matrix.
   * @param randomFill If true, the matrix is filled with random numbers between 0 and 1000.
   * @throws InterruptedException
   * @throws ExecutionException
   * @throws TimeoutException
   */
  private void SnailShellPatternTestNByNMatrix(int n, boolean randomFill)
      throws InterruptedException, ExecutionException, TimeoutException{
    int expectedLen = n*n;

    int[][] matrix = new int[n][n];
    int[] expected = new int[expectedLen];
    
    createNByNTestMatrix(matrix, expected, expectedLen, n, randomFill);

    Future<int[]> resultFuture = new SnailShellPattern().getSnailShell(matrix);
    int[] result = resultFuture.get(10, TimeUnit.SECONDS);
    assertArrayEquals(expected, result);
  }


  @Test
  public void ScreenLockinPatternTestFirst3Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    int[][] matrix = { { 1, 2, 3 }, { 8, 9, 4 }, { 7, 6, 5 } };
    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    assertArrayEquals(result, expected);
  }


  // New tests.

  @Test
  public void SnailShellPatternTest0By0Matrix()
      throws InterruptedException, ExecutionException, TimeoutException {

    int[][] matrix = { { } };

    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);

    int[] expected = { };
    assertArrayEquals(expected, result);
  }


  @Test
  public void SnailShellPatternTest1By1Matrix()
      throws InterruptedException, ExecutionException, TimeoutException {

    int[][] matrix = { { 99999 } };

    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);

    int[] expected = { 99999 };
    assertArrayEquals(expected, result);
  }


  @Test
  public void SnailShellPatternTest4By4Matrix()
      throws InterruptedException, ExecutionException, TimeoutException {

    int[][] matrix = { { -123, 362, 76, 1 },
                       { 8873, 2, 0, 14 },
                       { 826, -64, 72, -96 },
                       { -3, -7, 293, 244 } };

    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);

    int[] expected = { -123, 362, 76, 1, 14, -96, 244, 293, -7, -3, 826, 8873, 2, 0, 72, -64 };
    assertArrayEquals(expected, result);
  }


  // Performance tests.

  @Test
  public void SnailShellPatternTestsMatrixSize1To100()
      throws InterruptedException, ExecutionException, TimeoutException {

    for (int i = 1; i <= 100; i++) {
      SnailShellPatternTestNByNMatrix(i, false);
    }
  }


  @Test
  public void SnailShellPatternTestsMatrixSize100To1000()
      throws InterruptedException, ExecutionException, TimeoutException {

    for (int i = 100; i <= 1000; i++) {
      SnailShellPatternTestNByNMatrix(i, true);
    }
  }

}