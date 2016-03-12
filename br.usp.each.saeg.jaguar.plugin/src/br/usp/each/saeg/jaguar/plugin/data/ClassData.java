package br.usp.each.saeg.jaguar.plugin.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Position;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;


/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class ClassData{// implements Comparable<ClassData> {
	
	private Position packagePosition;
    private float packageScore;
    private Position openPosition;
    private Position closePosition;
    private IMarker openMarker;
    private IMarker closeMarker;
    private int startLine;
    private int endLine;
    private int closeLine;

    private String value;
    private float score;
    private List<MethodData> methodData = new ArrayList<MethodData>();
    private int occurrences;
    private boolean anonymous;

    private String name;

    private IResource resource;
    private String logLine;
    private boolean enabled = true;
    
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

    public float getRoundedScore(int scale) {
        return roundScore(score,scale);
    }
    
    public List<MethodData> getMethodData() {
        return methodData;
    }
    public void setMethodData(List<MethodData> methodData) {
        if (null != methodData) {
            this.methodData = methodData;
        }
    }

    public int getOccurrences() {
        return occurrences;
    }
    public void setOcurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    /*public Size getSize() {
        return Size.from(score);
    }*/

    private int leftSide() {
        return (int) Math.ceil((double) (MethodData.covered(methodData).size())/2);
    }

    private int rightSide() {
        return Math.max(MethodData.covered(methodData).size() - leftSide(), 0);
    }

    public int getHeight() {
        return Math.max(leftSide(), rightSide());
    }

    public boolean isAnonymous() {
        return anonymous;
    }
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Position getPackagePosition() {
        return packagePosition;
    }
    public void setPackagePosition(Position packagePosition) {
        this.packagePosition = packagePosition;
    }

    public float getPackageScore() {
        return packageScore;
    }
    public void setPackageScore(float packageScore) {
        this.packageScore = packageScore;
    }

    public Position getOpenPosition() {
        return openPosition;
    }
    public void setOpenPosition(Position openPosition) {
        this.openPosition = openPosition;
    }

    public Position getClosePosition() {
        return closePosition;
    }
    public void setClosePosition(Position closePosition) {
        this.closePosition = closePosition;
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

    public void setResource(IResource resource) {
        this.resource = resource;
    }

    public boolean isScoreBetween(float min, float max) {
        return Float.compare(score, max) <= 0 && Float.compare(score, min) >= 0;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLogLine() {
        return logLine;
    }
    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

    public int getTotalLoCs() {
        int result = 0;
        for (MethodData branch : methodData) {
            result += branch.getTotalLoCs();
        }
        return result;
    }

    //this comparison seems to be only for the forest
    /*@Override
    public int compareTo(ClassData o) {
        return Configuration.TREE_DATA_COMPARE_STRATEGY.compare(this, o);
    }*/

    public static List<ClassData> covered(List<ClassData> arg) {
        List<ClassData> result = new ArrayList<ClassData>();
        for (ClassData data : arg) {
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
	
    public float roundScore(float originalScore, int scale){
    	BigDecimal roundedScore = new BigDecimal(Float.toString(originalScore));
    	roundedScore = roundedScore.setScale(scale, BigDecimal.ROUND_DOWN);
    	return roundedScore.floatValue();
    }
    
    public String getSingleName(){
		return name.substring(name.lastIndexOf('.')+1);
	}
    
    @Override
	public String toString() {
		return "Class [name=" + name + ", score=" + score + "]";
	}
}
