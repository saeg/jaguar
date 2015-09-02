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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DuaRequirement)) {
			return false;
		}
		DuaRequirement other = (DuaRequirement) obj;
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
		return "DuaRequirement [def=" + def + ", use=" + use + ", target=" + target + ", var=" + var + ", covered=" + covered + ", name="
				+ name + ", number=" + number + ", location=" + location + ", suspiciousValue=" + suspiciousValue + ", cef=" + cef
				+ ", cep=" + cep + ", cnf=" + cnf + ", cnp=" + cnp + ", enabled=" + enabled + "]";
	}

}
