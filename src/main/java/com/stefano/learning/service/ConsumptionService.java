package com.stefano.learning.service;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByMonthDTO;

import java.util.List;

public interface ConsumptionService {
    Consumption save(Consumption consumption);

    List<ConsumptionByMonthDTO> getConsumptionsByMonth(int month);

    List<Consumption> getAllConsumptions();

    List<ConsumptionByMonthDTO> getConsumptionsByMonth(String driverId, int month);
}
