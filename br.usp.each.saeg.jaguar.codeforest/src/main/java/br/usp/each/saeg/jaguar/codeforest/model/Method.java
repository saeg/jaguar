package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class Method extends SuspiciousElement {

	private Integer id;
	private Integer position;
	private Collection<Requirement> requirements = new ArrayList<Requirement>();

	@Override
	public Collection<Requirement> getChildren() {
		return getRequirements();
	}
	
	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@XmlAttribute
	public Double getMethodsusp() {
		return suspiciousValue;
	}

	@XmlElement(name = "requirement")
	public Collection<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Collection<Requirement> requirements) {
		this.requirements = requirements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
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
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (requirements == null) {
			if (other.requirements != null)
				return false;
		} else if (!requirements.equals(other.requirements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", position=" + position + ", requirementList=" + requirements + ", name=" + name
				+ ", number=" + number + ", location=" + location + ", suspiciousValue=" + suspiciousValue + "]";
	}

}
