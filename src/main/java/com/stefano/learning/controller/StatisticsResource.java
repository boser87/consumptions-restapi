package com.stefano.learning.controller;

import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.dto.StatisticsDTO;
import com.stefano.learning.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsResource {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(params = "driverId")
    public StatisticsDTO getStatistics(@RequestParam("driverId") String driverId) {
        return statisticsService.getStatisticsById(driverId);
    }

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }
}
