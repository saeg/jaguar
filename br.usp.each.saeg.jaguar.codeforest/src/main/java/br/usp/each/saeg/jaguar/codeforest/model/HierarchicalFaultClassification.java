package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "HierarchicalFaultClassification")
@XmlSeeAlso({DuaRequirement.class,LineRequirement.class})
public class HierarchicalFaultClassification extends FaultClassification {

	private Collection<Package> packages;
	private Map<String, Package> namePackage = new HashMap<String, Package>();

	@XmlElement(name = "package")
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
	
	public Package byName(String name) {
        if (namePackage.isEmpty()) {
            for (Package pkg : packages) {
                namePackage.put(pkg.getName(), pkg);
            }
        }
        return namePackage.get(name);
    }
	
	/**
	 * Return a 'flat' list of {@link SuspiciousElement}, extracted recursively from a collenction of {@link Package}, 
	 * iterating over each {@link Package}, {@link Class} and {@link Method}.
	 * 
	 * @param packages the collection of {@link Package}
	 * 
	 * @return A 'flat' list of {@link SuspiciousElement}
	 */
	@Override
	public List<SuspiciousElement> getSuspiciousElementList() {
		List<SuspiciousElement> elements = new ArrayList<SuspiciousElement>();
		for (Package currentPackage : packages) {
			for (Class currentClass : currentPackage.getClasses()) {
				for (Method currentMethod : currentClass.getMethods()) {
					for (Requirement requirement : currentMethod.getRequirements()) {
						requirement.setName(currentClass.getName());
						elements.add(requirement);
					}
				}
			}
		}
		return elements;
	}
	
}
