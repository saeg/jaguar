package br.usp.each.saeg.jaguar.core.model.sfl;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-criteria")
public class TestCriteria {

	private String heuristicType;
	private Requirement.Type requirementType;
	private Long timeSpent;
	private Collection<Requirement> requirements;

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

	@XmlElement(name = "requirements")
	public Collection<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Collection<Requirement> requirements) {
		this.requirements = requirements;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
		result = prime * result
				+ ((heuristicType == null) ? 0 : heuristicType.hashCode());
		result = prime * result
				+ ((requirementType == null) ? 0 : requirementType.hashCode());
		result = prime * result
				+ ((timeSpent == null) ? 0 : timeSpent.hashCode());
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
		if (requirements == null) {
			if (other.requirements != null)
				return false;
		} else if (!requirements.equals(other.requirements))
			return false;
		if (heuristicType == null) {
			if (other.heuristicType != null)
				return false;
		} else if (!heuristicType.equals(other.heuristicType))
			return false;
		if (requirementType != other.requirementType)
			return false;
		if (timeSpent == null) {
			if (other.timeSpent != null)
				return false;
		} else if (!timeSpent.equals(other.timeSpent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestCriteria [heuristicType=" + heuristicType
				+ ", requirementType=" + requirementType + ", timeSpent="
				+ timeSpent + ", requirements=" + requirements + "]";
	}

}
