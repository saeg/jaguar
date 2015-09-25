package br.usp.each.saeg.jaguar.plugin.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Position;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;
import br.usp.each.saeg.jaguar.plugin.utils.CollectionUtils;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class MethodData  implements Comparable<MethodData> {

    private List<RequirementData> requirementData = new ArrayList<RequirementData>();
    private String name;
    private String value;
    private float score;
    private int ocurrences;
    private Position position;
    private Position closePosition;
    private IMarker openMarker;
    private IMarker closeMarker;
    private ClassData parent;
    private IResource resource;
    private int scriptPosition;
    private float scriptScore;
    private String logLine;

    private int startLine;
    private int endLine;
    private int closeLine;
    
    private boolean enabled = true;
    
    public MethodData(ClassData parent) {
        this.parent = parent;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getClosePosition() {
        return closePosition;
    }
    public void setClosePosition(Position closePosition) {
        this.closePosition = closePosition;
    }

    public List<RequirementData> getRequirementData() {
        return requirementData;
    }
    public List<RequirementData> getCoveredRequirementData() {
        return RequirementData.covered(requirementData);
    }
    public void setRequirementData(List<RequirementData> requirementData) {
        if (null != requirementData) {
            this.requirementData = requirementData;
        }
        Collections.sort(this.requirementData);
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }

    public int getOcurrences() {
        return ocurrences;
    }
    public void setOcurrences(int ocurrences) {
        this.ocurrences = ocurrences;
    }

    public List<Float> getScoreOfCoveredEvenLoc() {
        return from(CollectionUtils.getEvenIndexedValuesFrom(RequirementData.covered(requirementData)));
    }

    public List<Float> getScoreOfCoveredOddLoc() {
        return from(CollectionUtils.getOddIndexedValuesFrom(RequirementData.covered(requirementData)));
    }

    private List<Float> from(List<RequirementData> partial) {
        List<Float> result = new ArrayList<Float>();
        for (RequirementData data : partial) {
            result.add(data.getScore());
        }
        return result;
    }

    public boolean isScoreBetween(float min, float max) {
        return Float.compare(score, max) <= 0 && Float.compare(score, min) >= 0;
    }

    public IMarker getOpenMarker() {
        if (openMarker == null) {
            openMarker = CodeMarkerFactory.findMarker(resource, score, startLine, endLine);
        }
        return openMarker;
    }

    public IMarker getCloseMarker() {
        if (closeMarker == null) {
            closeMarker = CodeMarkerFactory.findMarker(resource, score, closeLine);
        }
        return closeMarker;
    }

    public int getStartLine() {
        return startLine;
    }
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getCloseLine() {
        return closeLine;
    }
    public void setCloseLine(int closeLine) {
        this.closeLine = closeLine;
    }

    public int getProportionOfCoveredLoCs() {
        if (getTotalLoCs() == 0) {
            return 0;
        }
        if (parent.getTotalLoCs() == 0) {
            return 0;
        }
        return (int) ((getTotalLoCs() / (float) parent.getTotalLoCs()) * 100);
    }

    public void setResource(IResource resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getScriptPosition() {
        return scriptPosition;
    }
    public void setScriptPosition(Integer scriptPosition) {
        if (scriptPosition != null) {
            this.scriptPosition = scriptPosition;
        }
    }

    public float getScriptScore() {
        return scriptScore;
    }
    public void setScriptScore(Float scriptScore) {
        if (scriptScore != null) {
            this.scriptScore = scriptScore;
        }
    }

    public String getLogLine() {
        return logLine;
    }
    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

    @Override
    public int compareTo(MethodData o) {
        return new CompareToBuilder()
        .append(o.getScore(), score)
        .append(o.getOcurrences(), ocurrences)
        .append(CollectionUtils.size(o.getRequirementData()), CollectionUtils.size(requirementData))
        .toComparison();
    }

    public int getTotalLoCs() {
        return RequirementData.covered(requirementData).size();
    }

    public static List<MethodData> covered(List<MethodData> arg) {
        List<MethodData> result = new ArrayList<MethodData>();
        for (MethodData data : arg) {
            if (data.getScore() < 0 || data.getCoveredRequirementData().size() == 0) {
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
    
    public Collection<RequirementData> getChildren() {
		return getRequirementData();
	}
    
    @Override
	public String toString() {
		return "Method [name=" + name + ", score=" + score + "]";
	}
}
