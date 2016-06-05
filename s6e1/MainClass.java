package s6e1;
public class MainClass {
    public static void main(String args[]) {
        if(args.length != 2) {
            System.out.println("Inadequate number of arguments.");
            return;
        }

        int n, m;
        try {
            n = Integer.parseInt(args[0]);
            if(n <= 0 || n%2 == 1)
                throw new NumberFormatException();
				
			m = Integer.parseInt(args[1]);
			if(m <= 0)
				throw new NumberFormatException();
        }
        catch(NumberFormatException exc) {
            System.out.println("Inadequate argument.");
            return;
        }

        Proxy proxy = new Proxy(n);
        new TimedExit(proxy);

        for(int i=0; i<m; i++) {
            new Producer(n, proxy).start();
            new Consumer(n, proxy).start();
        }
    }
}
