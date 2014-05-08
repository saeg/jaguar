package br.usp.each.saeg.jaguar.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class Class extends SuspiciousElement {

	private List<Method> methodList;

	@XmlElement(name = "method")
	public List<Method> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<Method> methodList) {
		this.methodList = methodList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((methodList == null) ? 0 : methodList.hashCode());
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
		Class other = (Class) obj;
		if (methodList == null) {
			if (other.methodList != null)
				return false;
		} else if (!methodList.equals(other.methodList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Class [methodList=" + methodList + ", name=" + name
				+ ", number=" + number + ", location=" + location
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}

}
