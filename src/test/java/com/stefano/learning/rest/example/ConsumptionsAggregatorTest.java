package com.stefano.learning.rest.example;


import com.stefano.learning.core.domain.Consumption;
import com.stefano.learning.statistics.service.aggregator.ConsumptionsAggregatorImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConsumptionsAggregatorTest {

    ConsumptionsAggregatorImpl consumptionsAggregator;
    List<Consumption> consumptions;
    List<Consumption> consumptionsAugust2018;

    @Before
    public void initTestClass() {
        consumptionsAggregator = new ConsumptionsAggregatorImpl();
        consumptions = new ArrayList<>();

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-15"));
        consumption.setDriverId("12345");
        consumption.setFuelType("D");
        consumption.setPrice(new BigDecimal(20.4));
        consumption.setVolume(new BigDecimal(11));
        consumptions.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-10"));
        consumption.setDriverId("12345");
        consumption.setFuelType("D");
        consumption.setPrice(new BigDecimal(20.4));
        consumption.setVolume(new BigDecimal(30));
        consumptions.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-14"));
        consumption.setDriverId("12345");
        consumption.setFuelType("98");
        consumption.setPrice(new BigDecimal(10));
        consumption.setVolume(new BigDecimal(40));
        consumptions.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-09-01"));
        consumption.setDriverId("44444");
        consumption.setFuelType("95");
        consumption.setPrice(new BigDecimal(10.3));
        consumption.setVolume(new BigDecimal(5));
        consumptions.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-09-22"));
        consumption.setDriverId("11111");
        consumption.setFuelType("95");
        consumption.setPrice(new BigDecimal(3.3));
        consumption.setVolume(new BigDecimal(12));
        consumptions.add(consumption);

        consumptionsAugust2018 = new ArrayList<>();
        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-15"));
        consumption.setDriverId("12345");
        consumption.setFuelType("D");
        consumption.setPrice(new BigDecimal(20.4));
        consumption.setVolume(new BigDecimal(11));
        consumptionsAugust2018.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-10"));
        consumption.setDriverId("12345");
        consumption.setFuelType("D");
        consumption.setPrice(new BigDecimal(20.4));
        consumption.setVolume(new BigDecimal(30));
        consumptionsAugust2018.add(consumption);

        consumption = new Consumption();
        consumption.setId(1L);
        consumption.setDate(LocalDate.parse("2018-08-14"));
        consumption.setDriverId("12345");
        consumption.setFuelType("98");
        consumption.setPrice(new BigDecimal(10));
        consumption.setVolume(new BigDecimal(40));
        consumptionsAugust2018.add(consumption);
    }

    @Test
    public void shouldAggregateConsumptionsByMonthCorrectly() {
        Map<YearMonth, List<Consumption>> yearMonthListMap = consumptionsAggregator.groupByYearMonth(consumptions);

        assertEquals(yearMonthListMap.get(YearMonth.from(LocalDate.parse("2018-08-01"))).size(), 3);
        assertEquals(yearMonthListMap.get(YearMonth.from(LocalDate.parse("2018-09-01"))).size(), 2);
    }

    @Test
    public void shouldCalculateTotalPriceCorrectly() {
        BigDecimal result = consumptionsAggregator.calculateTotalPrice(consumptions);

        assertEquals(new BigDecimal(64.4).compareTo(result), 1);
    }

    @Test
    public void shouldAggregateConsumptionsByFuelTypeCorrectly() {
        Map<String, List<Consumption>> fuelTypeMap = consumptionsAggregator.groupByFuelType(consumptions);

        assertEquals(fuelTypeMap.get("95").size(), 2);
        assertEquals(fuelTypeMap.get("98").size(), 1);
    }

    @Test
    public void shouldAggregateConsumptionsByMonthAndFuelTypeCorrectly() {
        Map<YearMonth, Map<String, List<Consumption>>> monthFuelTypeMap = consumptionsAggregator.groupByMonthAndFuelType(consumptions);

        assertEquals(monthFuelTypeMap.get(YearMonth.from(LocalDate.parse("2018-08-01"))).get("D").size(), 2);
    }

    @Test
    public void reduceCalculatingTotalPriceShouldCalculateCorrectValues() {
        Map<YearMonth, List<Consumption>> groupingMap = new HashMap<>();
        YearMonth yearMonth = YearMonth.from(LocalDate.parse("2018-08-01"));
        groupingMap.put(yearMonth, consumptionsAugust2018);

        Map<YearMonth, BigDecimal> monthFuelTypeMap = consumptionsAggregator.reduceCalculatingTotalPrice(groupingMap);

        assertEquals(monthFuelTypeMap.get(yearMonth).compareTo(new BigDecimal(50.4)),1);
    }
}
