package s5e1;

import java.awt.image.*;
import java.util.concurrent.Callable;

public class Mandelbrot implements Callable<Object> {

    private int x;
    private int y;
    private int MAX_ITER;
    private double ZOOM;
    BufferedImage I;

    public Mandelbrot(int x, int y, int MAX_ITER, double ZOOM, BufferedImage I) {
        this.x = x;
        this.y = y;
        this.MAX_ITER = MAX_ITER;
        this.ZOOM = ZOOM;
        this.I = I;
    }

    public Object call() throws Exception {
        double zx = 0;
        double zy = 0;
        double cX = (x - 400) / ZOOM;
        double cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;

        double tmp;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }

        I.setRGB(x, y, iter | (iter << 8));
        return null;
    }

}