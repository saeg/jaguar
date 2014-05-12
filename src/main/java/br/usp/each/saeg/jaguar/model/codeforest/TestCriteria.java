package br.usp.each.saeg.jaguar.model.codeforest;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-criteria")
public class TestCriteria {

	private String heuristicType;
	private String requirementType;
	private List<Package> packagelist;

	@XmlAttribute(name = "heuristic-type")
	public String getHeuristicType() {
		return heuristicType;
	}

	public void setHeuristicType(String heuristicType) {
		this.heuristicType = heuristicType;
	}

	@XmlAttribute(name = "requirement-type")
	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	@XmlElement(name = "package")
	public List<Package> getPackagelist() {
		return packagelist;
	}

	public void setPackagelist(List<Package> packagelist) {
		this.packagelist = packagelist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((heuristicType == null) ? 0 : heuristicType.hashCode());
		result = prime * result
				+ ((packagelist == null) ? 0 : packagelist.hashCode());
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
		if (packagelist == null) {
			if (other.packagelist != null)
				return false;
		} else if (!packagelist.equals(other.packagelist))
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
				+ packagelist + "]";
	}
	
}
