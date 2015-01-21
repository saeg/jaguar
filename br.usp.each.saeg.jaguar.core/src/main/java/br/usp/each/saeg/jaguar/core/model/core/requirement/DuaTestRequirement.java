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

	
}
