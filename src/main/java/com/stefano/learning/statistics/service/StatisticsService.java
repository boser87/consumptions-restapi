package com.stefano.learning.statistics.service;

import com.stefano.learning.statistics.dto.StatisticsDTO;

public interface StatisticsService {
    StatisticsDTO getStatisticsById(String driverId);

    StatisticsDTO getStatistics();
}
