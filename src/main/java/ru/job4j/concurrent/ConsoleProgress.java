package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            var process = new char[]{'-', '\\', '|', '/'};
            int index = 0;
            int percent = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.printf(
                        "\r %s... %d%c   %c", "Loading", percent++, '%', process[index++]);
                index %= process.length;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}