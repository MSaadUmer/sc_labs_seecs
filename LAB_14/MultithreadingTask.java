public class MultithreadingTask {
    public static void main(String[] args) {
        // Create the first thread using the Thread class
        Thread printNumbersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Number: " + i);
                    try {
                        Thread.sleep(500); // Delay for better observation
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                }
            }
        });

        // Create the second thread using the Runnable interface
        Thread printSquaresThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Square of " + i + ": " + (i * i));
                    try {
                        Thread.sleep(500); // Delay for better observation
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                }
            }
        });

        // Start both threads
        printNumbersThread.start();
        printSquaresThread.start();

        // Join threads to ensure they finish before the main thread exits (optional)
        try {
            printNumbersThread.join();
            printSquaresThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("Both threads have finished execution.");
    }
}
