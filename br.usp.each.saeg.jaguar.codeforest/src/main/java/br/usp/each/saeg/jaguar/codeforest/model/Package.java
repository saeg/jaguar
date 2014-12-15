package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "package")
public class Package extends SuspiciousElement {
	
	private Collection<Class> classes = new ArrayList<Class>();

	@Override
	public Collection<Class> getChildren() {
		return getClasses();
	}
	
	@XmlElement(name = "class")
	public Collection<Class> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Class> classes) {
		this.classes = classes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((classes == null) ? 0 : classes.hashCode());
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
		Package other = (Package) obj;
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Package [classList=" + classes + ", name=" + name
				+ ", number=" + number + ", location=" + location
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}

}
