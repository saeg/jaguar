package br.usp.each.saeg.jaguar.core.model.core.requirement;


public class LineTestRequirement extends AbstractTestRequirement {

	private final Integer lineNumber;

	public LineTestRequirement(String className, Integer lineNumber) {
		super();
		this.lineNumber = lineNumber;
		setClassName(className);
	}

	public Type getType() {
		return Type.LINE;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
		result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
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
		LineTestRequirement other = (LineTestRequirement) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (lineNumber == null) {
			if (other.lineNumber != null)
				return false;
		} else if (!lineNumber.equals(other.lineNumber))
			return false;
		return true;
	}

}
