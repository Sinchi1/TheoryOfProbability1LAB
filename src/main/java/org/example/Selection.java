package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Selection {
    private final ArrayList<Double> selection;
    private final ArrayList<Double> sortedSelection;

    public Selection(List<Double> selection) {
        this.selection = new ArrayList<>(selection);
        this.sortedSelection = new ArrayList<>(this.selection);
        Collections.sort(sortedSelection);
    }

    public StringBuilder getStatisticSelection() {
        StringBuilder statisticSelection = new StringBuilder();
        double last = sortedSelection.get(0);
        int count = 0;

        for (double a : sortedSelection) {
            if (Double.compare(last, a) == 0) {
                count++;
            } else {
                statisticSelection.append("[%f, %d]\n".formatted(last, count));
                last = a;
                count = 1;
            }
        }
        // Добавляем последний элемент
        statisticSelection.append("[%f, %d]\n".formatted(last, count));
        return statisticSelection;
    }

    /*
    Эмпирическая функция
     */
    public ArrayList<double[]> getFunc() {
        ArrayList<double[]> func = new ArrayList<>();
        double n = sortedSelection.size();
        double last = sortedSelection.get(0);
        int count = 0;
        int afterCount = 0;

        for (double a : sortedSelection) {
            if (Double.compare(last, a) == 0) {
                count++;
                afterCount++;
            } else {
                func.add(new double[]{last, (double) (afterCount - count) / n});
                last = a;
                count = 1;
                afterCount++;
            }
        }
        // Добавляем последний элемент
        func.add(new double[]{last, (double) afterCount / n});
        return func;
    }

    @Override
    public String toString() {
        return selection.toString();
    }

    public ArrayList<Double> getSelection() {
        return selection;
    }

    public ArrayList<Double> getSorted() {
        return sortedSelection;
    }

    public double getMin() {
        return sortedSelection.get(0);
    }

    public double getMax() {
        return sortedSelection.get(sortedSelection.size() - 1);
    }

    public double getRange() {
        return getMax() - getMin();
    }
}
