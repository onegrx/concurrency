package s2e3;

/**
 * Created by onegrx on 15.03.16.
 */
public class BoundedSemaphore {

    CountingSemaphore cs1, cs2;

    public BoundedSemaphore(int lim1, int lim2) {
        cs1 = new CountingSemaphore(lim1);
        cs2 = new CountingSemaphore(lim2);
    }

    //?
    void takeWeight() {
        try {
            cs2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cs1.take();
        System.out.println("Taking weight");
    }

    //?
    void returnWeight() {
        cs2.take();
        try {
            cs1.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Taking weight");
    }
}
