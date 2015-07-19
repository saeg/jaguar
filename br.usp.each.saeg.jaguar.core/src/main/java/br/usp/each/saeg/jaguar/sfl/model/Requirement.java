package br.usp.each.saeg.jaguar.sfl.model;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class Requirement extends SuspiciousElement {
	
	
	public enum Type {
		LINE, DUA;
	}

	@XmlAttribute
	public abstract Type getType();
	
	@XmlAttribute
	private String className;
	
	@Override
	public String toString() {
		return "Requirement [name=" + className + ", number=" + number
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}

}
