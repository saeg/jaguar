package br.usp.each.saeg.jaguar.core.model.sfl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "duaRequirement")
public class DuaRequirement extends Requirement {

	private int def;
	private int use;
	private int target;
	private String var;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + def;
		result = prime * result + target;
		result = prime * result + use;
		result = prime * result + ((var == null) ? 0 : var.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DuaRequirement other = (DuaRequirement) obj;
		if (def != other.def)
			return false;
		if (target != other.target)
			return false;
		if (use != other.use)
			return false;
		if (var == null) {
			if (other.var != null)
				return false;
		} else if (!var.equals(other.var))
			return false;
		return true;
	}

}
