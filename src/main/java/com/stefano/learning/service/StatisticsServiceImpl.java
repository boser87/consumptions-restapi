package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByFuelTypeDTO;
import com.stefano.learning.dto.StatisticsDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import com.stefano.learning.service.aggregator.ConsumptionsAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private ConsumptionsAggregator consumptionsAggregator;

    @Override
    public StatisticsDTO getStatisticsById(String driverId) {
        List<Consumption> consumptions = driverId == null || driverId.isEmpty() ? consumptionRepository.findAll() : consumptionRepository.findConsumptionByDriverId(driverId);

        Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth = createConsumptionsTotalAmountByMonth(consumptions);

        Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonth = createConsumptionsStatisticsByMonth(consumptions);

        return new StatisticsDTO(consumptionsTotalAmountByMonth, consumptionsStatisticsByMonth);
    }

    @Override
    public StatisticsDTO getStatistics() {
        return getStatisticsById(null);
    }

    private Map<YearMonth, BigDecimal> createConsumptionsTotalAmountByMonth(List<Consumption> consumptions) {
        Map<YearMonth, List<Consumption>> consumptionsByYearMonth = consumptionsAggregator.groupByYearMonth(consumptions);
        return consumptionsAggregator.reduceCalculatingTotalPrice(consumptionsByYearMonth);
    }

    private Map<YearMonth, List<ConsumptionByFuelTypeDTO>> createConsumptionsStatisticsByMonth(List<Consumption> consumptions) {
        Map<YearMonth, Map<String, List<Consumption>>> consumptionsGroupedByMonthByType = consumptionsAggregator.groupByMonthAndFuelType(consumptions);
        Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsStatisticsByMonth = new HashMap<>();

        for(Map.Entry<YearMonth, Map<String, List<Consumption>>> entryByMonthByType : consumptionsGroupedByMonthByType.entrySet()) {

            // consumptions by fuel type for n-th month group
            Map<String, List<Consumption>> consumptionsByFuelType = entryByMonthByType.getValue();

            List<ConsumptionByFuelTypeDTO> consumptionsByFuelTypeDTO = new ArrayList<>();
            for(Map.Entry<String, List<Consumption>> entryByType : consumptionsByFuelType.entrySet()) {
                // consumptions for n-th month and m-th fuel type group
                List<Consumption> consumptionsByMonthByType = entryByType.getValue();

                ConsumptionByFuelTypeDTO consumptionByFuelTypeDTO = new ConsumptionByFuelTypeDTO(entryByType.getKey(),
                        consumptionsAggregator.calculateTotalPrice(consumptionsByMonthByType),
                        consumptionsAggregator.calculateTotalVolume(consumptionsByMonthByType),
                        consumptionsAggregator.count(consumptionsByMonthByType));
                consumptionsByFuelTypeDTO.add(consumptionByFuelTypeDTO);
            }

            consumptionsStatisticsByMonth.put(entryByMonthByType.getKey(), consumptionsByFuelTypeDTO);
        }

        return consumptionsStatisticsByMonth;
    }

}
