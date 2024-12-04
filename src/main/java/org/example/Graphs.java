package org.example;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Graphs {
    Selection selection;
    int numIntervals; // Количество интервалов
    BigDecimal intervalLength; // Длина интервала
    MathContext mc = new MathContext(5);

    public StringBuilder getIntervals() {
        // Строка для хранения информации об интервалах
        StringBuilder Info = new StringBuilder("Количество интервалов: ")
                .append(numIntervals)
                .append(", Длина интервала: ")
                .append(intervalLength)
                .append("\n");

        BigDecimal start = selection.getMin(); // Начало первого интервала

        for (int i = 0; i < numIntervals; i++) {
            BigDecimal end = start.add(intervalLength); // Конец интервала: start + intervalLength
            BigDecimal finalStart = start;
            long count = selection.getSelection().stream()
                    .filter(x -> x.compareTo(finalStart) >= 0 && x.compareTo(end) <= 0)
                    .count(); // Количество элементов в интервале
            Info.append("Интервал [%f, %f): Частота = %d, Частотность = %f"
                            .formatted(start, end, count, count / Double.valueOf(selection.getSelection().size())))
                    .append("\n");
            start = end; // Переход к следующему интервалу
        }
        return Info;
    }

    public void getFuncChart() {
        XYSeries series = new XYSeries("Эмпирическая функция распределения");
        var func = selection.getFunc(); // Получение значений функции распределения
        int n = func.size();

        for (int i = 0; i < n; i++) {
            series.add(func.get(i)[0], func.get(i)[1]); // Добавление точек для графика
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Эмпирическая функция распределения",
                "Значения",
                "Вероятность",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        JFrame frame = new JFrame("Эмпирическая функция распределения");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public Graphs(Selection selection) {
        this.selection = selection;

        // Вычисление количества интервалов по правилу Стерджесса:
        // k = ⌈1 + log2(N)⌉
        numIntervals = (int) Math.ceil(Math.round(1 + Math.log(selection.getSelection().size()) / Math.log(2)));

        // Длина интервала: range / numIntervals
        intervalLength = selection.getRange().divide(BigDecimal.valueOf(numIntervals), mc);
    }

    public void createPolygon() {
        XYSeries series = new XYSeries("Selection");

        BigDecimal start = selection.getMin(); // Начало первого интервала
        for (int i = 0; i < numIntervals; i++) {
            BigDecimal end = start.add(intervalLength); // Конец интервала: start + intervalLength
            BigDecimal finalStart = start;

            // Количество элементов в интервале
            long count = selection.getSelection().stream()
                    .filter(x -> x.compareTo(finalStart) >= 0 && x.compareTo(end) <= 0)
                    .count();

            // Середина интервала: start + (intervalLength / 2) * (i + 1)
            series.add(selection.getMin().add(intervalLength.divide(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(i + 1))), count);

            start = end; // Переход к следующему интервалу
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Полигон интервального ряда",
                "Середины интервалов",
                "Частота",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Полигон интервального ряда");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void createHistogram() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int numBins = numIntervals;
        BigDecimal start = selection.getMin();
        long[] frequency = new long[numBins];

        for (int i = 0; i < numIntervals; i++) {
            BigDecimal end = start.add(intervalLength);
            BigDecimal finalStart = start;
            long count = selection.getSelection().stream().filter(x -> x.compareTo(finalStart) >= 0 && x.compareTo(end) <= 0).count();
            frequency[i] = count;
            start = end;
        }

        for (int i = 0; i < numBins; i++) {
            dataset.addValue(frequency[i], "Частота", "Интервал " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createBarChart("Гистограмма", "Интервалы", "Частота", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Гистограмма");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}