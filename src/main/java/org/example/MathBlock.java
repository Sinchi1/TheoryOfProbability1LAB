package org.example;

import java.util.ArrayList;

public class MathBlock {
    private final ArrayList<Double> selection;          // Выборка
    private Double mathExpectation;                     // Математическое ожидание
    private Double sampleVariance;                      // Выборочная дисперсия
    private Double correctedVariance;                   // Исправленная дисперсия
    private Double sampleStdDeviation;                  // Выборочное стандартное отклонение
    private Double correctedStdDeviation;               // Исправленное стандартное отклонение
    private Double arithmeticMean;                      // Среднее арифметическое

    public MathBlock(Selection selection) {
        this.selection = selection.getSelection();
    }

    public double getMathExpectation() {
        if (mathExpectation == null) {
            calculateStatistics();
        }
        return mathExpectation;
    }

    public double getArithmeticMean() {
        if (arithmeticMean == null) {
            calculateStatistics();
        }
        return arithmeticMean;
    }

    public double getSampleVariance() {
        if (sampleVariance == null) {
            calculateStatistics();
        }
        return sampleVariance;
    }

    public double getCorrectedVariance() {
        if (correctedVariance == null) {
            calculateStatistics();
        }
        return correctedVariance;
    }

    public double getSampleStdDeviation() {
        if (sampleStdDeviation == null) {
            calculateStatistics();
        }
        return sampleStdDeviation;
    }

    public double getCorrectedStdDeviation() {
        if (correctedStdDeviation == null) {
            calculateStatistics();
        }
        return correctedStdDeviation;
    }

    private void calculateStatistics() {
        int n = selection.size();

        // Вычисляем математическое ожидание (выборочное среднее)
        mathExpectation = 0.0;
        for (double value : selection) {
            mathExpectation += value;
        }
        mathExpectation /= n;
        arithmeticMean = mathExpectation;

        // Вычисляем выборочную дисперсию
        sampleVariance = 0.0;
        for (double value : selection) {
            sampleVariance += Math.pow(value - arithmeticMean, 2);
        }

        // Исправленная дисперсия и выборочная дисперсия
        correctedVariance = sampleVariance;
        sampleVariance /= n;
        correctedVariance /= (n - 1);

        // Стандартные отклонения
        sampleStdDeviation = Math.sqrt(sampleVariance);
        correctedStdDeviation = Math.sqrt(correctedVariance);
    }
}
