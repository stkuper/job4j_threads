package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        CompletableFuture<Sums>[] completableFutures = new CompletableFuture[size];
        for (int i = 0; i < size; i++) {
            int index = i;
            completableFutures[i] = CompletableFuture.supplyAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < size; j++) {
                    rowSum += matrix[index][j];
                    colSum += matrix[j][index];
                }
                sums[index] = new Sums(rowSum, colSum);
                return sums[index];
            });
        }
        for (int i = 0; i < size; i++) {
            sums[i] = completableFutures[i].get();
        }
        return sums;
    }
}