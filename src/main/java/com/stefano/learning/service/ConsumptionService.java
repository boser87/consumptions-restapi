package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.dto.ConsumptionsBatchDTO;

import java.util.List;
import java.util.Optional;

public interface ConsumptionService {
    Consumption save(Consumption consumption);

    List<ConsumptionByMonthDTO> getConsumptionsByMonth(int month);

    List<Consumption> getAllConsumptions();

    List<ConsumptionByMonthDTO> getConsumptionsByMonth(String driverId, int month);

    ConsumptionsBatchDTO saveBatch(List<Consumption> consumptions);

    Optional<Consumption> findById(Long id);
}
