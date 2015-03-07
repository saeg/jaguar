package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-criteria")
public class TestCriteria {

	private String heuristicType;
	private Requirement.Type requirementType;
	private Long timeSpent;
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
	
	/**
	 * @return the timeSpent
	 */
	@XmlElement(name = "time-spent")
	public Long getTimeSpent() {
		return timeSpent;
	}

	/**
	 * @param timeSpent the timeSpent to set
	 */
	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
		result = prime * result
				+ ((timeSpent == null) ? 0 : timeSpent.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TestCriteria)) {
			return false;
		}
		TestCriteria other = (TestCriteria) obj;
		if (heuristicType == null) {
			if (other.heuristicType != null) {
				return false;
			}
		} else if (!heuristicType.equals(other.heuristicType)) {
			return false;
		}
		if (packages == null) {
			if (other.packages != null) {
				return false;
			}
		} else if (!packages.equals(other.packages)) {
			return false;
		}
		if (requirementType != other.requirementType) {
			return false;
		}
		if (timeSpent == null) {
			if (other.timeSpent != null) {
				return false;
			}
		} else if (!timeSpent.equals(other.timeSpent)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCriteria [heuristicType=" + heuristicType
				+ ", requirementType=" + requirementType + ", timeSpent="
				+ timeSpent + ", packages=" + packages + "]";
	}
	
}
