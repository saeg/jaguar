package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "HierarchicalFaultClassification")
@XmlSeeAlso({DuaRequirement.class,LineRequirement.class})
public class HierarchicalFaultClassification extends FaultClassification {

	private Collection<Package> packages;

	@XmlElement
	public Collection<Package> getPackages() {
		return packages;
	}

	public void setPackages(Collection<Package> packages) {
		this.packages = packages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((packages == null) ? 0 : packages.hashCode());
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
		HierarchicalFaultClassification other = (HierarchicalFaultClassification) obj;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HierarchicalFaultClassification [packages=" + packages + ", project=" + project + ", heuristic=" + heuristic
				+ ", requirementType=" + requirementType + ", timeSpent=" + timeSpent + "]";
	}
	
}
