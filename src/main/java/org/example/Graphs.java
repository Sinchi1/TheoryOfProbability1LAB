package org.example;

import java.awt.*;
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
import java.util.List;

public class Graphs {
    private final Selection selection;
    private final int numIntervals;
    private final double intervalLength;

    public Graphs(Selection selection) {
        this.selection = selection;

        // Вычисление количества интервалов по правилу Стерджесса:
        // k = ⌈1 + log2(N)⌉
        numIntervals = (int) Math.ceil(1 + Math.log(selection.getSelection().size()) / Math.log(2));

        // Длина интервала: range / numIntervals
        intervalLength = selection.getRange() / numIntervals;
    }

    public StringBuilder getIntervals() {
        // Строка для хранения информации об интервалах
        StringBuilder info = new StringBuilder("Количество интервалов: ")
                .append(numIntervals)
                .append(", Длина интервала: ")
                .append(intervalLength)
                .append("\n");

        double start = selection.getMin() - intervalLength/2; // Начало первого интервала

        for (int i = 0; i < numIntervals; i++) {
            double end = start + intervalLength; // Конец интервала
            double finalStart = start;
            long count = selection.getSelection().stream()
                    .filter(x -> x >= finalStart && x < end)
                    .count(); // Количество элементов в интервале

            info.append("Интервал [%f, %f): Частота = %d, Частотность = %f"
                            .formatted(start, end, count, (double) count / selection.getSelection().size()))
                    .append("\n");
            start = end; // Переход к следующему интервалу
        }
        return info;
    }

    public void getFuncChart() {
        XYSeries series = new XYSeries("Эмпирическая функция распределения");
        List<double[]> func = selection.getFunc(); // Получение значений функции распределения

        // Добавляем точки для ступенчатой линии
        double lastX = func.get(0)[0];
        series.add(lastX, 0); // Начинаем с F(x) = 0

        for (double[] point : func) {
            series.add(point[0], series.getY(series.getItemCount() - 1)); // Горизонтальная линия
            series.add(point[0], point[1]); // Вертикальный шаг
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Создаем график
        JFreeChart chart = ChartFactory.createXYStepChart(
                "Эмпирическая функция распределения",
                "Значения",
                "Вероятность",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Явно указываем NumberAxis для обоих осей
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainAxis(new NumberAxis("Значения")); // Ось X
        plot.setRangeAxis(new NumberAxis("Вероятность")); // Ось Y

        // Настройки для осей
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false); // Отключение включения 0 по оси X

        JFrame frame = new JFrame("Эмпирическая функция распределения");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void createPolygon() {
        XYSeries series = new XYSeries("Selection");

        double start = selection.getMin(); // Начало первого интервала
        for (int i = 0; i < numIntervals; i++) {
            double end = start + intervalLength; // Конец интервала
            double finalStart = start;

            // Количество элементов в интервале
            long count = selection.getSelection().stream()
                    .filter(x -> x >= finalStart && x < end)
                    .count();

            // Середина интервала: start + intervalLength / 2
            series.add(start + intervalLength / 2, count);

            start = end; // Переход к следующему интервалу
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Полигон",
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
        double start = selection.getMin();
        long[] frequency = new long[numIntervals];

        for (int i = 0; i < numIntervals; i++) {
            double end = start + intervalLength;
            double finalStart = start;
            long count = selection.getSelection().stream()
                    .filter(x -> x >= finalStart && x < end)
                    .count();
            frequency[i] = count;
            start = end;
        }

        for (int i = 0; i < numIntervals; i++) {
            dataset.addValue(frequency[i], "Частота", "Интервал " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Гистограмма",
                "Интервалы",
                "Частота",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Гистограмма");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
