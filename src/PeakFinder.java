/**
 * PeakFinder.java
 * Peak detection by Improved Divide & Conquer on 2D and 3D arrays
 * @author mustafaHTP
 */

import java.util.Random;

public class PeakFinder {

    int numberOfCol;
    int numberOfRow;
    int numberOfLayer;

    int startColIdx;
    int endColIdx;

    int startRowIdx;
    int endRowIdx;

    int startLayerIdx;
    int endLayerIdx;

    // 2D FUNCTIONS
    /**
     * checks the number whether it is peak or not
     * @param arr 2d array
     * @param indx holds the number indexes that is going to be controlled whether it is peak or not
     * indx[0] = row index 
     * indx[1] = column index
     * @return if the number satisfies the condition of peak number then return true, otherwise return false
     */
    public boolean isPeak2D(int[][] arr, int[] indx) {

        //Get indexes
        int rowIdx = indx[0];
        int colIdx = indx[1];
        int number = arr[rowIdx][colIdx];

        // Looks up
        if (rowIdx > 0 && number < arr[rowIdx - 1][colIdx])
            return false;
        // Looks down
        else if (rowIdx < numberOfRow-1 && number < arr[rowIdx + 1][colIdx])
            return false;
        // Looks left
        else if (colIdx > 0 && number < arr[rowIdx][colIdx - 1])
            return false;
        // Looks right
        else if (colIdx < numberOfCol-1 && number < arr[rowIdx][colIdx + 1])
            return false;

        return true;
    }

    /**
     * finds max number in given row index
     * @param arr 2d array
     * @param searchRow row index to find the max number
     * @return max number on row
     */
    public int findMaxOnRow(int[][] arr, int searchRow) {

        int max = arr[searchRow][startColIdx];
        int maxIdx = startColIdx;
        for (int i = startColIdx + 1; i <= endColIdx; i++) {
            if (max < arr[searchRow][i]) {
                max = arr[searchRow][i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    /**
     * finds max number in given column index
     * @param arr 2d array
     * @param searchRow column index to find the max number
     * @return max number on column
     */
    public int findMaxOnCol(int[][] arr, int searchCol) {

        int max = arr[startRowIdx][searchCol];
        int maxIdx = startRowIdx;
        for (int i = startRowIdx + 1; i <= endRowIdx; i++) {
            if (max < arr[i][searchCol]) {
                max = arr[i][searchCol];
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    /**
     * If this method does not find peak number, 
     * it only splits array by rows ( row: 4 col: 4 after splitByRow -> row: 2 col: 4)
     * @param arr 2d array
     * @return if there is peak number then return it otherwise return null
     */
    public int[] splitByRow(int[][] arr) {

        int sRow = startRowIdx;
        int eRow = endRowIdx;
        int[] index = new int[2];

        // Take mid row
        int midRowIdx = (sRow + eRow) / 2;
        // Update index
        index[0] = midRowIdx;
        index[1] = startColIdx;

        // If mid is a peak
        if (isPeak2D(arr, index))
            return index;

        // Find max
        int maxIdx = findMaxOnRow(arr, midRowIdx);
        // Update index
        index[0] = midRowIdx;
        index[1] = maxIdx;

        //If max is a peak
        if(isPeak2D(arr, index))
            return index;

        int maxNumber = arr[midRowIdx][maxIdx];
        // Check neighbours
        // Looks up
        if (midRowIdx > 0 && maxNumber < arr[midRowIdx - 1][maxIdx])
            endRowIdx = midRowIdx - 1;
        // Looks down
        else if (midRowIdx < numberOfRow-1 && maxNumber < arr[midRowIdx + 1][maxIdx])
            startRowIdx = midRowIdx + 1;

        return index;       
    }

    /**
     * If this method does not find peak number, 
     * it only splits array by columns ( row: 4 col: 4 after splitByRow -> row: 4 col: 2)
     * @param arr 2d array
     * @return if there is peak number then return it otherwise return null
     */
    public int[] splitByCol(int[][] arr) {

        int sCol = startColIdx;
        int eCol = endColIdx;
        int[] index = new int[2];

        // Take mid col
        int midColIdx = (sCol + eCol) / 2;
        // Update index
        index[0] = startRowIdx;
        index[1] = midColIdx;

        // If mid is a peak
        if (isPeak2D(arr, index))
            return index;

        // Find max
        int maxIdx = findMaxOnCol(arr, midColIdx);
        // Update index
        index[0] = maxIdx;
        index[1] = midColIdx;

        // If max is a peak
        if (isPeak2D(arr, index))
            return index;

        int maxNumber = arr[maxIdx][midColIdx];
        // Check neighbours
        // Looks left
        if (midColIdx > 0 && maxNumber < arr[maxIdx][midColIdx - 1])
            endColIdx = midColIdx - 1;
        // Looks right
        else if (midColIdx < numberOfCol-1 && maxNumber < arr[maxIdx][midColIdx + 1])
            startColIdx = midColIdx + 1;

        return index;
    }

    /**
     * generate random 2D array
     */
    public int[][] random2dArray(int n, int m) {
        int[][] a = new int[n][];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = new int[m];
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = r.nextInt(100);
            }
        }
        return a;

    }

    public void print2d(int[][] a) {
        for (int[] i : a) {
            for (int item : i) {
                System.out.print(item + "\t ");
            }
            System.out.println("");
        }
    }

    /**
     * @param n = number of rows
     * @param m = number of columns
     * @param rowOrCol 
     * rowOrcol -> 0, splits array only by rows
     * rowOrcol -> 1, splits array only by columns
     * rowOrcol -> 2, splits array by rows in one iteration, in the other iteration split by columns
     */
    public int[] peakFinder2D(int[][] a, int n, int m, int rowOrcol) throws NullPointerException {

        // determine boundries
        numberOfRow = n;
        numberOfCol = m;

        startRowIdx = 0;
        endRowIdx = n - 1;
        startColIdx = 0;
        endColIdx = m - 1;

        int[] indx = new int[2];
        indx[0] = -1;
        indx[1] = -1;
        boolean isPeakNumber = false;

        // split by row
        if (rowOrcol == 0) {

            while (startRowIdx >= 0 && endRowIdx < numberOfRow) {
                indx = splitByRow(a);
                isPeakNumber = isPeak2D(a, indx);
                if (isPeakNumber)
                    return indx;       
            }

        }
        // split by col
        else if (rowOrcol == 1) {

            while (startColIdx >= 0 && endColIdx < numberOfCol) {

                indx = splitByCol(a);
                isPeakNumber = isPeak2D(a, indx);
                if (isPeakNumber)
                    return indx;
            }

        }
        // first split row and then column
        else if (rowOrcol == 2) {

            while (startRowIdx >= 0 && endRowIdx < numberOfRow && startColIdx >= 0 && endColIdx < numberOfCol) {

                indx = splitByRow(a);
                isPeakNumber = isPeak2D(a, indx);
                if (isPeakNumber)
                    return indx;

                indx = splitByCol(a);
                isPeakNumber = isPeak2D(a, indx);
                if (isPeakNumber)
                    return indx;
            }
        }
        return null;
    }

    // 3D FUNCTIONS
    public void printLayer(int[][][] arr, int currLayer) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j][currLayer] + "\t");
            }
            System.out.println("");
        }
    }

