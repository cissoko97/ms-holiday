package org.ck.holiday.securityservice.coding;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MaxAverage {

    public String[] stocks = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    public Float[][] prices = {
            {12.01F, 12.13F, 14.41F, 7F, 6F, 8F, 10F},
            {6.01F, 4.13F, 8.41F, 5.05F, 4.08F, 3.89F, 74.08F},
            {6.01F, 4.13F, 8.41F, 5.05F, 4.08F, 3.89F, 8.08F},
    };

    @BeforeAll
    void init() {

    }

    @AfterAll
    void destroy() {

    }

    @Test
    void maxAverageTest(String[] stocks, Float[][] prices) {
        Map<String, Float> averages = new TreeMap<>();
        for (Float[] data : prices) {
            for (int i = 0; i < data.length; i++) {
                averages.put(stocks[i], data[i] + averages.getOrDefault(stocks[i], 0.0F));
            }
        }

        List<String> topStocks = new ArrayList<>(averages.keySet());
        Collections.sort(topStocks, (s1, s2) -> Float.compare(averages.get(s2), averages.get(s1)));
        String[] strings = topStocks.subList(0, 3).toArray(new String[3]);

        for (String s : strings) {
            System.out.println(s);
        }

    }
}
