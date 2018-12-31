import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;

public class Quick {
    private static Stopwatch time;

    public static void sort(double[] a) {
        time = new Stopwatch();
        sort(a, 0 , a.length - 1);
    }

    private static void sort(double[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
        show(a, time.elapsedTime());

    }

    private static int partition(double[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        double v = a[lo];

        while (true) {
            while (a[++i] < v) {
                if (i == hi) break;
            }

            while(a[--j] > v) {
                if (j == lo) break;
            }

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(double[] a, double elapsedTime) {
        StdDraw.clear(Color.BLACK);
        int width = 20;
        for (int i = 0; i < 200; i += 1) {
            StdDraw.filledRectangle(width,0,2,a[i] * 200);
            width += 5;
        }
        StdDraw.textLeft(20, 250, "" + elapsedTime);
        Core.drawButton();
        StdDraw.show();
        StdDraw.pause(10);


    }
}

