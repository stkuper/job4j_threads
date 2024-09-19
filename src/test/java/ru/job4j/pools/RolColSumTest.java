package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    void whenSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        Sums[] result = RolColSum.sum(matrix);
        assertThat(result[1].getColSum()).isEqualTo(15);
        assertThat(result[0].getRowSum()).isEqualTo(6);
    }

    @Test
    void whenAssyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(result[2].getColSum()).isEqualTo(18);
        assertThat(result[2].getRowSum()).isEqualTo(24);
    }
}