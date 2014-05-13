package br.usp.each.saeg.jaguar.model.core;

public class TestRequirement implements Comparable<TestRequirement> {

	private final String className;
	private Integer classFirstLine;

	private Integer methodId;
	private Integer methodLine;
	private String methodSignature;
	
	private final Integer lineNumber;
	
	private int cef = 0;
	private int cep = 0;
	private Double suspiciousness; 

	public TestRequirement(String className, Integer lineNumber) {
		super();
		this.className = className;
		this.lineNumber = lineNumber;
	}

	public String getClassName() {
		return className;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}
	
	public Integer getClassFirstLine() {
		return classFirstLine;
	}

	public void setClassFirstLine(Integer classFirstLine) {
		this.classFirstLine = classFirstLine;
	}

	public Integer getMethodId() {
		return methodId;
	}

	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public Integer getMethodLine() {
		return methodLine;
	}

	public void setMethodLine(Integer methodLine) {
		this.methodLine = methodLine;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public void increaseFailed(){
		cef++;
	}
	
	public int getCef() {
		return cef;
	}

	public void increasePassed(){
		cep++;
	}

	public int getCep() {
		return cep;
	}
	
	public double getSuspiciousness() {
		return suspiciousness;
	}

	public void setSuspiciousness(double suspiciousness) {
		this.suspiciousness = suspiciousness;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((lineNumber == null) ? 0 : lineNumber.hashCode());
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
		TestRequirement other = (TestRequirement) obj;
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

	public int compareTo(TestRequirement o) {
		return this.suspiciousness.compareTo(o.getSuspiciousness());
	}

}
