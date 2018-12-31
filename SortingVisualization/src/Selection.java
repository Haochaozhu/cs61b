import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;

public class Selection {

    public static void sort(double[] a) {
        boolean[] markedRed = new boolean[a.length];
        boolean[] markedGrey = new boolean[a.length];
        Stopwatch time = new Stopwatch();
        int N = a.length;
        for (int i = 0; i < N; i += 1) {
            int min = i;
            for (int j = i + 1; j < N; j += 1) {
                if (a[j] < a[min]) min = j;
            }
            markedRed[min] = true;
            show(a, markedRed, markedGrey, time.elapsedTime());
            markedRed[min] = false;
            exch(a, i, min);
            markedGrey[i] = true;
        }
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(double[] a, boolean[] markedRed, boolean[] markedGrey, double elapsedTime) {
        StdDraw.clear(Color.BLACK);
        int width = 20;
        for (int i = 0; i < 200; i += 1) {
            if (markedRed[i]) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledRectangle(width, 0, 2, a[i] * 200);
                StdDraw.setPenColor(Color.WHITE);
            } else if (markedGrey[i]) {
                StdDraw.setPenColor(Color.GRAY);
                StdDraw.filledRectangle(width, 0, 2, a[i] * 200);
                StdDraw.setPenColor(Color.WHITE);
            } else StdDraw.filledRectangle(width, 0, 2, a[i] * 200);
            width += 5;
        }

        StdDraw.textLeft(20, 250, "" + elapsedTime);
        Core.drawButton();
        StdDraw.show();
        StdDraw.pause(10);

    }

    public static void showInitial(double[] a) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        int width = 20;
        for (int i = 0; i < 200; i += 1) {
            StdDraw.filledRectangle(width, 0, 2, a[i] * 200);
            width += 5;
        }
        Core.drawButton();
        StdDraw.show();
    }

    public static void main(String[] args) {
        StdDraw.clear(Color.BLACK);
    }

}