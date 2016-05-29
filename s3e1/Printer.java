package s3e1;

import java.util.*;

/**
 * Created by onegrx on 28.05.16.
 */
public class Printer {

    private static int counter = 0;
    private int id = ++counter;
    private Random random = new Random();

    void print(int length) {
        String message = createTask(length);
        System.out.println("Printer no. " + id + ": " + message);
    }

    String createTask(int taskSize) {
        StringBuilder task = new StringBuilder(taskSize);
        for (int i = 0; i < taskSize; i++) {
            task.append(Integer.toString(random.nextInt(10)));
        }
        return task.toString();
    }
}
