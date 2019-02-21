package lab4.data;

import java.util.Observable;

public class GameGrid extends Observable {


    private final int INROW = 2;
    private int[][] grid;
    private int[] lastMove;

    public GameGrid(int gridSize) {
        grid = new int[gridSize][gridSize];
        lastMove = new int[2];
    }

//    public static void main(String[] args) {
//        GameGrid g = new GameGrid(5);
////        g.move(4, 3, TileState.ME);
////        g.move(1, 3, TileState.ME);
////        g.move(2, 3, TileState.ME);
////        g.move(3, 3, TileState.ME);
//
//
//        g.move(3, 0, TileState.OTHER);
//        g.move(3, 1, TileState.OTHER);
//        g.move(3, 2, TileState.OTHER);
//        g.move(3, 3, TileState.OTHER);
//
//        g.isWinner();
//    }

    public int getLocation(int x, int y) {
        return 0;
    }

    public int getSize() {
        return grid.length;
    }

    /**
     * NOTICE Y THEN X
     *
     * @param x
     * @param y
     * @param player
     * @return
     */
    public boolean move(int x, int y, TileState player) {
        if (grid[y][x] != TileState.EMPTY.getValue()) {
            return false;
        }

        lastMove[0] = x;
        lastMove[1] = y;

        grid[y][x] = player.getValue();
        setChanged();
        notifyObservers();
        return true;
    }

    public void clearGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = TileState.EMPTY.getValue();
            }
        }

        setChanged();
        notifyObservers();

    }

    /**
     * loop through array and check if there are 'INROW' of ME
     * or OTHER in a row. Also diagonal!
     *
     * @return Returns true if the move was a winning one, else return false.
     */
    public boolean isWinner() {


        //Get horizontal arrays.
        for (int x[] : grid) {
            if (winningRow(x)) {
                System.out.println("you won in horizontal!");
                return true;
            }
        }

        //Get vertical arrays.

        int[] vertical = new int[grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                vertical[j] = grid[j][i];
            }
            if (winningRow(vertical)) {
                System.out.println("you won in vertical! col:" + i);
                return true;
            }
        }


        // Get NW-SE horizontal
        // arrays will have different sizes.
        // length = grid.length-col+1

        // x (collumn) goes from 0 to grid.length - INROW + 1


        // 1,0 2,1 3,2
        // 0,0 1,1 2,2 ...
        // 0,1 1,2 2,3 ...
        // 0,2 1,3 2,4


        //Get SW-NE horizontal


        return false;
    }


    /**
     * Check if there are atleast INROW amount of consequtive integers
     * greater than zero, in a int array.
     * <p>
     * enum is not used
     *
     * @param arr is the array that will be searched for consequitive ints.
     * @return Return true if there is atleast INROW amo
     */
    private boolean winningRow(int[] arr) {
        int counter = 0;
        int current = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != current || arr[i] == 0) {
                counter = 0;
            }

            if (arr[i] > 0) {
                current = arr[i];
                counter++;
            }
            if (counter >= INROW) {
                return true;
            }
        }
        return false;
    }

    /*
        0 1 2 3 4 5 6
      0 x x x x x x x
      1 x x x x x x x
      2 x x x x x x x
      3 x x x x x x x
      4 x x x x x x x
      5 x x x x x x x
      6 x x x x x x x
     */

    public enum TileState {
        EMPTY(0), ME(1), OTHER(2);

        private final int value;

        TileState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
