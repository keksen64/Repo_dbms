package dbms.main.performance.analysis;

public class HighPrecisionTiming {
    public static void main(String[] args) {
        // Начало измерения времени
        long startTime = System.nanoTime();

        // Блок кода, время выполнения которого нужно измерить
        performTask();

        // Конец измерения времени
        long endTime = System.nanoTime();

        // Вычисление времени выполнения
        long duration = endTime - startTime;

        // Печать времени выполнения в наносекундах
        System.out.println("Время выполнения в наносекундах: " + duration);

        // Печать времени выполнения в миллисекундах для удобства
        System.out.println("Время выполнения в миллисекундах: " + (duration / 1_000_000.0));
    }

    private static void performTask() {
        // Пример задачи для измерения времени выполнения
        try {
            Thread.sleep(1000); // Сон на 1 секунду
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
