package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import com.stefano.learning.utils.Between;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        List<Consumption> consumptionsByMonth = consumptionRepository.findConsumptionByDateBetween(between.getStart(), between.getEnd());
        return buildConsumptionsByMonthDTO(consumptionsByMonth);
    }

    @Override
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(String driverId, int month) {
        Between between = new Between(month).invoke();

        List<Consumption> consumptionsByMonthAndByDriverId = consumptionRepository.findConsumptionByDriverIdAndDateBetween(driverId, between.getStart(), between.getEnd());
        return buildConsumptionsByMonthDTO(consumptionsByMonthAndByDriverId);
    }

    @Override
    @Transactional
    public List<Consumption> saveBatch(List<Consumption> consumptions) {
        return consumptionRepository.saveAll(consumptions);
    }

    // TODO: this can be moved to external class or can use modelmapper
    private List<ConsumptionByMonthDTO> buildConsumptionsByMonthDTO(List<Consumption> consumptions) {
        List<ConsumptionByMonthDTO> consumptionsByMonthDTO = new ArrayList<>();
        for(Consumption consumption : consumptions) {
            ConsumptionByMonthDTO consumptionByMonthDTO = new ConsumptionByMonthDTO(consumption.getFuelType(), consumption.getVolume(), consumption.getDate(), consumption.getPrice(), consumption.getDriverId());
            consumptionsByMonthDTO.add(consumptionByMonthDTO);
        }
        return consumptionsByMonthDTO;
    }
}
