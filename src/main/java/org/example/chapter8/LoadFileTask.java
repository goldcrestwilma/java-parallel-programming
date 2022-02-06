package org.example.chapter8;

import java.util.concurrent.Callable;

public class LoadFileTask implements Callable<String> {

    private String fileName;

    public LoadFileTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String call() throws Exception {
        System.out.println("LoadFileTask call..");
        return fileName;
    }
}
