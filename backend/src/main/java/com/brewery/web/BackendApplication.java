package com.brewery.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.*;
import java.util.stream.Collectors;

@EnableCassandraRepositories
@SpringBootApplication(scanBasePackages = { "com.brewery.web" })
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    public static int[] mergeSort(int[] numbers) {
        // Base case: zero or one element is already sorted.
        if (numbers.length <= 1) {
            return numbers.clone();
        }

        int middleIndex = numbers.length / 2;

        int[] leftHalf = Arrays.copyOfRange(
                numbers, 0, middleIndex
        );
        int[] rightHalf = Arrays.copyOfRange(
                numbers, middleIndex, numbers.length
        );

        // Recursive calls: sort each smaller array.
        int[] sortedLeftHalf = mergeSort(leftHalf);
        int[] sortedRightHalf = mergeSort(rightHalf);

        return merge(sortedLeftHalf, sortedRightHalf);
    }

    private static int[] merge(int[] sortedLeftHalf, int[] sortedRightHalf) {
        int[] mergedNumbers = new int[sortedLeftHalf.length + sortedRightHalf.length];

        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        while (leftIndex < sortedLeftHalf.length && rightIndex < sortedRightHalf.length) {
            if(sortedLeftHalf[leftIndex] <= sortedRightHalf[rightIndex]) {
                mergedNumbers[mergedIndex] = sortedLeftHalf[leftIndex];
                leftIndex++;
            } else {
                mergedNumbers[mergedIndex] = sortedRightHalf[rightIndex];
                rightIndex++;
            }

            mergedIndex++;
        }

        while(leftIndex < sortedLeftHalf.length) {
            mergedNumbers[mergedIndex] = sortedLeftHalf[leftIndex];
            leftIndex++;
            mergedIndex++;
        }

        while(rightIndex < sortedRightHalf.length) {
            mergedNumbers[mergedIndex] = sortedRightHalf[rightIndex];
            rightIndex++;
            mergedIndex++;
        }

        return mergedNumbers;
    }

}
