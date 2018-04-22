package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByFuelTypeDTO;
import com.stefano.learning.dto.StatisticsDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Override
    public StatisticsDTO getStatistics() {
        List<Consumption> allConsumptions = consumptionRepository.findAll();

        Map<Object, BigDecimal> consumptionsTotalAmountByMonth = calculateTotalPriceOfConsumptionsGroupedByMonth(allConsumptions);

        // groups by month and by fuel type
        Map<Object, Map<String, List<Consumption>>> consumptionsGroupedByMonthByType = groupsByMonthAndFuelType(allConsumptions);

        // cycles on the map and the nested map to calculate the aggregate values and required by the output DTO
        Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeDTOByMonth = createConsumptionsByFuelTypeDTOMap(consumptionsGroupedByMonthByType);

        return new StatisticsDTO(consumptionsTotalAmountByMonth, consumptionsByFuelTypeDTOByMonth);
    }

    private Map<Object, Map<String, List<Consumption>>> groupsByMonthAndFuelType(List<Consumption> allConsumptions) {
        return allConsumptions.stream().collect(groupingBy(item -> YearMonth.from(item.getDate()), groupingBy(Consumption::getFuelType)));
    }

    @Override
    public StatisticsDTO getStatisticsById(String driverId) {
        List<Consumption> consumptionsByDriverId = consumptionRepository.findConsumptionByDriverId(driverId);

        Map<Object, BigDecimal> consumptionsTotalAmountByMonth = calculateTotalPriceOfConsumptionsGroupedByMonth(consumptionsByDriverId);

        // groups by month and by fuel type
        Map<Object, Map<String, List<Consumption>>> consumptionsGroupedByMonthByType = groupsByMonthAndFuelType(consumptionsByDriverId);

        Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeDTOByMonth = createConsumptionsByFuelTypeDTOMap(consumptionsGroupedByMonthByType);

        return new StatisticsDTO(consumptionsTotalAmountByMonth, consumptionsByFuelTypeDTOByMonth);
    }

    private Map<Object, BigDecimal> calculateTotalPriceOfConsumptionsGroupedByMonth(List<Consumption> allConsumptions) {
        return allConsumptions.stream().collect(groupingBy(item -> YearMonth.from(item.getDate()), mapping(Consumption::getPrice, reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    /**
     * cycles on the map and the nested map to calculate the aggregate values and required by the output DTO
     * @param consumptionsByMonthByType
     * @return
     */
    private Map<Object, List<ConsumptionByFuelTypeDTO>> createConsumptionsByFuelTypeDTOMap(Map<Object, Map<String, List<Consumption>>> consumptionsByMonthByType) {
        Map<Object, List<ConsumptionByFuelTypeDTO>> consumptionsByFuelTypeDTOByMonth = new HashMap<>();

        for(Map.Entry<Object, Map<String, List<Consumption>>> entryByMonthByType : consumptionsByMonthByType.entrySet()) {

            // consumptions by fuel type for n-th month group
            Map<String, List<Consumption>> consumptionsByFuelType = entryByMonthByType.getValue();

            List<ConsumptionByFuelTypeDTO> consumptionsByFuelTypeDTO = new ArrayList<>();
            for(Map.Entry<String, List<Consumption>> entryByType : consumptionsByFuelType.entrySet()) {
                // calculate total price of consumptions grouped by m-th fuel type in the n-th month group
                BigDecimal totalPrice = entryByType.getValue().stream().collect(mapping(Consumption::getPrice, reducing(BigDecimal.ZERO, BigDecimal::add)));

                // calculate total volume of consumptions grouped by m-th fuel type in the n-th month group
                BigDecimal totalVolume = entryByType.getValue().stream().collect(mapping(Consumption::getVolume, reducing(BigDecimal.ZERO, BigDecimal::add)));

                // calculate average price of consumptions grouped by m-th fuel type in the n-th month group
                long count = entryByType.getValue().stream().count();
                BigDecimal averagePrice = totalPrice.divide(new BigDecimal(count), 2, RoundingMode.CEILING);

                ConsumptionByFuelTypeDTO consumptionByFuelTypeDTO = new ConsumptionByFuelTypeDTO(entryByType.getKey(), totalVolume, averagePrice, totalPrice);
                consumptionsByFuelTypeDTO.add(consumptionByFuelTypeDTO);
            }

            consumptionsByFuelTypeDTOByMonth.put(entryByMonthByType.getKey(), consumptionsByFuelTypeDTO);
        }

        return consumptionsByFuelTypeDTOByMonth;
    }

}
