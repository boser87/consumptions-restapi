package com.stefano.learning.consumptions.service;

import com.stefano.learning.consumptions.dto.ConsumptionBatchDTO;
import com.stefano.learning.consumptions.dto.ConsumptionByMonthDTO;
import com.stefano.learning.consumptions.utils.Between;
import com.stefano.learning.core.domain.Consumption;
import com.stefano.learning.core.dto.ConsumptionDTO;
import com.stefano.learning.repository.ConsumptionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Override
    public ConsumptionDTO save(Consumption consumption) {
        Consumption savedConsumption = consumptionRepository.save(consumption);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedConsumption, ConsumptionDTO.class);
    }

    @Override
    public List<ConsumptionDTO> getAllConsumptions() {
        List<Consumption> consumptions = consumptionRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consumptions, new TypeToken<List<ConsumptionDTO>>() {}.getType());
    }

    @Override
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(int month) {
        Between between = new Between(month).invoke();
        List<Consumption> consumptionsByDateBetween = consumptionRepository.findConsumptionByDateBetween(between.getStart(), between.getEnd());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consumptionsByDateBetween, new TypeToken<List<ConsumptionByMonthDTO>>() {}.getType());
    }

    @Override
    public List<ConsumptionByMonthDTO> getConsumptionsByDriverIdAndByMonth(String driverId, int month) {
        Between between = new Between(month).invoke();
        List<Consumption> consumptionByDriverIdAndDateBetween = consumptionRepository.findConsumptionByDriverIdAndDateBetween(driverId, between.getStart(), between.getEnd());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consumptionByDriverIdAndDateBetween, new TypeToken<List<ConsumptionByMonthDTO>>() {}.getType());
    }

    @Override
    @Transactional
    public List<ConsumptionBatchDTO> saveBatch(List<Consumption> consumptions) {
        List<Consumption> savedConsumptions = consumptionRepository.saveAll(consumptions);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedConsumptions, new TypeToken<List<ConsumptionBatchDTO>>() {}.getType());
    }

    @Override
    public Optional<ConsumptionDTO> findById(Long id) {
        ConsumptionDTO consumptionDTO = null;
        Optional<Consumption> consumption = consumptionRepository.findById(id);
        if(consumption.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            consumptionDTO = modelMapper.map(consumption.get(), ConsumptionDTO.class);
        }

        return Optional.ofNullable(consumptionDTO);
    }
}
