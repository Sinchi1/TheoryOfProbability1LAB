package org.example;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class MathBlock {
    private final ArrayList<BigDecimal> selection;          // Выборка
    private BigDecimal mathExpectation;                     // Математическое ожидание
    private BigDecimal sampleVariance;                      // Выборочная дисперсия
    private BigDecimal correctedVariance;                   // Исправленная дисперсия
    private BigDecimal sampleStdDeviation;                  // Выборочное стандартное отклонение
    private BigDecimal correctedStdDeviation;               // Исправленное стандартное отклонение
    private BigDecimal arithmeticMean;                      // Среднее арифметическое

    public MathBlock(Selection selection) {
        this.selection = selection.getSelection();
    }

    public BigDecimal getMathExpectation() {
        if (mathExpectation == null) {
            calculateStatistics();
        }
        return mathExpectation;
    }

    public BigDecimal getArithmeticMean() {
        if (arithmeticMean == null) {
            calculateStatistics();
        }
        return arithmeticMean;
    }

    public BigDecimal getSampleVariance() {
        if (sampleVariance == null) {
            calculateStatistics();
        }
        return sampleVariance;
    }

    public BigDecimal getCorrectedVariance() {
        if (correctedVariance == null) {
            calculateStatistics();
        }
        return correctedVariance;
    }

    public BigDecimal getSampleStdDeviation() {
        if (sampleStdDeviation == null) {
            calculateStatistics();
        }
        return sampleStdDeviation;
    }

    public BigDecimal getCorrectedStdDeviation() {
        if (correctedStdDeviation == null) {
            calculateStatistics();
        }
        return correctedStdDeviation;
    }

    private void calculateStatistics() {
        MathContext mc = new MathContext(30); // Точность расчётов
        BigDecimal n = BigDecimal.valueOf(selection.size());

        // Вычисляем математическое ожидание (выборочное среднее)
        mathExpectation = BigDecimal.ZERO;
        for (BigDecimal value : selection) {
            mathExpectation = mathExpectation.add(value);
        }
        mathExpectation = mathExpectation.divide(n, mc);
        arithmeticMean = mathExpectation;

        // Вычисляем выборочную дисперсию
        sampleVariance = BigDecimal.ZERO;
        for (BigDecimal value : selection) {
            sampleVariance = sampleVariance.add(value.subtract(arithmeticMean).pow(2));
        }

        // Исправленная дисперсия и выборочная дисперсия
        correctedVariance = sampleVariance;
        sampleVariance = sampleVariance.divide(n, mc);
        correctedVariance = correctedVariance.divide(n.subtract(BigDecimal.ONE), mc);

        // Стандартные отклонения
        sampleStdDeviation = sampleVariance.sqrt(mc);
        correctedStdDeviation = correctedVariance.sqrt(mc);
    }
}
