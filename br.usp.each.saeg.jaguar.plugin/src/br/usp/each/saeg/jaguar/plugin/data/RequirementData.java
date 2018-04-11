package br.usp.each.saeg.jaguar.plugin.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Position;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;

public abstract class RequirementData extends AbstractData implements Comparable<RequirementData> {

	private String name;
    private int line;
    private Position position;
    private IMarker marker;
    private IResource resource;
    private String logLine;
    private boolean enabled = true;
   
    public abstract Type getType();
	
    public String getName() {
		return name;
	}
    
	public void setName(String name) {
		this.name = name;
	}
	
	public Position getPosition() {
        return position;
    }
	
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public int getLine() {
        return line;
    }
    public void setLine(int line) {
        this.line = line;
    }

    public IMarker getMarker() {
        if (marker == null) {
            marker = CodeMarkerFactory.findMarker(resource, score, line);
        }
        
        return marker;
    }
    
    public IResource getResource() {
    	return this.resource;
    }

    public void setResource(IResource resource) {
        this.resource = resource;
    }

    public String getLogLine() {
        return logLine;
    }
    
    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

    public boolean isScoreBetween(float min, float max) {
        return Float.compare(score, max) <= 0 && Float.compare(score, min) >= 0;
    }

    @Override
    public int compareTo(RequirementData o) {
        return new CompareToBuilder()
        .append(o.getScore(), score)
        .toComparison();
    }

    public static List<RequirementData> covered(List<RequirementData> arg) {
        List<RequirementData> result = new ArrayList<RequirementData>();
        for (RequirementData data : arg) {
            if (data.getScore() < 0) {
                continue;
            }
            result.add(data);
        }

        return result;
    }
    
    public void enable() {
        if (enabled) {
            return;
        }
        enabled = true;
    }

    public void disable() {
        if (!enabled) {
            return;
        }
        enabled = false;
    }
    
    public boolean isEnabled(){
    	return enabled;
    }
    
    public Collection<Object> getChildren() {
		return Collections.emptyList();
	}
}
