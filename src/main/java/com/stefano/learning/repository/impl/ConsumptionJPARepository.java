package com.stefano.learning.repository;

import com.stefano.learning.domain.Consumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsumptionJPARepository implements ConsumptionRepository {

    private final ImportedConsumptionJPARepository impl;

    @Autowired
    public ConsumptionJPARepository(final ImportedConsumptionJPARepository impl) {
        this.impl = impl;
    }

    @Override
    public Consumption save(Consumption consumption) {
        return null;
    }

    @Override
    public List<Consumption> findAll() {
        return null;
    }

    @Override
    public List<Consumption> findConsumptionByDriverId(String driverId) {
        return null;
    }

    @Override
    public List<Consumption> findConsumptionByDateBetween(LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public List<Consumption> findConsumptionByDriverIdAndDateBetween(String driverId, LocalDate start, LocalDate end) {
        return null;
    }
}
