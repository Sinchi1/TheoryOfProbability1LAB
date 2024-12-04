package org.example;

public class ResultPrinter {

    /**
     * Вывод информации о выборке.
     */
    public static void displaySelectionInfo(Selection selection) {
        System.out.println("Выборка: " + selection);
        System.out.println("Вариационный ряд: " + selection.getSorted());
        System.out.println("Статистический ряд:\n" + selection.getStatisticSelection());
        System.out.println("Максимум: " + selection.getMax());
        System.out.println("Минимум: " + selection.getMin());
        System.out.println("Размах: " + selection.getRange());
    }

    /**
     * Вывод статистических характеристик выборки.
     */
    public static void displayStatistics(MathBlock mathBlock) {
        System.out.println("Выборочное среднее: " + mathBlock.getArithmeticMean());
        System.out.println("Мат. ожидание: " + mathBlock.getMathExpectation());
        System.out.println("Выборочная дисперсия: " + mathBlock.getSampleVariance());
        System.out.println("Выборочное СКО: " + mathBlock.getSampleStdDeviation());
        System.out.println("Исправленная дисперсия: " + mathBlock.getCorrectedVariance());
        System.out.println("Исправленное СКО: " + mathBlock.getCorrectedStdDeviation());
    }

}
