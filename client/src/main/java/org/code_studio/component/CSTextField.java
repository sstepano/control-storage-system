package org.code_studio.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.robot.Robot;

/**
 * 
 * @author gkljajic
 *
 */
public class CSTextField extends TextField {

    public enum VALIDATION_TYPE {
          NONE // default, doesn't need a special formatter
        , TEXT_ONLY
        , TEXT_AND_SPACE // for example first name and last name
        , NUMBER_ONLY
        , MONEY
          // , DATE // We control date in complete different UI control - CSDatePicker
        , TIME
        , ALPHANUMERIC
        , EMAIL // TODO: not implemented
        , PHONE // TODO: not implemented
        , POSITIVE_NUMBER
    }

    private VALIDATION_TYPE validationType;
    
    /*
     * If true, when user presses <KEY_ENTER> while textfield is focused, it will move focus to the NEXT field that can have focus within the scene.
     */
    private boolean focusTraversalWithEnterKey = false;

    /*
     * Used if validation is TIME, to populate textfield with current time in HH:mm
     * format
     * IMPORTANT: We must set this BEFORE setting the validation type, otherwise,
     * time will not be populated
     */
    private boolean defaultNow;

    /**
     * Time format used in the component
     */
    private static final String TimeFormat = "HH:mm";

    /**
     * Used if user does not provide full TIME in component
     * Then, on focus lost, component will set up this time
     */
    private static final String DefaultTime = "00:00";

    /**
     * Interface for calling onFocusChanged
     */
    @FunctionalInterface
    public interface CSTextFieldFocusChangedListener {
        public abstract void onFocusChanged(Boolean oldValue, Boolean newValue);
    }

    /**
     * Interface for calling onTextChanged
     * when the text in the control has changed value
     */
    @FunctionalInterface
    public interface CSTextFieldTextChangedListener {
        public abstract void onTextChanged(String oldValue, String newValue);
    }

    public CSTextField() {
    }

    /***************************************
     * VALIDATION OF USER INPUT
     **************************************/
    UnaryOperator<TextFormatter.Change> textOnlyValidationFormatter = change -> {
        if (change.getText().matches("[a-zA-Z]+") || change.isDeleted()) {
            return change; // if change is a number
        } else {
            change.setText(""); // else make no change
            change.setRange( // don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart());
            return change;
        }
    };
    
    UnaryOperator<TextFormatter.Change> textAndSpaceValidationFormatter = change -> {
        if (change.getText().matches("[a-zA-Z\\s]") || change.isDeleted()) {
            return change; // if change is a number
        } else {
            change.setText(""); // else make no change
            change.setRange( // don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart());
            return change;
        }
    };

    UnaryOperator<TextFormatter.Change> numberOnlyValidationFormatter = change -> {
        if (change.getText().matches("\\d+")) {
            return change;
        } else {
            change.setText(""); // else make no change
            return change;
        }
    };

    UnaryOperator<TextFormatter.Change> moneyValidationFormatter = change -> {
        String formattedChange = null;
        BigDecimal bdValue = null;

        DecimalFormatSymbols serbianSymbols = new DecimalFormatSymbols(Locale.getDefault());
        serbianSymbols.setDecimalSeparator('.');
        serbianSymbols.setGroupingSeparator(',');

        DecimalFormat formatter = new DecimalFormat("###,##0.00", serbianSymbols);
        formatter.setGroupingUsed(true);

        if (change.getText().matches("(-{1})?(\\d+)(\\.{1}\\d{1,4})?")) {
            bdValue = new BigDecimal(change.getControlNewText().replace(",", ""));
            bdValue.setScale(2, RoundingMode.HALF_UP); // uvek gledamo dve decimale
            formattedChange = formatter.format(bdValue);
            change.setRange(0, change.getControlText().length());
            change.setText(formattedChange);

            // if user is not changing decimal places numbers, then do this. Else ... TODO
            if (change.getControlNewText().indexOf(".") >= change.getAnchor()) {
                change.setAnchor(change.getControlNewText().indexOf("."));
                change.selectRange(change.getControlNewText().indexOf("."), change.getControlNewText().indexOf("."));
            }
            //

        } else {
            change.setText(""); // else make no change
        }

        if (change.getControlNewText().isBlank()) {
            change.setText("0.00");
        }
        return change;
    };

    UnaryOperator<TextFormatter.Change> timeValidationFormatter = change -> {
        switch (change.getCaretPosition()) {
            case 0:
                // default postavljanje cele vrednosti iz baze
                break;
            case 1:
                if (!change.getText().matches("[0-2]"))
                    change.setText("");
                break;
            case 2:
                if (change.getControlText().equals("2") && change.getText().matches("[4-9]")) {
                    change.setText("");
                } else {
                    if (!change.isDeleted() && change.isContentChange()) { // if user did not just select all the text, but actually changed content
                        change.setText(change.getText() + ":");
                        change.setAnchor(3);
                        change.selectRange(change.getControlNewText().length(), change.getControlNewText().length());
                    }
                }
                break;
            case 3:
                if (change.getText().matches("[:\\.]")) {
                    change.setText(":");
                } else {
                    change.setText("");
                }
                break;
            case 4:
                if (!change.getText().matches("[0-5]"))
                    change.setText("");
                break;
            case 5:
                if (!change.getText().matches("[0-9]"))
                    change.setText("");
                break;
            default:
                change.setText("");
                return change;
        }
        return change;
    };

    UnaryOperator<TextFormatter.Change> alphanumericValidationFormatter = change -> {
        if (change.getText().matches("[a-zA-Z0-9]+") || change.isDeleted()) {
            return change; // if change is a number and text
        } else {
            change.setText(""); // else make no change
            change.setRange( // don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart());
            return change;
        }
    };

    UnaryOperator<TextFormatter.Change> positiveNumericValidationFormatter = change -> {
        switch (change.getCaretPosition()) {
            case 0:
                break;
            case 1:
                if (!change.getText().matches("[1-9]"))
                    change.setText("");
                break;
            default:
                if (!change.getText().matches("[0-9]"))
                    change.setText("");
                break;
        }
        return change;
    };

    public VALIDATION_TYPE getValidationType() {
        return validationType;
    }

    public void setValidationType(VALIDATION_TYPE validationType) {
        this.validationType = validationType;

        switch (validationType) {
            case TEXT_ONLY:
                this.setTextFormatter(new TextFormatter<String>(textOnlyValidationFormatter));
                break;
            case TEXT_AND_SPACE:
                this.setTextFormatter(new TextFormatter<String>(textAndSpaceValidationFormatter));
                break;
            case NUMBER_ONLY:
                this.setTextFormatter(new TextFormatter<String>(numberOnlyValidationFormatter));
                this.setAlignment(Pos.CENTER_RIGHT); // desno alignovani brojevi
                break;
            case MONEY:
                this.setTextFormatter(new TextFormatter<Double>(moneyValidationFormatter));
                this.setAlignment(Pos.CENTER_RIGHT); // desno alignovani iznosi
                break;
            case TIME:
                if (this.defaultNow) {
                    this.setText(LocalTime.now().format(DateTimeFormatter.ofPattern(TimeFormat)));
                }
                this.setTextFormatter(new TextFormatter<String>(timeValidationFormatter));

                focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                        /**
                         * if user does not add full time in format HH:MM, we set it to 00:00
                         * But we allow him to delete values, leaving field completely blank
                         */
                        if (getText().length() < 5) {
                            setText(DefaultTime);
                        }
                    }
                });
                break;
            case ALPHANUMERIC:
                this.setTextFormatter(new TextFormatter<String>(alphanumericValidationFormatter));
                break;
            case POSITIVE_NUMBER:
                this.setTextFormatter(new TextFormatter<String>(positiveNumericValidationFormatter));
                this.setAlignment(Pos.CENTER_RIGHT);
                break;
            default:
                this.validationType = VALIDATION_TYPE.NONE;
                break;
        }
    }

