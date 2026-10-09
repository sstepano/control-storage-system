package org.code_studio.component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.HBox;

public final class CSDateTimePicker extends HBox {

    @FXML CSDatePicker dtDatePicker;
    @FXML CSTextField tfTime;

    private boolean defaultNow;

    /**
     * Time format used in the component
     */
    private static final String TimeFormat = "HH:mm";

    /**
     *
     */
    private static final String DefaultDate = "1900-01-01";

    /**
     * Used if user does not provide full TIME in component
     * Then, on focus lost, component will set up this time
     */
    private static final String DefaultTime = "00:00";

    public CSDateTimePicker() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSDateTimePicker.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {
    }

    /**
     * Returns date part from CSDatePicker component
     * @return
     */
    public LocalDate getDate() {
        return dtDatePicker.getValue() == null
          ? LocalDate.parse(DefaultDate)
          : dtDatePicker.getValue();
    }

    /**
     * Returns time part from CSTextField, which was already inserted in hh:mm format
     * @return
     */
    public LocalTime getTime() {
        if (tfTime.getText().length() == 5) { // hh:mm
            return  LocalTime.parse(tfTime.getText(), DateTimeFormatter.ofPattern(TimeFormat));
        } else return LocalTime.parse(DefaultTime);
    }

    /**
     * Returns LocalDAteTime, by utilizing getDate() and getTime() functions within this class
     * @return
     */
    public LocalDateTime getDateTime() {
        return LocalDateTime.of(getDate(), getTime());
    }

    public boolean getDefaultNow() {
        return defaultNow;
      }
   
      /**
       * Setting values to NOW
       * @param defaultNow
       */
      public void setDefaultNow(boolean defaultNow) {
        this.defaultNow = defaultNow;
        dtDatePicker.setValue(LocalDate.now());
        tfTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)));
      }

} // CLASS END