package com.stefano.learning.statistics.service.aggregator;

import com.stefano.learning.core.domain.Consumption;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface ConsumptionsAggregator {
    Map<YearMonth, List<Consumption>> groupByYearMonth(List<Consumption> consumptions);

    BigDecimal calculateTotalPrice(List<Consumption> consumptions);

    BigDecimal calculateTotalVolume(List<Consumption> consumptions);

    int count(List<Consumption> consumptions);

    Map<String, List<Consumption>> groupByFuelType(List<Consumption> consumptions);

    Map<YearMonth, BigDecimal> reduceCalculatingTotalPrice(Map<YearMonth, List<Consumption>> groupingMap);

    Map<YearMonth, Map<String, List<Consumption>>> groupByMonthAndFuelType(List<Consumption> consumptions);
}
