package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.*;

@XmlRootElement(name = "class")
public class Class extends SuspiciousElement {

	private int close;
	private boolean javaInterface;
	private Collection<Method> methods = new ArrayList<Method>();
	
	@Override
	public Collection<Method> getChildren() {
		return getMethods();
	}

	@XmlElement(name = "method")
	public Collection<Method> getMethods() {
		return methods;
	}

	public void setMethods(final Collection<Method> methods) {
		this.methods = methods;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((methods == null) ? 0 : methods.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Class other = (Class) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (methods == null) {
			if (other.methods != null)
				return false;
		} else if (!methods.equals(other.methods))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Class [methodList=" + methods + ", name=" + name
				+ ", number=" + number + ", location=" + getLocation()
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}
	
	public String getSingleName(){
		return name.substring(name.lastIndexOf('.')+1);
	}
	
	public int getClose() {
        return close;
    }
    public void setClose(int close) {
        this.close = close;
    }
	
	public boolean isJavaInterface() {
        return javaInterface;
    }
    
	public void setJavaInterface(boolean javaInterface) {
        this.javaInterface = javaInterface;
    }
    
    public Method byName(String name) {
        for(Method method : methods){
        	if (StringUtils.equals(name, method.getName())) {
                return method;
            }
        }
        return null;
    }
    
    public void addMethod(Method method) {
        if (method != null) {
            methods.add(method);
        }
    }
    
    @Override
	@XmlAttribute
	public Integer getLocation() {
		return location;
	}
	@Override
	public void setLocation(Integer location) {
		this.location = location;
	}
}
