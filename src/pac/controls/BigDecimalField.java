/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.controls;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

/**
 *
 * @author GvozdenSSD
 */
public class BigDecimalField extends Control{
    
    /**
	 * Default constructor. Returns a {@link BigDecimalField} with no number,
	 * minValue and maxValue set, but stepwidth 1 and default
	 * {@link NumberFormat}.
	 */
	public BigDecimalField() {
		super();
		setStyle(null);
		getStyleClass().add("big-decimal-field");
		number = new SimpleObjectProperty<BigDecimal>(this, "number");
		stepwidth = new SimpleObjectProperty<BigDecimal>(this, "stepwidth", BigDecimal.ONE);
		maxValue = new SimpleObjectProperty<BigDecimal>(this, "maxValue");
		minValue = new SimpleObjectProperty<BigDecimal>(this, "minValue");
		format = new SimpleObjectProperty<NumberFormat>(this, "format", NumberFormat.getNumberInstance());
		promptText = new SimpleStringProperty(this, "promptText", "");
		setFocusTraversable(false);
	}

	/**
	 * Returns a {@link BigDecimalField} with stepwidth 1 and {@link #number} set to number.
	 * @param number
	 */
	public BigDecimalField(BigDecimal number) {
		this();
		setNumber(number);
	}

	/**
	 * 
	 * @param initialValue
	 * @param stepwidth
	 * @param format
	 */
	public BigDecimalField(BigDecimal initialValue, BigDecimal stepwidth,
			NumberFormat format) {
		this();
		this.number.set(initialValue);
		this.stepwidth.set(stepwidth);
		this.format.set(format);
	}

	/**
	 * @return The text representation of number
	 */
	public String getText() {
		if (number.getValue() != null)
			return getFormat().format(number.getValue());
		else
			return null;
	}

	/**
	 * @param formattedNumber
	 *            representation of number
	 */
	public void setText(String formattedNumber) {
		try {
			Number parsedNumber = getFormat().parse(formattedNumber);
			setNumber(new BigDecimal(parsedNumber.toString()));
		} catch (ParseException ex) {
			Logger.getLogger(BigDecimalField.class.getName()).log(Level.INFO,
					null, ex);
		}
	}

	/**
	 * increments the number by stepwidth
	 */
	public void increment() {
		if (getNumber() != null && getStepwidth() != null) {
			BigDecimal newValue = getNumber().add(getStepwidth());
			if (checkBounds(newValue) == false) {return;}
			setNumber(newValue);
		}
	}

	/**
	 * decrements the number by stepwidth
	 */
	public void decrement() {
		if (getNumber() != null && getStepwidth() != null) {
			BigDecimal newValue = getNumber().subtract(getStepwidth());
			if (checkBounds(newValue) == false) {return;}
			setNumber(newValue);
		}
	}

	final private ObjectProperty<BigDecimal> number;

	/**
	 * @return The BigDecimal number
	 */
	public BigDecimal getNumber() {
		return number.getValue();
	}

	/**
	 * Set the BigDecimal number
	 */
	public void setNumber(BigDecimal value) {
		if (checkBounds(value) == false) {
			String message = MessageFormat.format("number {0} is out of bounds({1}, {2})", value, minValue.get(), maxValue.get());
			throw new IllegalArgumentException(message);
		}
		number.set(value);
	}

	/**
	 * Checks if value is between minValue and maxValue (both including) if set at all.
	 * @param value
	 * @return
	 */
	private boolean checkBounds(BigDecimal value) {
		if (value != null && getMaxValue() != null && value.compareTo(getMaxValue()) > 0) {
			return false;
		}
		if (value != null && getMinValue() != null && value.compareTo(getMinValue()) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * @return The property containing the BigDecimal number
	 */
	public ObjectProperty<BigDecimal> numberProperty() {
		return number;
	}

	/**
	 * stepwidth for inc/dec operation
	 */
	final private ObjectProperty<BigDecimal> stepwidth;

	/**
	 * stepwidth for inc/dec operation
	 */
	public BigDecimal getStepwidth() {
		return stepwidth.getValue();
	}

	/**
	 * stepwidth for inc/dec operation
	 */
	public void setStepwidth(BigDecimal value) {
		stepwidth.set(value);
	}

	/**
	 * stepwidth for inc/dec operation
	 */
	public ObjectProperty<BigDecimal> stepwidthProperty() {
		return stepwidth;
	}

	final private ObjectProperty<NumberFormat> format;

	public NumberFormat getFormat() {
		return format.getValue();
	}

	public final void setFormat(NumberFormat value) {
		format.set(value);
	}

	public ObjectProperty<NumberFormat> formatProperty() {
		return format;
	}

	final private StringProperty promptText;

	public String getPromptText() {
		return promptText.getValue();
	}

	public final void setPromptText(String value) {
		promptText.setValue(value);
	}

	public StringProperty promptTextProperty() {
		return promptText;
	}
	
	final private ObjectProperty<BigDecimal> maxValue;
	public BigDecimal getMaxValue() { return maxValue.getValue(); }
	public void setMaxValue(BigDecimal value) { maxValue.set(value); }
	public ObjectProperty<BigDecimal> maxValueProperty() { return maxValue; }
	
	final private ObjectProperty<BigDecimal> minValue;
	public BigDecimal getMinValue() { return minValue.getValue(); }
	public void setMinValue(BigDecimal value) { minValue.set(value); }
	public ObjectProperty<BigDecimal> minValueProperty() { return minValue; }
	

	@Override
	public String getUserAgentStylesheet() {
		return getClass().getResource(
				"/jfxtras/labs/internal/scene/control/"
						+ getClass().getSimpleName() + ".css").toExternalForm();
	}
    
}
