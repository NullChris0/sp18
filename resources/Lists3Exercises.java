import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import static org.junit.Assert.*;


public class Lists3Exercises {
    /**
     * Java arrays must contain elements of the same type.
     * To contain different types of data in a two-dimensional array,
     * use an array of type Object, because Object is the superclass of all classes.
     */
    @Test
    public void testdimensional() {
        Object[][] array = new Object[2][];
        array[0] = new String[] {"Java", "Python", "C++"};
        array[1] = new Integer[] {1, 2, 3, 4, 5};
        for (Object[] subArray : array) {
            for (Object element : subArray) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public interface NullSafeStringComparator {
        /**
         * @return negative if a < b, zero if equal or both null, positive otherwise.
         * null is less than any string.
         */
        public int compare(String a, String b);
    }

    public static class LengthComparator implements NullSafeStringComparator {
        /* In this file, LengthComparator is a inner class, so add static to make instance in main() */
        /* Java compiler will provide a non-args constructor, we don't need to code */
        public int compare(String a, String b) {
            if (a == null && b == null)
                return 0;
            else if (a == null)
                return -b.length();
            else if (b == null)
                return a.length();
            else return a.length() - b.length();
        }
    }

    /**
     * Helper function max, return maximum string of 1D array using StringComparator.
     * @param a 1D array of string
     * @param sc Comparator
     */
    public static String max(String[] a, LengthComparator sc) {
        String maxStr = a[0];
        for (int i = 1; i < a.length; i++) {
            if (sc.compare(a[i], maxStr) > 0) {
                maxStr = a[i];
            }
        }
        return maxStr;
    }

    /**
     * Step method will replace every string with its longest neighbor
     * (neighbors are 8 strings surrounding it), EXCEPT edges(null).
     * For the sake of allowing easy customization, we compare strings using an object that
     * implements the NullSafeStringComparator interface.
     * @param arr m * n rectangle array of string, surrounding with null.
     * @return new 2D array
     */
    public static String[][] step(String[][] arr) {
        String[][] stepped = new String[arr.length][arr[0].length];
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 1; j < arr[i].length - 1; j++) {
                String[] temp = new String[8];
                int index = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int m = -1; m <= 1; m++) {
                        if (k != 0 || m != 0) temp[index] = arr[i + k][j + m];
                        index += (k != 0 || m != 0) ? 1 : 0;  // For less conformable, not use `++`
                    }
                }
                stepped[i][j] = max(temp, new LengthComparator());
            }
        }
        return stepped;
    }

    @Test
    public void teststep() {
        String[][] a = {{null, null, null, null, null, null},
                        {null, "a", "cat", "cat", "dogs", null},
                        {null, "a", null, "cat", "a", null},
                        {null, "a", "ca", "", "ca", null},
                        {null, null, null, null, null, null}};
        String[][] b = {{null, null, null, null, null, null},
                        {null, "cat", "cat", "dogs", "cat", null},
                        {null, "cat", "cat", "dogs", "dogs", null},
                        {null, "ca", "cat", "cat", "cat", null},
                        {null, null, null, null, null, null}};
        String[][] c = step(a);
        for (String[] strings : c) {
            for (String s : strings)
                if (s != null) {
                    System.out.printf("%5s", s + " ");
                } else {
                    System.out.print("---- ");
                }
            System.out.println();
        }
        assertArrayEquals(b, c);
    }

    public class Piece {
        int longitude, latitude;
        public Piece(int lo, int la) {
            longitude = lo;
            latitude = la;
        }
    }

    /**
     * give in 1D array of Pieces which store latitude and longitude, return a square N * N array
     * which every row has the same latitude
     */
    public Piece[][] groupByLat(Piece[] p) {
        int width = (int)Math.sqrt(p.length);
        Piece[][] latGroup = new Piece[width][width];
        for (int i = 0 ; i < p.length; i++) {  // for each in p
            for (int j = 0 ; j < width; j++) {  // for each row in latGroup
                if (latGroup[j][0] == null) {
                    latGroup[j][0] = p[i];  // identify the row's first
                    break;
                }
                else if (p[i].latitude == latGroup[j][0].latitude) {  // find same in other elements of p
                    int counter;
                    // the original question is ??? < p.length-1, current maybe wrong
                    for (counter = 0; counter * counter + 1 < p.length - 1; counter++) {  // find free index in row
                        if (latGroup[j][counter] == null) {
                            break;
                        }
                    }
                    latGroup[j][counter] = p[i];
                    break;
                }
            }
        }
        return latGroup;
    }

    @Test
    public void testgroupByLat() {
        Piece[] pieces = {
                new Piece(0, 20), new Piece(20, 10), new Piece(20, 0),
                new Piece(10, 20), new Piece(10, 10), new Piece(10, 0),
                new Piece(20, 20), new Piece(0, 10), new Piece(0, 0),
        };
        Piece[][] result = solvePuzzle(pieces);
        for (Piece[] value : result) {
            for (Piece piece : value) {
                if (piece != null)
                    System.out.print("[" + piece.longitude + ", " + piece.latitude + "] ");
                else
                    System.out.print("[null] ");
            }
            System.out.println();
        }
    }

    /**
     * helper function that sort 2D array's rows by smallest latitude
     * @return the sorted array in place
     */
    public Piece[][] sortbyLat(Piece[][] p) {
        Arrays.sort(p, (row1, row2) -> {
            if (row1[0] == null && row2[0] == null) return 0;
            if (row1[0] == null) return 1;
            if (row2[0] == null) return -1;
            return Integer.compare(row1[0].latitude, row2[0].latitude);
        });
        return p;
    }

    /**
     * helper function <p>
     * Takes in a 1D array of Pieces and half sorts them all by longitude. In
     * this problem, the term half sort means that the array is fully sorted except the first half of the
     * sorted array is switched with the second half of the sorted array. For example:
     * say we have an array [9, 2, 4, 0]. This array sorted would be [0, 2, 4, 9]. This array half sorted would
     * be [4, 9, 0, 2] since the first half of the sorted array, [0, 2], would be swapped with the second half,
     * [4, 9]
     */
    public Piece[] sortHalfLong(Piece[] p) {
        Arrays.sort(p, (p1, p2) -> {
            if (p1 == null && p2 == null) return 0;
            if (p1 == null) return 1;
            if (p2 == null) return -1;
            return Integer.compare(p1.longitude, p2.longitude);
        });

        int n = p.length;
        Piece[] halfSorted = new Piece[n];
        if (n % 2 == 0) {
            for (int i = 0; i < n / 2; i++) {
                halfSorted[i] = p[n / 2 + i];
                halfSorted[n / 2 + i] = p[i];
            }
        } else {
            for (int i = 0; i < n / 2; i++) {
                halfSorted[i] = p[n / 2 + 1 + i];
                halfSorted[n / 2 + 1 + i] = p[i];
            }
        }
        if (n % 2 == 1)
            halfSorted[n / 2] = p[n / 2];
        return halfSorted;
    }

    public Piece[][] solvePuzzle(Piece[] scattered) {
        Arrays.sort(scattered, Comparator.comparingInt(p -> p.longitude));
        Piece[][] answer = sortbyLat(groupByLat(scattered));
        // Step 3: Create a temporary array to store each column
        int n = answer.length;
        for (int col = 0; col < n; col++) {
            Piece[] column = new Piece[n];
            for (int row = 0; row < n; row++)
                column[row] = answer[row][col];
            // Step 4: Apply sortHalfLong to each column
            column = sortHalfLong(column);
            // Step 5: Put the sorted column back into the 2D array
            for (int row = 0; row < n; row++)
                answer[row][col] = column[row];
        }
        return answer;
    }
}
