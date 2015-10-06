package br.usp.each.saeg.jaguar.codeforest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lineRequirement")
public class LineRequirement extends Requirement {

	@Override
	public Type getType() {
		return Type.LINE;
	}

	@Override
	public String toString() {
		return "LineRequirement [name=" + name + ", number=" + number + ", location=" + location + ", suspiciousValue=" + suspiciousValue
				+ ", cef=" + cef + ", cep=" + cep + ", cnf=" + cnf + ", cnp=" + cnp + ", enabled=" + enabled + "]";
	}
	
}
