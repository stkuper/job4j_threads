package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexOfItemTest {
    @Test
    void whenLineSearchIndexOfItem() {
        Item item = new Item(2);
        Item[] items = {new Item(1), item, new Item(3)};
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem<>(
                items, 0, items.length, item);
        assertThat(searchIndexOfItem.search(items, item)).isEqualTo(1);
    }

    @Test
    void whenParallelSearchIndexOfItem() {
        Item[] items = new Item[100];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i);
        }
        Item item = new Item(33);
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem(
                items, 0, items.length, item
        );
        assertThat(searchIndexOfItem.search(items, item)).isEqualTo(33);
    }

    @Test
    void whenNotSearchItem() {
        Item[] items = new Item[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i);
        }
        Item item = new Item(55);
        SearchIndexOfItem searchIndexOfItem = new SearchIndexOfItem(
                items, 0, items.length, item
        );
        assertThat(searchIndexOfItem.search(items, item)).isEqualTo(-1);
    }
}