package br.usp.each.saeg.jaguar.codeforest.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FaultClassification")
public abstract class FaultClassification {

	protected String project;
	protected String heuristic;
	protected Requirement.Type requirementType;
	protected Long timeSpent;

	@XmlAttribute
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@XmlAttribute
	public String getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}

	@XmlAttribute
	public Requirement.Type getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(Requirement.Type requirementType) {
		this.requirementType = requirementType;
	}

	@XmlAttribute
	public Long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((heuristic == null) ? 0 : heuristic.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((requirementType == null) ? 0 : requirementType.hashCode());
		result = prime * result + ((timeSpent == null) ? 0 : timeSpent.hashCode());
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
		FaultClassification other = (FaultClassification) obj;
		if (heuristic == null) {
			if (other.heuristic != null)
				return false;
		} else if (!heuristic.equals(other.heuristic))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
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
		return "FaultClassification [project=" + project + ", heuristic=" + heuristic + ", requirementType=" + requirementType
				+ ", timeSpent=" + timeSpent + "]";
	}

}
