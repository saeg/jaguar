package br.usp.each.saeg.jaguar.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "requirement")
public class Requirement extends SuspiciousElement {

	@Override
	public String toString() {
		return "Requirement [name=" + name + ", number=" + number
				+ ", location=" + location + ", suspiciousValue="
				+ suspiciousValue + "]";
	}

}
