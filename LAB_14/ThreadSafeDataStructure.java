import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeDataStructure {
    public static void main(String[] args) {
        // Shared thread-safe list
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        // Create threads to write to the shared list
        Thread writer1 = new Thread(new ListWriter(sharedList, 1, 5));
        Thread writer2 = new Thread(new ListWriter(sharedList, 6, 10));
        Thread writer3 = new Thread(new ListWriter(sharedList, 11, 15));

        // Create threads to read from the shared list
        Thread reader1 = new Thread(new ListReader(sharedList));
        Thread reader2 = new Thread(new ListReader(sharedList));

        // Start all threads
        writer1.start();
        writer2.start();
        writer3.start();
        reader1.start();
        reader2.start();

        // Wait for all writer threads to finish
        try {
            writer1.join();
            writer2.join();
            writer3.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        // Print the final state of the shared list
        System.out.println("Final Shared List: " + sharedList);
    }
}

// Task to write to the shared list
class ListWriter implements Runnable {
    private final CopyOnWriteArrayList<Integer> list;
    private final int start;
    private final int end;

    public ListWriter(CopyOnWriteArrayList<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            list.add(i);
            System.out.println(Thread.currentThread().getName() + " added: " + i);
            try {
                Thread.sleep(100); // Simulate delay
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

// Task to read from the shared list
class ListReader implements Runnable {
    private final CopyOnWriteArrayList<Integer> list;

    public ListReader(CopyOnWriteArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " reading: " + list);
            try {
                Thread.sleep(200); // Simulate delay
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                break;
            }
        }
    }
}
