package org.example.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrimeGeneratorTest {

    @Test
    void shouldThreadStopCallCancel() {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        new Thread(primeGenerator).start();

        try {
            Thread.sleep(1000); // 시작 후 1초 후에
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            primeGenerator.cancel(); // 취소요청
        }
        List<BigInteger> bigIntegers = primeGenerator.get();

        System.out.println("bigIntegers = " + bigIntegers);
    }
}