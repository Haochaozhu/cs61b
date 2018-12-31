import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;

public class Merge {
    private static double[] aux;
    private static Stopwatch time;

    public static void sort(double[] a) {
        time = new Stopwatch();
        aux = new double[a.length];
        sort(a, 0, a.length - 1);
    }

    private static void sort(double[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
        show(a, time.elapsedTime());
    }

    public static void merge(double[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void bottomUpSort(double[] a) {
        int N = a.length;
        aux = new double[N];

        for (int sz = 1; sz < N; sz *= 2) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, lo, lo + sz - 1, Math.min((lo + sz + sz - 1), N - 1));
            }
        }

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

    private static void exch(double[] a, int i , int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

}
