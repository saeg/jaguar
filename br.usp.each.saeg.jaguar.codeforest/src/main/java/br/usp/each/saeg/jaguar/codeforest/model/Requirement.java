package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "requirement")
public class Requirement extends SuspiciousElement {

	@Override
	public Collection<? extends SuspiciousElement> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "Requirement [name=" + name + ", number=" + number
				+ ", location=" + location + ", suspiciousValue="
				+ suspiciousValue + "]";
	}

}
