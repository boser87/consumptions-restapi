package com.stefano.learning.repository.impl;

import com.stefano.learning.core.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ImportedConsumptionJPARepository extends JpaRepository<Consumption, Long> {
    Consumption save(Consumption consumption);

    List<Consumption> findAll();

    List<Consumption> findConsumptionByDriverId(String driverId);

    List<Consumption> findConsumptionByDateBetween(LocalDate start, LocalDate end);

    List<Consumption> findConsumptionByDriverIdAndDateBetween(String driverId, LocalDate start, LocalDate end);

    Optional<Consumption> findById(Long id);
}