    public String getTextUnformatted() {
        return this.getText().replace(",", "");
    }

    // Used if validation is TIME
    public Boolean getDefaultNow() {
        return defaultNow;
    }

    public void setDefaultNow(Boolean defaultNow) {
        this.defaultNow = defaultNow;
    }

    /**
     * @return null if string is null or is empty or contains only whitespaces.
     *         Original getText returns null only if string is null.
     *         Replaces this repetitive code: TextField.getText().isEmpty() ? null :
     *         tfTariffNumberUS.getText()
     */
    public String getTextOrNullIfEmpty() {
        return (this.getText() == null || this.getText().isBlank())
                ? null
                : this.getText();
    }

    /**
     * Returns text from text field as Integer
     */
    public Integer getTextAsInteger() {
        return (getText() == null || getText().length() == 0) ? 0 : Integer.parseInt(getTextUnformatted());
    }

    /**
     * Returns text from text field as BigDecimal
     */
    public BigDecimal getTextAsBigDecimal() {
        BigDecimal res = null;
        if (getText() == null || getText().length() == 0) {
            res = BigDecimal.ZERO;
        } else {
            res = new BigDecimal(getTextUnformatted());
        }
        res.setScale(2, RoundingMode.HALF_UP);

        return res;
        // return (getText() == null || getText().length() == 0) ? new BigDecimal(0.00,
        // MathContext.DECIMAL32) : new BigDecimal(getTextUnformatted(),
        // MathContext.DECIMAL32);
    }

    /*
     * Fired when CSTextField gains or loses focus.
     */
    public final void onFocusChanged(CSTextFieldFocusChangedListener listener) {
        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                /**
                 * Call user defined method on focus lost
                 */
                listener.onFocusChanged(oldValue, newValue);
            }
        });
    }

    /*
     * Fired when CSTextField changes text value.
     */
    public final void onTextChanged(CSTextFieldTextChangedListener listener) {
        textProperty().addListener((_, oldValue, newValue) -> { // observable, oldValue, newValue
            listener.onTextChanged(oldValue, newValue);
        });
    }

    /**
     * Fills text value or empty string if provided value is null
     */
    public void setTextOrEmptyString(String textValue) {
        setText(textValue == null ? "" : textValue);
    }

	public boolean isFocusTraversalWithEnterKey() {
		return focusTraversalWithEnterKey;
	}

	public void setFocusTraversalWithEnterKey(boolean focusTraversalWithEnterKey) {
		Robot eventRobot = new Robot();
		if (focusTraversalWithEnterKey) {
		    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
		        if (event.getCode() == KeyCode.ENTER) {
		        	eventRobot.keyPress(KeyCode.TAB);
		        	eventRobot.keyRelease(KeyCode.TAB);
		        }
		    });
		} else {
		    this.removeEventFilter(KeyEvent.KEY_PRESSED, event -> {
		        if (event.getCode() == KeyCode.ENTER) {
		        	eventRobot.keyPress(KeyCode.TAB);
		        	eventRobot.keyRelease(KeyCode.TAB);
		        }
		    });
		}
	}
}