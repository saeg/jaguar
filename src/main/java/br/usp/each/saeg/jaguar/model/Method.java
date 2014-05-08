package br.usp.each.saeg.jaguar.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class Method extends SuspiciousElement {

	private String id;
	private String position;
	private String methodsusp;
	private List<Requirement> requirementList;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@XmlAttribute
	public String getMethodsusp() {
		return methodsusp;
	}

	public void setMethodsusp(String methodsusp) {
		this.methodsusp = methodsusp;
	}

	@XmlElement(name = "requirement")
	public List<Requirement> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<Requirement> requirementList) {
		this.requirementList = requirementList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((methodsusp == null) ? 0 : methodsusp.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((requirementList == null) ? 0 : requirementList.hashCode());
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
		Method other = (Method) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (methodsusp == null) {
			if (other.methodsusp != null)
				return false;
		} else if (!methodsusp.equals(other.methodsusp))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (requirementList == null) {
			if (other.requirementList != null)
				return false;
		} else if (!requirementList.equals(other.requirementList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", position=" + position + ", methodsusp="
				+ methodsusp + ", requirementList=" + requirementList
				+ ", name=" + name + ", number=" + number + ", location="
				+ location + ", suspiciousValue=" + suspiciousValue + "]";
	}

}
