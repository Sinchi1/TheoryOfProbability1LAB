package org.example;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Selection {
    private final ArrayList<BigDecimal> selection;
    private final ArrayList<BigDecimal> sortedSelection;

    public Selection(List<BigDecimal> selection){
        this.selection = new ArrayList<>(selection);
        sortedSelection = (ArrayList<BigDecimal>) this.selection.clone();
        Collections.sort(sortedSelection);
    }
    public StringBuilder getStatisticSelection(){
        StringBuilder statisticSelection = new StringBuilder("");
        BigDecimal last = sortedSelection.get(0);
        int count = 0;
        for(BigDecimal a: sortedSelection){
            if(last.equals(a)){
                count = count + 1;
            } else {
                statisticSelection.append("[%s, %d]\n".formatted(last, count));
                last = a;
                count = 1;
            }
        }
        return statisticSelection;
    }
    public ArrayList<BigDecimal[]> getFunc() {
        ArrayList<BigDecimal[]> func = new ArrayList<>();
        BigDecimal last = sortedSelection.get(0);
        BigDecimal n = BigDecimal.valueOf(selection.size());
        int count = 0;
        int afterCount = 0;
        MathContext mc = new MathContext(30);
        for(BigDecimal a: sortedSelection){
            if(last.equals(a)){
                count = count + 1;
                afterCount = afterCount + 1;
            } else {
                BigDecimal[] b = {a, BigDecimal.valueOf(afterCount-count).divide(n, mc)};
                func.add(b);
                afterCount = afterCount + 1;
            }
        }
        return func;
    }
    @Override
    public String toString(){
        return selection.toString();
    }

    public ArrayList<BigDecimal> getSelection(){
        return selection;
    }
    public ArrayList<BigDecimal> getSorted(){
        return sortedSelection;
    }
    public BigDecimal getMin(){
        return sortedSelection.get(0);
    }
    public BigDecimal getMax(){
        return sortedSelection.get(sortedSelection.size()-1);
    }
    public BigDecimal getRange(){
        return (sortedSelection.get(sortedSelection.size()-1).add(sortedSelection.get(0).negate()));
    }
}