    /**
     * finds max number 2d array in given layer index
     * @param arr 3d aray
     * @param searchLayer index to find max number
     * @return max number on 2d array under searchLayer index
     */
    public int[] findMaxOnLayer(int[][][] arr, int searchLayer) {

        int[] maxIdx = new int[3];
        maxIdx[2] = searchLayer;
        int max = arr[0][0][searchLayer];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (max < arr[i][j][searchLayer]) {
                    maxIdx[0] = i;
                    maxIdx[1] = j;
                    max = arr[i][j][searchLayer];
                }
            }
        }
        return maxIdx;
    }
    
    /**
     * checks the number whether it is peak or not
     * @param arr 3d array
     * @param indx holds the number indexes that is going to be controlled whether it is peak or not
     * indx[0] = row index 
     * indx[1] = column index
     * indx[2] = layer index
     * @return if the number satisfies the condition of peak number then return true, otherwise return false
     */
    public boolean isPeak3D(int[][][] arr, int[] point) {

        int rowIdx = point[0];
        int colIdx = point[1];
        int layerIdx = point[2];

        int number = arr[rowIdx][colIdx][layerIdx];

        // Looks up
        if (rowIdx > 0 && number < arr[rowIdx - 1][colIdx][layerIdx])
            return false;
        // Looks down
        else if (rowIdx < numberOfRow-1 && number < arr[rowIdx + 1][colIdx][layerIdx])
            return false;
        // Looks left
        else if (colIdx > 0 && number < arr[rowIdx][colIdx - 1][layerIdx])
            return false;
        // Looks right
        else if (colIdx < numberOfCol-1 && number < arr[rowIdx][colIdx + 1][layerIdx])
            return false;
        // Looks back
        else if (layerIdx > 0 && number < arr[rowIdx][colIdx][layerIdx - 1])
            return false;
        // Looks front
        else if (layerIdx < numberOfLayer-1 && number < arr[rowIdx][colIdx][layerIdx + 1])
            return false;

        return true;
    }

    /**
     * finds peak number in 3d array
     * 
     * @param a array
     * @param r number of Rows
     * @param c number of columns
     * @param d number of layers
     * @return peak number's row and column indexes
     */
    public int[] peakFinder3d(int[][][] a, int r, int c, int d) throws NullPointerException {

        numberOfRow = r;
        numberOfCol = c;
        numberOfLayer = d;
        
        int startLayerIdx = 0;
        int endLayerIdx = d - 1;

        int[] index = new int[3];
        boolean isPeakNumber = false;

        while (startLayerIdx >= 0 && endLayerIdx < numberOfLayer) {

            int midLayer = (startLayerIdx + endLayerIdx) / 2;
            // update index
            index[0] = 0;
            index[1] = 0;
            index[2] = midLayer;

            // If mid is a peak number
            isPeakNumber = isPeak3D(a, index);
            if (isPeakNumber){
                return index;
            }
            // find max on layer
            index = findMaxOnLayer(a, midLayer);
            int maxRowIdx = index[0];
            int maxColIdx = index[1];
            int maxLayIdx = index[2];
            int max = a[maxRowIdx][maxColIdx][maxLayIdx];

            // If mid is a peak number
            isPeakNumber = isPeak3D(a, index);
            if (isPeakNumber){
                return index;
            }

            // Looks front
            if (midLayer < numberOfLayer-1 && max < a[maxRowIdx][maxColIdx][maxLayIdx + 1])
                startLayerIdx = maxLayIdx + 1;
            // Looks back
            else if (midLayer > 0 && max < a[maxRowIdx][maxColIdx][maxLayIdx - 1])
                endLayerIdx = maxLayIdx - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        PeakFinder pf = new PeakFinder();
        int m = 100, n = 100, rowOrcol = 0;
        int[][] a = pf.random2dArray(n, m);
        long t1 = System.nanoTime();
        pf.peakFinder2D(a, n, m, rowOrcol);
        long t2 = System.nanoTime();

        System.out.printf("%d, %d, %d, %d", n, m, rowOrcol, t2 - t1);
    }
}
