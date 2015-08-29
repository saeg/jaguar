package br.usp.each.saeg.jaguar.codeforest.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FaultClassification")
public class HierarchicalFaultClassification {

	private String project;
	private TestCriteria testCriteria;

	@XmlAttribute
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@XmlElement(name = "test-criteria")
	public TestCriteria getTestCriteria() {
		return testCriteria;
	}

	public void setTestCriteria(TestCriteria testCriteria) {
		this.testCriteria = testCriteria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result
				+ ((testCriteria == null) ? 0 : testCriteria.hashCode());
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
		HierarchicalFaultClassification other = (HierarchicalFaultClassification) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (testCriteria == null) {
			if (other.testCriteria != null)
				return false;
		} else if (!testCriteria.equals(other.testCriteria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HierarchicalFaultClassification [project=" + project + ", testCriteria="
				+ testCriteria + "]";
	}

}
