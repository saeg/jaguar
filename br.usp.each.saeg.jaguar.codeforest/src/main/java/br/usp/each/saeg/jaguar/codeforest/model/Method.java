package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "class")
@XmlSeeAlso({DuaRequirement.class,LineRequirement.class})
public class Method extends SuspiciousElement {

	private Integer id;
	private Integer position;
	private int close;
	private Collection<Requirement> requirements = new ArrayList<Requirement>();
	private Integer mcpPosition;
	private Double mcpSuspiciousValue = 0.0;
	
	@Override
	public Collection<Requirement> getChildren() {
		return getRequirements();
	}
	
	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@XmlAttribute
	public Double getMethodsusp() {
		return suspiciousValue;
	}

//	@XmlElements({
//	    @XmlElement(name="line", type=LineRequirement.class),
//	    @XmlElement(name="dua", type=DuaRequirement.class)
//	})
	
	
	@XmlElement //TODO there's a difference in the models : new is requirements old is requirement
	public Collection<Requirement> getRequirements() {
		return requirements;
	}
	
	public void setRequirements(Collection<Requirement> requirement) {
		this.requirements = requirement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
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
		Method other = (Method) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (requirements == null) {
			if (other.requirements != null)
				return false;
		} else if (!requirements.equals(other.requirements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", position=" + position + ", requirementList=" + requirements + ", name=" + name
				+ ", number=" + number + ", location=" + location + ", suspiciousValue=" + suspiciousValue + "]";
	}
	
	public int getClose() {
        return close;
    }
    public void setClose(int close) {
        this.close = close;
    }

	public Integer getMcpPosition() {
		return mcpPosition;
	}

	public void setMcpPosition(Integer mcpPosition) {
		this.mcpPosition = mcpPosition;
	}

	public Double getMcpSuspiciousValue() {
		return mcpSuspiciousValue;
	}

	public void setMcpSuspiciousValue(Double mcpSuspiciousValue) {
		this.mcpSuspiciousValue = mcpSuspiciousValue;
	}
	
	public Requirement byRelativeLoc(Integer arg) {
        List<Requirement> result = new ArrayList<Requirement>();
        for(Requirement req : requirements){
        	if (arg.equals(req.getLocation() - location)) {
                result.add(req);
            }
        }
        return max(result);
    }
	
	public Requirement byAbsoluteLoc(Integer arg) {
        List<Requirement> result = new ArrayList<Requirement>();
        for(Requirement req : requirements){
        	if (arg.equals(req.getLocation())) {
                result.add(req);
            }
        }
        return max(result);
    }
	
	private Requirement max(List<Requirement> result) {
        if (result.isEmpty()) {
            return null;
        }
        Collections.sort(result, new Comparator<Requirement>() {
            @Override
            public int compare(Requirement o1, Requirement o2) {
                return o2.getSuspiciousValue().compareTo(o1.getSuspiciousValue());
            }
        });
        return result.get(0);
    }
	
	public void addRequirements(Requirement requirement) {
        if (requirement != null) {
        	requirements.add(requirement);
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
