package org.example.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.example.chapter8.MyAppThread;

public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            System.out.println("[" + Thread.currentThread().getName() + "] leftRight(): left");
            synchronized (right) {
                System.out.println("[" + Thread.currentThread().getName() + "] leftRight(): right");
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            System.out.println("[" + Thread.currentThread().getName() + "] rightLeft(): right");
            synchronized (left) {
                System.out.println("[" + Thread.currentThread().getName() + "] rightLeft(): left");
                doSomething();
            }
        }
    }

    private void doSomething() {
        System.out.println("[" + Thread.currentThread().getName() + "] doSomething");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5, MyAppThread::new);
        LeftRightDeadlock leftRightDeadlock = new LeftRightDeadlock();

        executorService.submit(leftRightDeadlock::rightLeft);
        executorService.submit(leftRightDeadlock::leftRight);
    }
}
