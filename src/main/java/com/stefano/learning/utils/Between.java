package com.stefano.learning.utils;

import java.time.LocalDate;

/**
 * Used by service to query repository with date between two months of current years
 */
public class Between {
    private int month;
    private LocalDate start;
    private LocalDate end;

    public Between(int month) {
        this.month = month;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Between invoke() {
        // month = month of the current year
        LocalDate currentDateWithMonth = LocalDate.now().withMonth(month);
        start = currentDateWithMonth.withDayOfMonth(1);
        end = currentDateWithMonth.withDayOfMonth(currentDateWithMonth.lengthOfMonth());
        return this;
    }
}
