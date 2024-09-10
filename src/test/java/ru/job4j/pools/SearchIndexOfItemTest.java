package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexOfItemTest {
    @Test
    void whenLineSearchIndexOfItem() {
        Item[] items = {new Item(1), new Item(2), new Item(3)};
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem<>(
                items, 0, items.length, new Item(2));
        assertThat(searchIndexOfItem.lineSearchIndexOfItem()).isEqualTo(1);
    }

    @Test
    void whenParallelSearchIndexOfItem() {
        Item[] items = new Item[100];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i);
        }
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem(
                items, 0, items.length, new Item(33)
        );
        assertThat(searchIndexOfItem.search(items, new Item(33))).isEqualTo(33);
    }

    @Test
    void whenNotSearchItem() {
        Item[] items = new Item[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i);
        }
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem(
                items, 0, items.length, new Item(55)
        );
        assertThat(searchIndexOfItem.search(items, new Item(55))).isEqualTo(-1);
    }
}