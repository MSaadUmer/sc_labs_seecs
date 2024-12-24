public class ThreadSynchronization {
    public static void main(String[] args) {
        // Shared Counter object
        Counter counter = new Counter();

        // Create three threads
        Thread thread1 = new Thread(new IncrementTask(counter));
        Thread thread2 = new Thread(new IncrementTask(counter));
        Thread thread3 = new Thread(new IncrementTask(counter));

        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        // Print the final counter value
        System.out.println("Final Counter Value: " + counter.getValue());
    }
}

// Shared Counter class
class Counter {
    private int count = 0;

    // Synchronized method to increment the counter
    public synchronized void increment() {
        count++;
    }

    // Method to get the counter value
    public int getValue() {
        return count;
    }
}

// Task to increment the counter
class IncrementTask implements Runnable {
    private final Counter counter;

    public IncrementTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment();
            try {
                Thread.sleep(1); // Slight delay to make race conditions more apparent without synchronization
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}
