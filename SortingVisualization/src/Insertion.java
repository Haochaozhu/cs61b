import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;

public class Insertion {

    public static void sort(double[] a) {
        boolean[] markedRed = new boolean[a.length];
        boolean[] markedGrey = new boolean[a.length];
        Stopwatch time = new Stopwatch();

        int N = a.length;

        for (int i = 1; i < N; i += 1) {
            int j = 0;
            for (j = i; j > 0 && a[j] < a[j - 1]; j -= 1) {
                exch(a, j, j - 1);
            }
            markedRed[j] = true;
            show(a, markedRed, markedGrey, time.elapsedTime());
            markedRed[j] = false;

        }

    }

    private static void show(double[] a, boolean[] markedRed, boolean[] markedGrey, double elapsedTime) {
        StdDraw.clear(Color.BLACK);
        int width = 20;
        for (int i = 0; i < 200; i += 1) {
            if (markedRed[i])  {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledRectangle(width,0,2,a[i] * 200);
                StdDraw.setPenColor(Color.WHITE);
            } else if (markedGrey[i]) {
                StdDraw.setPenColor(Color.GRAY);
                StdDraw.filledRectangle(width,0,2,a[i] * 200);
                StdDraw.setPenColor(Color.WHITE);
            }
            else StdDraw.filledRectangle(width,0,2,a[i] * 200);
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
