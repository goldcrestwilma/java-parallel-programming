package org.example.chapter8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header;
            Future<String> footer;
            header = executorService.submit(() -> "this is header");
            footer = executorService.submit(() -> "this is footer");
            return header.get() + " / " + footer.get();
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadDeadlock threadDeadlock = new ThreadDeadlock();
        threadDeadlock.render();
    }

    public void render() throws Exception {
        RenderPageTask renderPageTask = new RenderPageTask();
        String call = renderPageTask.call();
        System.out.println(call);
    }
}
