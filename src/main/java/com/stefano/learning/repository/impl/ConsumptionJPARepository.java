package com.stefano.learning.repository.impl;

import com.stefano.learning.ConsumptionsConfigurations;
import com.stefano.learning.core.domain.Consumption;
import com.stefano.learning.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionJPARepository implements ConsumptionRepository {

    private final ImportedConsumptionJPARepository impl;

    @PersistenceContext
    private
    EntityManager entityManager;

    @Autowired
    private ConsumptionsConfigurations consumptionsConfigurations;

    @Autowired
    public ConsumptionJPARepository(final ImportedConsumptionJPARepository impl) {
        this.impl = impl;
    }

    @Override
    public Consumption save(Consumption consumption) {
        return impl.save(consumption);
    }

    @Override
    public List<Consumption> saveAll(List<Consumption> consumptions) {
        int i = 0;
        for(Consumption consumption : consumptions) {
            if(consumption.getId() == null) {
                entityManager.persist(consumption);
            } else {
                entityManager.merge(consumption);
            }

            i++;

            if(i % consumptionsConfigurations.getBatchSize() == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        return consumptions;
    }

    @Override
    public List<Consumption> findAll() {
        return impl.findAll();
    }

    @Override
    public List<Consumption> findConsumptionByDriverId(String driverId) {
        return impl.findConsumptionByDriverId(driverId);
    }

    @Override
    public List<Consumption> findConsumptionByDateBetween(LocalDate start, LocalDate end) {
        return impl.findConsumptionByDateBetween(start, end);
    }

    @Override
    public List<Consumption> findConsumptionByDriverIdAndDateBetween(String driverId, LocalDate start, LocalDate end) {
        return impl.findConsumptionByDriverIdAndDateBetween(driverId, start, end);
    }

    @Override
    public Optional<Consumption> findById(Long id) {
        return impl.findById(id);
    }
}
