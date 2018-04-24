package com.stefano.learning.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsDTO {

    Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth;
    Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsByMonthByFuelType;

    public StatisticsDTO(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth, Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsByMonthByFuelType) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsByMonthByFuelType = consumptionsByMonthByFuelType;
    }

    public StatisticsDTO(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsByMonthByFuelType = new HashMap<>();
    }

    public Map<YearMonth, BigDecimal> getConsumptionsTotalAmountByMonth() {
        return consumptionsTotalAmountByMonth;
    }

    public void setConsumptionsTotalAmountByMonth(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
    }

    public Map<YearMonth, List<ConsumptionByFuelTypeDTO>> getConsumptionsByMonthByFuelType() {
        return consumptionsByMonthByFuelType;
    }

    public void setConsumptionsByMonthByFuelType(Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsByMonthByFuelType) {
        this.consumptionsByMonthByFuelType = consumptionsByMonthByFuelType;
    }
}
