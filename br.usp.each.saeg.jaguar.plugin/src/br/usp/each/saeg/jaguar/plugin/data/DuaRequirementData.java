package br.usp.each.saeg.jaguar.plugin.data;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

public class DuaRequirementData extends RequirementData{
	
	private int def;
	private int use;
	private int target;
	private String var;
	private Boolean covered;
	
	@Override
	public Type getType() {
		return Type.DUA;
	}
	
	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Boolean getCovered() {
		return covered;
	}

	public void setCovered(Boolean covered) {
		this.covered = covered;
	}

	//@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((covered == null) ? 0 : covered.hashCode());
		result = prime * result + def;
		result = prime * result + target;
		result = prime * result + use;
		result = prime * result + ((var == null) ? 0 : var.hashCode());
		return result;
	}

	//@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DuaRequirementData)) {
			return false;
		}
		DuaRequirementData other = (DuaRequirementData) obj;
		if (covered == null) {
			if (other.covered != null) {
				return false;
			}
		} else if (!covered.equals(other.covered)) {
			return false;
		}
		if (def != other.def) {
			return false;
		}
		if (target != other.target) {
			return false;
		}
		if (use != other.use) {
			return false;
		}
		if (var == null) {
			if (other.var != null) {
				return false;
			}
		} else if (!var.equals(other.var)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Dua [name=" + getName() + ", var=" + var + ", def=" + def
				+ ", use=" + use + ", line=" + getLine() + ", score=" + getScore() + "]";
	}
	
}
