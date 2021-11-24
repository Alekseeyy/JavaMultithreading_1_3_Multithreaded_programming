import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int SIZE = 1_000_000;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Integer[] array = createArray();
        System.out.printf("Массив случайных чисел размерностью %d\n%s\n", SIZE, Arrays.toString(array));
        long start = System.currentTimeMillis();
        int sum = sumArray(array);
        int average = sum / array.length;
        long result = System.currentTimeMillis() - start;
        System.out.printf("""
                ОДНОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА
                Сумма чисел массива: %d
                Среднее арифметическое элементов массива: %d
                Время выполнение задачи: %d мс
                """, sum, average, result);
        System.out.println();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long start11 = System.currentTimeMillis();
        int sum11 = forkJoinPool.invoke(new ArraySumTask(0, array.length, array));
        int average11 = sum / array.length;
        long result11 = System.currentTimeMillis() - start11;
        System.out.printf("""
                МНОГОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА
                Сумма чисел массива: %d
                Среднее арифметическое элементов массива: %d
                Время выполнение задачи: %d мс
                """, sum11, average11, result11);
        System.out.println();
        if (result == result11) {
            System.out.println("МЕТОДЫ РАВНЫ");
        } else if (result < result11) {
            System.out.println("ОДНОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА БЫСТРЕЕ");
        } else {
            System.out.println("МНОГОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА БЫСТРЕЕ");
        }
    }

    private static int sumArray(Integer[] array) {
        int sum = 0;
        for (Integer arr : array) {
            sum += arr;
        }
        return sum;
    }

    private static Integer[] createArray() {
        Integer[] array = new Integer[SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(SIZE);
        }
        return array;
    }
}
