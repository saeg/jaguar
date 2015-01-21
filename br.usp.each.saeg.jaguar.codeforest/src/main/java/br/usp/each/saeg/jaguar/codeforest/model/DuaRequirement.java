package br.usp.each.saeg.jaguar.codeforest.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "duaRequirement")
public class DuaRequirement extends Requirement {

	private int def;
	private int use;
	private int target;
	private String var;
	private Boolean covered;
	
	@Override
	public Type getType() {
		return Type.DUA;
	}

	@XmlAttribute
	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	@XmlAttribute
	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	@XmlAttribute
	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	@XmlAttribute
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@XmlAttribute
	public Boolean getCovered() {
		return covered;
	}

	public void setCovered(Boolean covered) {
		this.covered = covered;
	}
	
	
	
}
