package com.stefano.learning.consumptions.service;

import com.stefano.learning.consumptions.dto.ConsumptionBatchDTO;
import com.stefano.learning.consumptions.dto.ConsumptionByMonthDTO;
import com.stefano.learning.core.domain.Consumption;
import com.stefano.learning.core.dto.ConsumptionDTO;

import java.util.List;
import java.util.Optional;

public interface ConsumptionService {
    ConsumptionDTO save(Consumption consumption);

    List<ConsumptionDTO> getAllConsumptions();

    List<ConsumptionByMonthDTO> getConsumptionsByMonth(int month);

    List<ConsumptionByMonthDTO> getConsumptionsByDriverIdAndByMonth(String driverId, int month);

    List<ConsumptionBatchDTO> saveBatch(List<Consumption> consumptions);

    Optional<ConsumptionDTO> findById(Long id);
}
