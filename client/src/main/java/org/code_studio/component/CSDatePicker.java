package org.code_studio.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.function.UnaryOperator;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

public class CSDatePicker extends DatePicker {

	private boolean defaultToday;

	public CSDatePicker() {

		/*
		 * Disable focus on TAB traverse
		 */
		// this.setFocusTraversable(false);

		StringConverter<LocalDate> converter = new StringConverter<>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			// DateTimeFormatter dateFormatter =
			// DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		this.setConverter(converter);
		this.getEditor().setTextFormatter(new TextFormatter<>(dateValidationFormatter));

		// user ne moze sam da unosi datum, mora da bira iz kontrole
		// ovaj deo ce da obrise ceo datum iz kontrole ako pritisne delete ili backspace
		this.getEditor().setOnKeyReleased(e -> {

			// brisemo ceo tekst, mozda u buducnosti popravim da moze da se brise
			// pojedinacna cifra
			if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
				this.setValue(null);
				this.getEditor().setText(null);
				return;
			}

			switch (this.getEditor().getAnchor()) {
			case 2:
				if (this.getEditor().getText().length() > 3) {
					this.getEditor().selectRange(2, 3);
				}
				this.getEditor().replaceSelection(".");
				break;
			case 5:
				if (this.getEditor().getText().length() > 5) {
					this.getEditor().selectRange(5, 6);
				}
				this.getEditor().replaceSelection(".");
				break;
			case 10:
				if (this.getEditor().getText().length() > 10) {
					this.getEditor().selectRange(10, 11);
				}
				this.getEditor().replaceSelection(".");
				break;
			}
		});
	}

	/*
	 * Converts Date to LocalDate
	 */
	public static LocalDate dateToLocalDate(Date inputDate) {
		return inputDate == null ? null : inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/*
	 * Converts LocalDate to Date
	 */
	public static Date localDateToDate(LocalDate inputDate) {
		return inputDate == null ? null : Date.from(inputDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/*
	 * Converts LocalDateTime to Date
	 */
	public static Date localDateTimeToDate(LocalDateTime inputDatetime) {
		return inputDatetime == null ? null : Date.from(inputDatetime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/*
	 * Gets Date as input, picks time part and converts it to LocalTime
	 */
	public static LocalTime dateToLocalTime(Date inputDate) {
		return inputDate == null ? null : inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}

	// vraca format datuma kakav nam treba u Srbiji
	public static String getFormattedDate(LocalDate inputDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		return inputDate == null ? "" : dateFormatter.format(inputDate);
	}
	/*
	 * public static String getFormattedDatetime(LocalDateTime inputDatetime) {
	 * DateTimeFormatter dateFormatter =
	 * DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM); return inputDate ==
	 * null ? "" : dateFormatter.format(inputDate); }
	 */

	public static String getDateAsStringFromLocalDatetime(LocalDateTime inputDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		return inputDate == null ? "" : inputDate.toLocalDate().format(dateFormatter);
	}

	public static String getTimeAsStringFromLocalDatetime(LocalDateTime inputDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
		return inputDate == null ? "" : inputDate.toLocalTime().format(dateFormatter);
	}

	/**
	 * Shorthand for LocalDateTime.Of(LocalDate, LocalTime) I always forget this, so
	 * I made shorthand :)
	 *
	 * @return
	 */
	public static LocalDateTime getLocalDateTimeFromLocalDateAndLocalTime(LocalDate date, LocalTime time) {
		return LocalDateTime.of(date, time);
	}

	public boolean getDefaultToday() {
		return defaultToday;
	}

	public void setDefaultToday(boolean defaultToday) {
		this.defaultToday = defaultToday;
		this.setValue(LocalDate.now());
	}

	/***************************************
	 * VALIDATION OF USER INPUT We will discard all of it just delete of value is
	 * allowed Regex checks for valid date i.e: 23.02.2023.
	 **************************************/
	UnaryOperator<TextFormatter.Change> discardAllValidationFormatter = change -> {
		if (change.getText().matches("[0-9]{2}\\.[0-3]{1}[0-9]{1}\\.[0-9]{4}\\.")) {
			return change; // if change is a number
		} else {
			change.setText(""); // else make no change
			return change;
		}
	};

	/**
	 *
	 */
	UnaryOperator<TextFormatter.Change> dateValidationFormatter = change -> {
		// Ako je user koristio kontrolu da odabere datum
		if (change.getText().matches("[0-9]{2}\\.[0-3]{1}[0-9]{1}\\.[0-9]{4}\\.")) {
			return change;
		}

		// Ako je rucno ukucavao cifre datuma
		switch (change.getControlAnchor()) {
		case 0:
			if (change.getText().matches("[0-3]")) {
				if (change.getControlText().length() > 0)
					change.setRange(0, 1);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 1:
			if (change.getText().matches("[0-9]")) {
				if (change.getControlText().length() > 1)
					change.setRange(1, 2);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 2:
			/*
			 * if(change.getText().matches("[\\.]")) { return change; } else {
			 * change.setText(""); return change; }
			 */
			return change;
		case 3:
			if (change.getText().matches("[0-1]")) {
				if (change.getControlText().length() > 3)
					change.setRange(3, 4);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 4:
			if (change.getText().matches("[0-9]")) {
				if (change.getControlText().length() > 4)
					change.setRange(4, 5);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 5:
			/*
			 * if(change.getText().matches("[\\.]")) { return change; } else {
			 * change.setText(""); return change; }
			 */
			return change;
		case 6:
			if (change.getText().matches("2")) {
				if (change.getControlText().length() > 6)
					change.setRange(6, 7);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 7:
			if (change.getText().matches("0")) {
				if (change.getControlText().length() > 7)
					change.setRange(7, 8);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 8:
			if (change.getText().matches("[0-3]")) {
				if (change.getControlText().length() > 8)
					change.setRange(8, 9);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 9:
			if (change.getText().matches("[0-9]")) {
				if (change.getControlText().length() > 9)
					change.setRange(9, 10);
				return change;
			} else {
				change.setText("");
				return change;
			}
		case 10:
			/*
			 * if(change.getText().matches("[\\.]")) { return change; } else {
			 * change.setText(""); return change; }
			 */
			return change;
		default:
			change.setText("");
			break;
		}
		return change;

	};

}
