package ru.job4j.concurrent;

public final class Cache {
    private static Cache cache;

    private Cache() {
    }

    public static synchronized Cache getInstance() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}