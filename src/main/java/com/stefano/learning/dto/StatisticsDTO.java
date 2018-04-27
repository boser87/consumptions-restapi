package com.stefano.learning.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsDTO {

    Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth;
    Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonth;

    public StatisticsDTO(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth, Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsStatisticsByMonth = consumptionsStatisticsByMonth;
    }

    public StatisticsDTO(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsStatisticsByMonth = new HashMap<>();
    }

    public Map<YearMonth, BigDecimal> getConsumptionsTotalAmountByMonth() {
        return consumptionsTotalAmountByMonth;
    }

    public void setConsumptionsTotalAmountByMonth(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
    }

    public Map<YearMonth, List<ConsumptionByFuelTypeDTO>> getConsumptionsStatisticsByMonth() {
        return consumptionsStatisticsByMonth;
    }

    public void setConsumptionsStatisticsByMonth(Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonth) {
        this.consumptionsStatisticsByMonth = consumptionsStatisticsByMonth;
    }
}
