package org.example.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.example.chapter8.MyAppThread;

public class DynamicDeadlockHashcode {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(MyAppThread::new);
        DynamicDeadlockHashcode dynamicDeadlock = new DynamicDeadlockHashcode();

        Account accountX = new Account("accountX");
        Account accountY = new Account("accountY");
        DollarAmount amount = new DollarAmount();

        executorService.submit(() -> dynamicDeadlock.transferMoney(accountX, accountY, amount));
        executorService.submit(() -> dynamicDeadlock.transferMoney(accountY, accountX, amount));
        System.out.println("Exit");
    }

    private static final Object tieLock = new Object();

    public void transferMoney (Account fromAccount, Account toAccount, DollarAmount amount) {
        class Helper {
            public void transfer() {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    private static class Account {
        private final String name;

        public Account(String name) {
            this.name = name;
        }

        public Comparable<DollarAmount> getBalance() {
            return null;
        }

        public void debit(DollarAmount amount) {
            System.out.println("debit");

        }

        public void credit(DollarAmount amount) {
            System.out.println("credit");
        }

        @Override
        public String toString() {
            return "Account{" +
                "name='" + name + '\'' +
                '}';
        }
    }

    private static class DollarAmount {

    }
}
