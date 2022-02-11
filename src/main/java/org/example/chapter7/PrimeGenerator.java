package org.example.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class PrimeGenerator implements Runnable {

    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger prime = BigInteger.ONE;
        while (!cancelled) {
            prime = prime.nextProbablePrime();
            synchronized (this) {
                primes.add(prime);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }
}
