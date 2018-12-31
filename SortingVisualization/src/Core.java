import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.awt.*;


public class Core {
    private static void drawInitialArray(double[] a) {
        StdDraw.setCanvasSize(1080, 320);
        StdDraw.setXscale(0, 1080);
        StdDraw.setYscale(0, 320);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);

        StdDraw.setPenColor(Color.WHITE);
        int width = 20;
        for (int i = 0; i < 200; i += 1) {
            StdDraw.filledRectangle(width,0,2,a[i] * 200);
            width += 5;
        }
        drawButton();
        StdDraw.show();
    }

    public static void drawButton() {
        StdDraw.rectangle(53, 302, 40, 10);
        StdDraw.text(53, 300, "Selection");

        StdDraw.rectangle(146, 302, 40, 10);
        StdDraw.text(146, 300, "Insertion");

        StdDraw.rectangle(232, 302, 33, 10);
        StdDraw.text(232, 300, "Merge");

        StdDraw.rectangle(310, 302, 33, 10);
        StdDraw.text(311, 300, "Quick");

        StdDraw.rectangle(390, 302, 33, 10);
        StdDraw.text(390, 300, "Shell");

        StdDraw.rectangle(470, 302, 40, 10);
        StdDraw.text(470, 300, "MergeBU");

        StdDraw.rectangle(53, 275, 40, 10);
        StdDraw.text(53,273, "Shuffle");

        StdDraw.rectangle(146, 275, 33, 10);
        StdDraw.text(146, 273, "Reset");
    }

    public static void main(String[] args) {
        double[] array = new double[200];
        double[] initialArrayCopy = new double[200];
        boolean exit = false;
        for (int i = 0; i < 200; i += 1) {
            array[i] = StdRandom.uniform();
        }

        for (int i = 0; i < 200; i += 1) {
            initialArrayCopy[i] = array[i];
        }

        drawInitialArray(array);
        while (!exit) {
            if (!StdDraw.isMousePressed()) {
                continue;
            }
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            if (mouseX > 13 && mouseX < 93 && mouseY < 312 && mouseY > 292) {
                Selection.sort(array);
            }

            if (mouseX < 186 && mouseX > 106 && mouseY < 312 && mouseY > 292) {
                Insertion.sort(array);
            }

            if (mouseX > 199 && mouseX < 265 && mouseY < 312 && mouseY > 292) {
                Merge.sort(array);
            }

            if (mouseX > 277 && mouseX < 343 && mouseY < 312 && mouseY > 292) {
                Quick.sort(array);
            }

            if (mouseX > 357 && mouseX < 423 && mouseY < 312 && mouseY > 292) {
                Shell.sort(array);
            }

            if (mouseX > 430 && mouseX < 510 && mouseY < 312 && mouseY > 292) {
                MergeBU.bottomUpSort(array);
            }

            if (mouseX > 13 && mouseX < 93 && mouseY < 285 && mouseY > 265) {
                for (int i = 0; i < 200; i += 1) {
                    array[i] = StdRandom.uniform();
                }

                for (int i = 0; i < 200; i += 1) {
                    initialArrayCopy[i] = array[i];
                }
                Selection.showInitial(array);
            }

            if (mouseX < 179 && mouseX > 113 && mouseY < 285 && mouseY > 265) {
                Selection.showInitial(initialArrayCopy);
                for (int i = 0; i < 200; i += 1) {
                    array[i] = initialArrayCopy[i];
                }
            }

        }
//        Selection.sort(array);
    }
}
