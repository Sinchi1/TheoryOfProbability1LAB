package org.example;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        var sampleData = Stream.of(
                -0.26, 1.49, -1.54, -1.33, -1.68, -1.55, 0.34, -0.84,
                -1.72, 0.34, -0.58, -0.84, 1.13, -0.78, -0.94, 1.54, 0.58, -1.58,
                -0.49, -0.14
        ).map(Double::doubleValue).toList();


        Selection selection = new Selection(sampleData);


        ResultPrinter.displaySelectionInfo(selection);


        MathBlock mathBlock = new MathBlock(selection);
        ResultPrinter.displayStatistics(mathBlock);


        ResultPrinter.displayEmpiricalFunction(selection);


        Graphs charts = new Graphs(selection);
        ResultPrinter.displayIntervals(charts.getIntervals());

        charts.getFuncChart(); // Отображение графика функции распределения
        charts.createHistogram(); // Построение гистограммы
        charts.createPolygon(); // Построение полигона
    }
}
