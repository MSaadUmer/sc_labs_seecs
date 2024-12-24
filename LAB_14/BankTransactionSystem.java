import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class BankTransactionSystem {
    public static void main(String[] args) {
        // Shared bank account
        BankAccount account = new BankAccount();

        // Create client threads
        Thread client1 = new Thread(new Client(account), "Client 1");
        Thread client2 = new Thread(new Client(account), "Client 2");
        Thread client3 = new Thread(new Client(account), "Client 3");

        // Start client threads
        client1.start();
        client2.start();
        client3.start();

        // Wait for all threads to finish
        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Print final account balance
        System.out.println("Final Account Balance: " + account.getBalance());
    }
}

// Bank account class with thread-safe operations
class BankAccount {
    private AtomicInteger balance = new AtomicInteger(0);

    // Method to deposit money
    public void deposit(int amount) {
        balance.getAndAdd(amount);
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount + " | Balance: " + balance.get());
    }

    // Method to withdraw money
    public void withdraw(int amount) {
        if (balance.get() >= amount) {
            balance.getAndAdd(-amount);
            System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + " | Balance: " + balance.get());
        } else {
            System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + " | Insufficient funds! Balance: " + balance.get());
        }
    }

    // Method to get the current balance
    public int getBalance() {
        return balance.get();
    }
}

// Client class representing a thread performing transactions
class Client implements Runnable {
    private final BankAccount account;
    private final Random random = new Random();

    public Client(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) { // Each client performs 5 transactions
            int transactionType = random.nextInt(2); // 0 for deposit, 1 for withdrawal
            int amount = random.nextInt(100) + 1; // Random amount between 1 and 100

            if (transactionType == 0) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }

            try {
                Thread.sleep(100); // Simulate processing time
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
            }
        }
    }
}
