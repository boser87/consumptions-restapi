package com.stefano.learning.repository;

import com.stefano.learning.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepositoryJPA extends JpaRepository<Consumption, Long> {

    @Query("SELECT c.dateMonthAsInt, SUM(c.price) FROM Consumption c GROUP BY c.dateMonthAsInt")
    List<Object[]> findTotalAmountOfMoneySpentGroupedByMonth();

    @Query("SELECT c.dateMonthAsInt, SUM(c.price) FROM Consumption c WHERE c.driverId = :driverId GROUP BY c.dateMonthAsInt")
    List<Object[]> findTotalAmountOfMoneySpentGroupedByMonthByDriverId(@Param("driverId") String driverId);

    @Query("SELECT c.fuelType, c.volume, c.date, c.price, c.driverId FROM Consumption c WHERE c.dateMonthAsInt = :month")
    List<Object[]> findConsumptionsByMonth(int month);
}
