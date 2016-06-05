package s5e1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame {

    private final int MAX_ITER = 1500;
    private final double ZOOM = 150;
    private BufferedImage I;

    public Main() throws InterruptedException, ExecutionException {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(32);
        Set<Future<Object>> callables = new HashSet<>();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<Object> callable = new Mandelbrot(x, y, MAX_ITER, ZOOM, I);
                callables.add(pool.submit(callable));
            }
        }

        for (Future<Object> callable : callables) {
            callable.get();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.nanoTime();
        new Main().setVisible(true);
        long end = System.nanoTime();
        System.out.println(end-start);
    }
}