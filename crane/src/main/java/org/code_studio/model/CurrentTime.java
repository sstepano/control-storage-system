package org.code_studio.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime implements SystemComponent {

    private LocalTime currentTime;

    public CurrentTime() {
        this.currentTime = LocalTime.now();
    }

    @Override
    public String getId() {
        return currentTime.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    @Override
    public String toString() {
        return currentTime.format(DateTimeFormatter.ofPattern("HHmmss"));
    }
    
}
