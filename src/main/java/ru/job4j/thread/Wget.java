package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        validatorURL(url);
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        File file = new File("tmp.xml");
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            System.out.printf(
                    "Open connection: %d ms%n", System.currentTimeMillis() - startAt);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                long download = System.nanoTime() - downloadAt;
                long sleep = (long) Math.floor((1024.0 / download * 1000000) / speed);
                System.out.printf("Read 1024 bytes : %s nano.%n", download);
                if (sleep > 1) {
                    try {
                        System.out.printf("Thread sleep on : %s ms%n", sleep);
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validatorURL(String url) {
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException(
                    String.format("This url %s is invalid", url)
            );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}