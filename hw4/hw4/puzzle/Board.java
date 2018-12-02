package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Map;

public class Board implements WorldState{
    private int[][] board;

    public Board(int[][] tiles) {
        int[][] temp = new int[tiles.length][tiles.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = tiles[i][j];
            }
        }
        board = temp;
    }

    public int tileAt(int i, int j) {
        return board[i][j];
    }

    public int size() {
        return board.length;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;

    }

    public int hamming() {
        int hamming = 0;
        int correctNumber = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i == board.length - 1 && j == board.length - 1) return hamming;
                if (board[i][j] != correctNumber) hamming += 1;
                correctNumber += 1;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    manhattan = manhattan + calculateManhattan(board[i][j], i, j);
                }
            }
        }
        return manhattan;
    }

    private int calculateManhattan(int correctNumber, int i, int j) {
        int N = board.length;
        int xPosition = correctNumber % N == 0 ? correctNumber / N - 1 : correctNumber / N;
        int yPosition = correctNumber % N == 0 ? N - 1 : correctNumber % N - 1;
        return Math.abs(xPosition - i) + Math.abs(yPosition - j);
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        return Arrays.equals(this.board,((Board) y).board);
    }



    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] test ={{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(test);
        StdOut.println(b.manhattan());
    }
}
