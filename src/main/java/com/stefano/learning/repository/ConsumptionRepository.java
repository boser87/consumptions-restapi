package com.stefano.learning.repository;

import com.stefano.learning.domain.Consumption;
import org.springframework.data.repository.RepositoryDefinition;

import java.time.LocalDate;
import java.util.List;

public interface ConsumptionRepository {
    Consumption save(Consumption consumption);

    List<Consumption> findAll();

    List<Consumption> findConsumptionByDriverId(String driverId);

    List<Consumption> findConsumptionByDateBetween(LocalDate start, LocalDate end);

    List<Consumption> findConsumptionByDriverIdAndDateBetween(String driverId, LocalDate start, LocalDate end);

}
