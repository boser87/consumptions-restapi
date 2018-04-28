package com.stefano.learning.statistics.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class StatisticsDTO {

    private Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth;
    private Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonthDTO;

    public StatisticsDTO(Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth, Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonthDTO) {
        this.consumptionsTotalAmountByMonth = consumptionsTotalAmountByMonth;
        this.consumptionsStatisticsByMonthDTO = consumptionsStatisticsByMonthDTO;
    }

    public Map<YearMonth, BigDecimal> getConsumptionsTotalAmountByMonth() {
        return consumptionsTotalAmountByMonth;
    }

    public Map<YearMonth, List<ConsumptionByFuelTypeDTO>> getConsumptionsStatisticsByMonthDTO() {
        return consumptionsStatisticsByMonthDTO;
    }
}
