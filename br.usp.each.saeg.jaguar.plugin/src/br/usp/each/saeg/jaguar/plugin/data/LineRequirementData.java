package br.usp.each.saeg.jaguar.plugin.data;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

public class LineRequirementData extends RequirementData{

	@Override
	public Type getType() {
		return Type.LINE;
	}
	
	@Override
	public String toString() {
		return "Block [line=" + getLine() + ", score=" + getScore() + ", content= \"" + getValue().trim() + "\"]";
	}

}
