package com.stefano.learning.statistics.controller;

import com.stefano.learning.core.controller.AbstractResource;
import com.stefano.learning.statistics.service.StatisticsService;
import com.stefano.learning.statistics.dto.StatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("statistics")
public class StatisticsResource extends AbstractResource {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(params = "driverId")
    public StatisticsDTO getStatistics(@RequestParam(value = "driverId") String driverId) {
        return statisticsService.getStatisticsById(driverId);
    }

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }
}
