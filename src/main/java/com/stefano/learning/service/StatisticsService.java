package com.stefano.learning.service;

import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.dto.StatisticsDTO;

import java.util.List;

public interface StatisticsService {
    StatisticsDTO getStatisticsById(String id);
    StatisticsDTO getStatistics();
}
