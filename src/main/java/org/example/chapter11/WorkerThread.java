package org.example.chapter11;

import java.util.concurrent.BlockingQueue;

// WorkerThread 클래스가 병렬화되었다고 생각할 수 있다.
// 하지만 작업 큐(queue)에서 작업을 하나씩 뽑아 내는 부분은 순차적이다.
public class WorkerThread extends Thread {

    private final BlockingQueue<Runnable> queue;

    public WorkerThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 큐의 안정적인 상태를 위해 락을 사용했다면,
                // 특정 스레드가 큐에서 작업을 하나 뽑아내는 이 시점에,
                // 역시 큐에서 작업을 가져가고자 하는 다른 모든 스레드는 대기해야만 한다.
                Runnable task = queue.take();
                task.run();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
