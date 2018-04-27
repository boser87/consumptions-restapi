package com.stefano.learning.repository.impl;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionJPARepository implements ConsumptionRepository {

    private final ImportedConsumptionJPARepository impl;

    @PersistenceContext
    EntityManager entityManager;

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
        final List<Consumption> savedConsumptions = new ArrayList<>(consumptions.size());
        int batchSize = 20;

        int i = 0;
        for(Consumption consumption : consumptions) {
            if(consumption.getId() == null) {
                entityManager.persist(consumption);
            } else {
                consumption = entityManager.merge(consumption);
            }

            savedConsumptions.add(consumption);
            i++;

            if(i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        return savedConsumptions;
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
}
