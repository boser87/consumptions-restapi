package com.stefano.learning.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsDTO {

    Map<Object, BigDecimal> consumptionsTotalAmountByMonth;
    Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeByMonth;

    public StatisticsDTO(Map<Object, BigDecimal> consumptionsTotalAmountByMonth, Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsByFuelTypeByMonth = consumptionsByFuelTypeByMonth;
    }

    public StatisticsDTO(Map<Object, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsByFuelTypeByMonth = new HashMap<>();
    }

    public Map<Object, BigDecimal> getConsumptionsTotalAmountByMonth() {
        return consumptionsTotalAmountByMonth;
    }

    public void setConsumptionsTotalAmountByMonth(Map<Object, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
    }

    public Map<Object, List<ConsumptionByFuelTypeDTO>> getConsumptionsByFuelTypeByMonth() {
        return consumptionsByFuelTypeByMonth;
    }

    public void setConsumptionsByFuelTypeByMonth(Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeByMonth) {
        this.consumptionsByFuelTypeByMonth = consumptionsByFuelTypeByMonth;
    }
}
