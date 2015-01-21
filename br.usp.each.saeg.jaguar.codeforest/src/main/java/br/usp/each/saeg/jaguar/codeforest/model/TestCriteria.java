package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-criteria")
public class TestCriteria {

	private String heuristicType;
	private Requirement.Type requirementType;
	private Collection<Package> packages;

	@XmlAttribute(name = "heuristic-type")
	public String getHeuristicType() {
		return heuristicType;
	}

	public void setHeuristicType(String heuristicType) {
		this.heuristicType = heuristicType;
	}

	@XmlAttribute(name = "requirement-type")
	public Requirement.Type getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(Requirement.Type requirementType) {
		this.requirementType = requirementType;
	}

	@XmlElement(name = "package")
	public Collection<Package> getPackages() {
		return packages;
	}

	public void setPackages(Collection<Package> packageSet) {
		this.packages = packageSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((heuristicType == null) ? 0 : heuristicType.hashCode());
		result = prime * result
				+ ((packages == null) ? 0 : packages.hashCode());
		result = prime * result
				+ ((requirementType == null) ? 0 : requirementType.hashCode());
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
		TestCriteria other = (TestCriteria) obj;
		if (heuristicType == null) {
			if (other.heuristicType != null)
				return false;
		} else if (!heuristicType.equals(other.heuristicType))
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		if (requirementType == null) {
			if (other.requirementType != null)
				return false;
		} else if (!requirementType.equals(other.requirementType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestCriteria [heuristicType=" + heuristicType
				+ ", requirementType=" + requirementType + ", packagelist="
				+ packages + "]";
	}
	
}
