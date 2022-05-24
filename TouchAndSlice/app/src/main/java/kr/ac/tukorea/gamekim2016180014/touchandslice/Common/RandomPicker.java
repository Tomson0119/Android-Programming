package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import java.util.Arrays;
import java.util.Random;

public class RandomPicker {
    private int[] numbers;
    private final Random random;

    public RandomPicker(int[] probNums) {
        random = new Random();
        setNumbers(probNums);
    }

    private void setNumbers(int[] probNums) {
        int sum = 0;
        for(int e : probNums) sum += e;

        numbers = new int[sum];
        int k = 0;
        int counter = 0;
        for(int i = 0; i < numbers.length; i++) {
            if(counter == probNums[k]) {
                counter = 0;
                k += 1;
            }
            counter += 1;
            numbers[i] = k;
        }
    }

    public int getRandomNum() {
        int idx = random.nextInt(numbers.length);
        return numbers[idx];
    }
}
