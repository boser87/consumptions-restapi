package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByFuelTypeDTO;
import com.stefano.learning.dto.StatisticsDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import com.stefano.learning.utils.aggregator.ConsumptionsAggregator;
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

        Map<YearMonth, List<Consumption>> consumptionsByYearMonth = consumptionsAggregator.groupByYearMonth(consumptions);
        Map<YearMonth, BigDecimal> consumptionsTotalAmountByMonth = consumptionsAggregator.reduceCalculatingTotalPrice(consumptionsByYearMonth);

        Map<YearMonth, Map<String, List<Consumption>>> consumptionsGroupedByMonthByType = consumptionsAggregator.groupByMonthAndFuelType(consumptions);
        Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeDTOByMonth = createConsumptionsByFuelTypeDTOGroupedByMonth(consumptionsGroupedByMonthByType);

        return new StatisticsDTO(consumptionsTotalAmountByMonth, consumptionsByFuelTypeDTOByMonth);
    }

    // TODO: move this code to DTO constructor or DTO builder
    /**
     * cycles on the map and the nested map to calculate the aggregate values and required by the output DTO
     * @param consumptionsByMonthByType
     * @return
     */
    private Map<YearMonth, List<ConsumptionByFuelTypeDTO>> createConsumptionsByFuelTypeDTOGroupedByMonth(Map<YearMonth, Map<String, List<Consumption>>> consumptionsByMonthByType) {
        Map<YearMonth, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeDTOGroupedByMonth = new HashMap<>();

        for(Map.Entry<YearMonth, Map<String, List<Consumption>>> entryByMonthByType : consumptionsByMonthByType.entrySet()) {

            // consumptions by fuel type for n-th month group
            Map<String, List<Consumption>> consumptionsByFuelType = entryByMonthByType.getValue();

            List<ConsumptionByFuelTypeDTO> consumptionsByFuelTypeDTO = new ArrayList<>();
            for(Map.Entry<String, List<Consumption>> entryByType : consumptionsByFuelType.entrySet()) {
                // consumptions for n-th month and m-th fuel type group
                List<Consumption> consumptions = entryByType.getValue();

                ConsumptionByFuelTypeDTO consumptionByFuelTypeDTO = new ConsumptionByFuelTypeDTO(entryByType.getKey(),
                        consumptionsAggregator.calculateTotalPrice(consumptions),
                        consumptionsAggregator.calculateTotalVolume(consumptions),
                        consumptionsAggregator.count(consumptions));
                consumptionsByFuelTypeDTO.add(consumptionByFuelTypeDTO);
            }

            consumptionsByFuelTypeDTOGroupedByMonth.put(entryByMonthByType.getKey(), consumptionsByFuelTypeDTO);
        }

        return consumptionsByFuelTypeDTOGroupedByMonth;
    }

}
