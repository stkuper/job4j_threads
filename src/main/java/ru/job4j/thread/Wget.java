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
    private final File file;

    public Wget(String url, int speed, File file) {
        validatorURL(url);
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            System.out.printf(
                    "Open connection: %d ms%n", System.currentTimeMillis() - startAt);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int countByteRead = 0;
            long startDownload = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                countByteRead += bytesRead;
                if (countByteRead >= speed) {
                    long endDownload = System.currentTimeMillis() - startDownload;
                    if (endDownload < 1000) {
                        try {
                            long sleep = 1000 - endDownload;
                            System.out.printf("Thread sleep on : %s ms%n", sleep);
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    startDownload = System.currentTimeMillis();
                    countByteRead = 0;
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
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        File file = new File(args[2]);
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }
}