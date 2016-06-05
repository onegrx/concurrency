package s5e1.ania;

/**
 * Created by onegrx on 05.06.16.
 */
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER;
    private final double ZOOM = 150;
    private BufferedImage I;
    int amountOfThreads;

    public static void main(String [] args){
        System.out.println("alg,ilosc,czas");
        int MAX_ITER = 350;
        //for(int MAX_ITER = 100; MAX_ITER < 10000; MAX_ITER+=500){
        for(int amountOfThreads = 100 ; amountOfThreads <101; amountOfThreads += 100){
            for(int j = 0; j < 1; j++){
                long start = System.nanoTime();
                new Mandelbrot(MAX_ITER, amountOfThreads).setVisible(true);
                long stop = System.nanoTime();
                System.out.println("fixedPool,"+ amountOfThreads + ","+ (stop - start));
            }
        }
        //}
    }

    public Mandelbrot(int MAX_ITER, int amount){
        super("Mandelbrot Set");
        this.amountOfThreads = amount;
        this.MAX_ITER = MAX_ITER;
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(amountOfThreads);
        //ExecutorService pool = Executors.newSingleThreadExecutor();
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<Integer> callable = new Draw(I, x, y, MAX_ITER, ZOOM);
                Future<Integer> future = pool.submit(callable);
                set.add(future);
            }
        }
        for (Future<Integer> future : set){
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        setVisible(false);
        dispose();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }


    public static class Draw implements Callable<Integer>{
        BufferedImage I;
        int x;
        int y;
        int MAX_ITER;
        double ZOOM;

        public Draw(BufferedImage I, int x, int y, int MAX_ITER, double ZOOM) {
            this.x = x;
            this.y = y;
            this.MAX_ITER = MAX_ITER;
            this.ZOOM = ZOOM;
            this.I = I;
        }

        public Integer call(){
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
            return I.getRGB(x, y);
        }
    }



}