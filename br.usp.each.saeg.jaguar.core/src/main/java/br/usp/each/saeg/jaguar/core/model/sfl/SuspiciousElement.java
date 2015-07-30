package br.usp.each.saeg.jaguar.core.model.sfl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class SuspiciousElement implements
		Comparable<SuspiciousElement> {

	protected String className;
	protected Integer number;
	protected Double suspiciousValue = 0.0;

	/**
	 * Return the name. Package name, simple class name, method signature or
	 * line number.
	 */
	@XmlAttribute
	public String getClassName() {
		return className;
	}

	/**
	 * Set the complete class name.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Return the total amount of requirements within this element which has the
	 * maximum suspicious value
	 */
	@XmlAttribute
	public Integer getNumber() {
		return number;
	}

	/**
	 * Set the suspicious value of the element. For package, class or method
	 * represent its children's maximum suspicious value.
	 */
	public void setSuspiciousValue(Double suspiciousValue) {
		this.suspiciousValue = suspiciousValue;
	}

	/**
	 * If the the new value is greater than the old one, update the value and
	 * quantity. If the new value is equal to the old one, update only the
	 * quantity (sum the new and old value). Otherwise, do nothing.
	 * 
	 * @param value
	 *            suspicious value
	 * @param quantity
	 *            quantity of elements with this suspicious value
	 */
	public void updateSupicousness(Double value, Integer quantity) {
		if (quantity == 0) {
			quantity = 1;
		}

		if (value > this.suspiciousValue) {
			this.suspiciousValue = value;
			this.number = quantity;
		} else if (value.equals(this.suspiciousValue)) {
			this.number += quantity;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result
				+ ((suspiciousValue == null) ? 0 : suspiciousValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuspiciousElement other = (SuspiciousElement) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (suspiciousValue == null) {
			if (other.suspiciousValue != null)
				return false;
		} else if (!suspiciousValue.equals(other.suspiciousValue))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SuspiciousElement [className=" + className + ", number="
				+ number + ", suspiciousValue=" + suspiciousValue + "]";
	}

	public int compareTo(SuspiciousElement other) {
		if (this.suspiciousValue > other.suspiciousValue) {
			return -1;
		} else if (this.suspiciousValue < other.suspiciousValue) {
			return 1;
		}
		return 0;
	}

}
