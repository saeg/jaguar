package br.usp.each.saeg.jaguar.plugin.project;

import java.nio.file.FileSystem;
import java.text.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class ProjectState {
	
	private final Map<String, Collection<ClassData>> analysisResult = new HashMap<String, Collection<ClassData>>();
	private final Set<String> marked = new HashSet<String>();
    private final Map<String, Collection<SimpleMarkerAnnotation>> fileAnnotations = new HashMap<String, Collection<SimpleMarkerAnnotation>>();
    private boolean analyzed = false;
    //private final String folderName = "/tmp/" + "##project##_" + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + "/";
    private final String folderName = System.getProperty("user.home") + System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator");// + "##project##";
	private final String logFileName = folderName + ".jaguar_commands.log";
    private final String screenFileName = folderName + "screenshot" + "_" + "##number##" + ".jpg";
	private int count = 1;
	private final List<PackageData> packageResult = new ArrayList<PackageData>();
	private Type requirementType = Type.LINE;
	
	 public Set<String> getMarked() {
	        return marked;
	 }

	public Map<String, Collection<ClassData>> getAnalysisResult() {
        return analysisResult;
    }
	
	public boolean isAnalyzed() {
        return analyzed;
    }
    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public boolean containsAnalysis() {
        return analysisResult.size() > 0;
    }

    public Map<String, Collection<SimpleMarkerAnnotation>> getFileAnnotations() {
        return fileAnnotations;
    }
    
    public List<ClassData> getAllAnalysis() {
        Collection<Collection<ClassData>> all = analysisResult.values();
        List<ClassData> result = new ArrayList<ClassData>();
        for (Collection<ClassData> subColl : all) {
            for (ClassData data : subColl) {
                result.add(data);
            }
        }
        return result;
    }
    
    public synchronized void clear() {
        analysisResult.clear();
        marked.clear();
        fileAnnotations.clear();
        analyzed = false;
    }
	
	public String formatLogFileName(String projectName) {
        return logFileName.replace("##project##", projectName);
    }

    public String formatScreenFileName(String projectName) {
        return screenFileName.replace("##project##", projectName).replace("##number##", StringUtils.leftPad(String.valueOf(count++), 8, '0'));
    }

    public String formatFolderFileName(String projectName) {
	        return folderName.replace("##project##", projectName);
	 }
    
    public void createPackageResult(HierarchicalFaultClassification testCriteria){
    	if(analyzed){
    		List<ClassData> listClassData = getAllAnalysis();
	    	for(Package pack : testCriteria.getPackages()){
	    		PackageData packData = new PackageData();
	    		List<ClassData> packClassData = new ArrayList<ClassData>();
	    		packData.setName(pack.getName());
	    		packData.setScore(pack.getSuspiciousValue().floatValue());
	    		packData.setName(pack.getName());
	    		for(Class clazz : pack.getClasses()){
	    			for(ClassData classData : listClassData){
		    			if(clazz.getName().equals(classData.getName())){
		    				packClassData.add(classData);
		    			}
		    		}
	    		}
	    		packData.setClassData(packClassData);
	    		packageResult.add(packData);
	    	}
    	}
    }
    
    public List<PackageData> getPackageDataResult(){
    	return packageResult;
    }
    
    public Type getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(Type requirementType) {
		this.requirementType = requirementType;
	}

    
}
