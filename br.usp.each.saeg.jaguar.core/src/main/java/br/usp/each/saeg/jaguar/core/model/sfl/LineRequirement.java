package br.usp.each.saeg.jaguar.core.model.sfl;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lineRequirement")
public class LineRequirement extends Requirement {

	@Override
	public Type getType() {
		return Type.LINE;
	}

}
