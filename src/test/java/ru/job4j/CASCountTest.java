package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    void whenSingleThread() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        count.increment();
        assertThat(count.get()).isEqualTo(3);
    }

    @Test
    public void whenIncrementMultiThread() throws InterruptedException {
        CASCount count = new CASCount();
        Runnable incrementTask = count::increment;
        Thread thread1 = new Thread(incrementTask);
        Thread thread2 = new Thread(incrementTask);
        Thread thread3 = new Thread(incrementTask);
        Thread thread4 = new Thread(incrementTask);
        Thread thread5 = new Thread(incrementTask);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        assertThat(count.get()).isEqualTo(5);
    }
}