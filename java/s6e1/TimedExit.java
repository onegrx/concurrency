package s6e1;
import java.util.*;

public class TimedExit {
    public TimedExit(final Proxy proxy) {
        Timer timer = new Timer();
        TimerTask exitApp = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Total consumptions: " + proxy.getConsumptionNumber() + ".");
                System.out.println("Total productions: " + proxy.getProductionNumber() + ".");
                System.exit(0);
            }
        };

        timer.schedule(exitApp, new Date(System.currentTimeMillis() + 2*1000));
    }
}
