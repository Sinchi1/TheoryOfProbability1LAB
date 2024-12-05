package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultPrinter {

    private static final String OUTPUT_FILE = "src/main/java/org/example/result.txt";

    /**
     * Вывод информации о выборке.
     */
    public static void displaySelectionInfo(Selection selection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Выборка: " + selection + "\n\n");
            writer.write("Максимум выборки: " + selection.getMax() + "\n");
            writer.write("Минимум выборки: " + selection.getMin() + "\n");
            writer.write("Размах выборки: " + selection.getRange() + "\n\n");
            writer.write("Вариационный ряд: " + selection.getSorted() + "\n");
            writer.write("Статистический ряд:\n" + selection.getStatisticSelection() + "\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Вывод статистических характеристик выборки.
     */
    public static void displayStatistics(MathBlock mathBlock) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Выборочное среднее: " + mathBlock.getArithmeticMean() + "\n");
            writer.write("Математическое ожидание: " + mathBlock.getMathExpectation() + "\n");
            writer.write("Выборочная дисперсия: " + mathBlock.getSampleVariance() + "\n");
            writer.write("Выборочное Средне Квадратичное отклонение: " + mathBlock.getSampleStdDeviation() + "\n");
            writer.write("Исправленная дисперсия: " + mathBlock.getCorrectedVariance() + "\n");
            writer.write("Исправленное Средне Квадратичное отклонение: " + mathBlock.getCorrectedStdDeviation() + "\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запись эмпирической функции распределения.
     */
    public static void displayEmpiricalFunction(Selection selection) {
        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Эмпирическая функция распределения:\n");
            writer.write("Fn(" + 0.0 + ") при x <" + selection.getMin() + ", ");
            double last = selection.getMin();
            for (double[] arr : selection.getFunc() ) {
                if (last == arr[0]){
                    continue;
                }
                if (arr[0] == selection.getMax()){
                    writer.write("Fn(" + 0.95 + ") при "+ last + " < x <= " + arr[0] + ", \n");
                    continue;
                }
                writer.write("Fn(" + arr[1] + ") при "+ last + " < x <= " + arr[0] + ", \n");
                last = arr[0];
            }
            writer.write("Fn(" + 1.0 + ") при x > " + selection.getMax() + "\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запись интервалов.
     */
    public static void displayIntervals(StringBuilder intervals) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("Группированный (интервальный) ряд:\n");
            writer.write(intervals.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
