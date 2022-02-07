package org.example.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.InsufficientResourcesException;

import org.example.chapter8.MyAppThread;

public class DynamicDeadlock {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5, MyAppThread::new);
        DynamicDeadlock dynamicDeadlock = new DynamicDeadlock();

        Account accountX = new Account("accountX");
        Account accountY = new Account("accountY");
        DollarAmount amount = new DollarAmount();

        executorService.submit(() -> {
            try {
                dynamicDeadlock.transferMoney(accountX, accountY, amount);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                dynamicDeadlock.transferMoney(accountY, accountX, amount);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        });
    }

    public void transferMoney (Account fromAccount, Account toAccount, DollarAmount amount) throws InsufficientFundsException {
        synchronized (fromAccount) {
            System.out.println("[" + Thread.currentThread().getName() + "] from: " + fromAccount);
            synchronized (toAccount) {
                System.out.println("[" + Thread.currentThread().getName() + "] to: " + toAccount);
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
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

        }

        public void credit(DollarAmount amount) {

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

    private class InsufficientFundsException extends Throwable {

    }
}
