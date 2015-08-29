package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FaultClassification")
public class FlatFaultClassification {

	private String project;
	private String heuristic;
	private Requirement.Type requirementType;
	private Long timeSpent;
	private Collection<Requirement> requirements = new ArrayList<Requirement>();

	@XmlAttribute
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@XmlElement(name = "requirements")
	public Collection<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Collection<Requirement> requirements) {
		this.requirements = requirements;
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
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
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
		FlatFaultClassification other = (FlatFaultClassification) obj;
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
		if (requirements == null) {
			if (other.requirements != null)
				return false;
		} else if (!requirements.equals(other.requirements))
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
		return "FlatFaultClassification [project=" + project + ", heuristic=" + heuristic + ", requirementType=" + requirementType
				+ ", timeSpent=" + timeSpent + ", requirements=" + requirements + "]";
	}

}
