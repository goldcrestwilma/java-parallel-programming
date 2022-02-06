package org.example.chapter8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.text.TabExpander;

public class ThreadDeadlock {
    ExecutorService executorService = Executors.newSingleThreadExecutor(MyAppThread::new);

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header;
            Future<String> footer;
            header = executorService.submit(new LoadFileTask("header.html"));
            footer = executorService.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return "render body";
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadDeadlock threadDeadlock = new ThreadDeadlock();
        Future<String> result = threadDeadlock.executorService.submit(threadDeadlock.new RenderPageTask());

        try {
            System.out.println("result: " + result.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("e = " + e);
        } finally {
            threadDeadlock.executorService.shutdown();
        }
    }
}
