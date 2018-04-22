package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Override
    public Consumption save(Consumption consumption) {
        return consumptionRepository.save(consumption);
    }

    @Override
    public List<Consumption> getAllConsumptions() {
        return consumptionRepository.findAll();
    }

    @Override
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(int month) {
        Between between = new Between(month).invoke();

        List<Consumption> consumptionsByMonth = consumptionRepository.findConsumptionByDateBetween(between.start, between.end);
        return buildConsumptionsByMonthDTO(consumptionsByMonth);
    }

    @Override
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(String driverId, int month) {
        Between between = new Between(month).invoke();

        List<Consumption> consumptionsByMonthAndByDriverId = consumptionRepository.findConsumptionByDriverIdAndDateBetween(driverId, between.start, between.end);
        return buildConsumptionsByMonthDTO(consumptionsByMonthAndByDriverId);
    }

    private List<ConsumptionByMonthDTO> buildConsumptionsByMonthDTO(List<Consumption> consumptions) {
        List<ConsumptionByMonthDTO> consumptionsByMonthDTO = new ArrayList<>();
        for(Consumption consumption : consumptions) {
            ConsumptionByMonthDTO consumptionByMonthDTO = new ConsumptionByMonthDTO(consumption.getFuelType(), consumption.getVolume(), consumption.getDate(), consumption.getPrice(), consumption.getVolume().multiply(consumption.getPrice()), consumption.getDriverId());
            consumptionsByMonthDTO.add(consumptionByMonthDTO);
        }
        return consumptionsByMonthDTO;
    }

    private class Between {
        private int month;
        private LocalDate start;
        private LocalDate end;

        public Between(int month) {
            this.month = month;
        }

        public LocalDate getStart() {
            return start;
        }

        public LocalDate getEnd() {
            return end;
        }

        public Between invoke() {
            // month = month of the current year
            LocalDate currentDateWithMonth = LocalDate.now().withMonth(month);
            start = currentDateWithMonth.withDayOfMonth(1);
            end = currentDateWithMonth.withDayOfMonth(currentDateWithMonth.lengthOfMonth());
            return this;
        }
    }
}
