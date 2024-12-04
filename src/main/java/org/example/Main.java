package org.example;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class Main {

    ResultPrinter resultPrinter;
    public static void main(String[] args) {


        var sampleData = Stream.of(
                -0.26, 1.49, -1.54, -1.33, -1.68, -1.55, 0.34, -0.84,
                -1.72, 0.34, -0.58, -0.84, 1.13, -0.78, -0.94, 1.54, 0.58, -1.58,
                -0.49, -0.14
        ).map(BigDecimal::valueOf).toList();

        Selection selection = new Selection(sampleData);

        ResultPrinter.displaySelectionInfo(selection);

        MathBlock mathBlock = new MathBlock(selection);
        ResultPrinter.displayStatistics(mathBlock);

        System.out.println("Эмпирическая функция распределения:");
        selection.getFunc().forEach(
                arr -> System.out.println("Fn(" + arr[0] + ") = " + arr[1])
        );


        Graphs charts = new Graphs(selection);
        System.out.println("Группированный (интервальный) ряд:");
        System.out.println(charts.getIntervals());


        charts.getFuncChart();
        charts.createHistogram();
        charts.createPolygon();
    }


}
