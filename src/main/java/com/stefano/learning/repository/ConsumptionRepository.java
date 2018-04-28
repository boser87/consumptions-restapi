package com.stefano.learning.repository;

import com.stefano.learning.core.domain.Consumption;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsumptionRepository {
    Consumption save(Consumption consumption);

    List<Consumption> saveAll(List<Consumption> consumptions);

    List<Consumption> findAll();

    List<Consumption> findConsumptionByDriverId(String driverId);

    List<Consumption> findConsumptionByDateBetween(LocalDate start, LocalDate end);

    List<Consumption> findConsumptionByDriverIdAndDateBetween(String driverId, LocalDate start, LocalDate end);

    Optional<Consumption> findById(Long id);
}
