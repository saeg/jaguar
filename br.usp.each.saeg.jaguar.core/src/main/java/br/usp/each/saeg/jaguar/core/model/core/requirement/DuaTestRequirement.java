package br.usp.each.saeg.jaguar.core.model.core.requirement;



public class DuaTestRequirement extends AbstractTestRequirement {

	private int def;
	private int use;
	private int target;
	private String var;

	public DuaTestRequirement(String className, int def, int use, int target, String var) {
		super();
		this.className = className;
		this.def = def;
		this.use = use;
		this.target = target;
		this.var = var;
	}
	
	public Type getType(){
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DuaTestRequirement other = (DuaTestRequirement) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (var == null) {
			if (other.var != null)
				return false;
		} else if (!var.equals(other.var))
			return false;
		if (def != other.def)
			return false;
		if (use != other.use)
			return false;		
		if (target != other.target)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "DuaTestRequirement [def=" + def + ", use=" + use + ", target="
				+ target + ", var=" + var + "]";
	}

	
}
