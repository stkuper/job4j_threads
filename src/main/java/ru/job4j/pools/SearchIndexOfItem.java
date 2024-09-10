package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndexOfItem<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int fromIndex;
    private final int toIndex;
    private final T item;

    public SearchIndexOfItem(T[] array, int fromIndex, int toIndex, T item) {
        this.array = array;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.item = item;
    }

    @Override
    protected Integer compute() {
        if (toIndex - fromIndex <= 10) {
            return lineSearchIndexOfItem();
        }
        int middle = (fromIndex + toIndex) / 2;
        SearchIndexOfItem leftSearch = new SearchIndexOfItem(array, fromIndex, middle, item);
        SearchIndexOfItem rightSearch = new SearchIndexOfItem(array, middle + 1, toIndex, item);
        leftSearch.fork();
        rightSearch.fork();
        return Math.min((int) leftSearch.join(), (int) rightSearch.join());
    }

    protected Integer lineSearchIndexOfItem() {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (item.equals(array[i])) {
                result = i;
            }
        }
        return result;
    }

    public Integer search(T[] array, T item) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new SearchIndexOfItem<T>(array, fromIndex, toIndex, item));
    }
}
