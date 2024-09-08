package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> send(
                String.format("Notification {%s} to email {%s}", user.username(), user.email()),
                String.format("Add a new event to {%s}", user.username()),
                String.format("%s", user.email())));
    }

    public void send(String subject, String body, String email) {
        System.out.printf("%s %s %s", subject, body, email);
    }

    public void close() {
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}