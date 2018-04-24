package com.stefano.learning.utils.aggregator;

import com.stefano.learning.domain.Consumption;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class ConsumptionsAggregatorImpl implements ConsumptionsAggregator {

    @Override
    public Map<YearMonth, List<Consumption>> groupByYearMonth(List<Consumption> consumptions) {
        return consumptions.stream().collect(groupingBy(item -> YearMonth.from(item.getDate())));
    }

    @Override
    public BigDecimal calculateTotalPrice(List<Consumption> consumptions) {
        return consumptions.stream().map(Consumption::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalVolume(List<Consumption> consumptions) {
        return consumptions.stream().map(Consumption::getVolume).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long count(List<Consumption> consumptions) {
        return consumptions.stream().count();
    }

    @Override
    public Map<String, List<Consumption>> groupByFuelType(List<Consumption> consumptions) {
        return consumptions.stream().collect(groupingBy(Consumption::getFuelType));
    }

    @Override
    public Map<YearMonth, BigDecimal> reduceCalculatingTotalPrice(Map<YearMonth, List<Consumption>> groupingMap) {
        return groupingMap.entrySet().stream().collect(toMap(Map.Entry::getKey, listEntry -> listEntry.getValue().stream().map(Consumption::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    @Override
    public Map<YearMonth, Map<String, List<Consumption>>> groupByMonthAndFuelType(List<Consumption> consumptions) {
        return consumptions.stream().collect(groupingBy(item -> YearMonth.from(item.getDate()), groupingBy(Consumption::getFuelType)));
    }
}
