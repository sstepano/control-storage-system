package org.code_studio.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentDate implements SystemComponent {

    private LocalDate currentDate;

    public CurrentDate() {
        this.currentDate = LocalDate.now();
    }

    @Override
    public String getId() {
        return currentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    @Override
    public String toString() {
        return currentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }
}
