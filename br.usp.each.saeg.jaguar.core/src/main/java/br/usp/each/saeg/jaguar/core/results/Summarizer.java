package br.usp.each.saeg.jaguar.core.results;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.FlatFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationEntry;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationReport;

public class Summarizer {

	private Collection<FaultLocalizationEntry> reportEntries = new ArrayList<FaultLocalizationEntry>();
	private final Map<String, FaultClassification> jaguarFiles;
	private final String faultyClassName;
	private final Integer faltyLineNumber;
	
	public Summarizer(Map<String, FaultClassification> jaguarFiles, String faultyClassName, Integer faltyLineNumber) {
		super();
		this.jaguarFiles = jaguarFiles;
		this.faultyClassName = faultyClassName;
		this.faltyLineNumber = faltyLineNumber;
	}

	public FaultLocalizationReport summarizePerformResults() throws FileNotFoundException {
		for (String fileName : jaguarFiles.keySet()) {
			FaultClassification faultClassification = jaguarFiles.get(fileName);
			List<? extends SuspiciousElement> elements = getElements(faultClassification);
			Collections.sort(elements);
			
			FaultLocalizationEntry reportEntry = new FaultLocalizationEntry();
			reportEntry.setFileName(fileName);
			reportEntry.setCoverageType(faultClassification.getRequirementType().name());
			reportEntry.setHeuristic(faultClassification.getHeuristic());
			reportEntry.setTotalTime(faultClassification.getTimeSpent());
			reportEntry.setFaultSuspiciousValue(0D);
			
			boolean faultFound = false;
			for (SuspiciousElement suspiciousElement : elements) {
				if (!faultFound) {
				
					if (containTheFault(suspiciousElement)) {
						faultFound = true;
						reportEntry.setFaultSuspiciousValue(suspiciousElement.getSuspiciousValue());
					}
					addLines(reportEntry, suspiciousElement);

				}else{
					
					if (suspiciousElement.getSuspiciousValue().equals(reportEntry.getFaultSuspiciousValue())) {
						addLines(reportEntry, suspiciousElement);
					} else {
						break;
					}
					
				}
				
			}
			
			reportEntries.add(reportEntry);
		}
		FaultLocalizationReport finalReport = new FaultLocalizationReport();
		finalReport.setEntries(reportEntries);
		return finalReport;
	}

	/**
	 * Add the element lines to the report list of lines.
	 * If it is a Dua, it is added the def, use and target lines.
	 * If it is a Line, it is added just the line.
	 *   
	 * @param reportEntry the reportEntry to be added the lines
	 * @param suspiciousElement the element (dua or line)
	 */
	private void addLines(FaultLocalizationEntry reportEntry, SuspiciousElement suspiciousElement) {
		String className = suspiciousElement.getName();
		if (suspiciousElement instanceof DuaRequirement){
			DuaRequirement dua = (DuaRequirement) suspiciousElement;
			reportEntry.addLine(className + ":" + dua.getDef(), dua.getSuspiciousValue());
			reportEntry.addLine(className + ":" + dua.getUse(), dua.getSuspiciousValue());
			if (dua.getTarget() != -1){
				reportEntry.addLine(className + ":" + dua.getTarget(), dua.getSuspiciousValue());
			}
		}else if (suspiciousElement instanceof LineRequirement){
			reportEntry.addLine(className + ":" + suspiciousElement.getLocation(), suspiciousElement.getSuspiciousValue());
		}
	}
	
	private boolean containTheFault(SuspiciousElement suspiciousElement) {
		if (suspiciousElement.getName().equals(faultyClassName)){
			if (suspiciousElement instanceof DuaRequirement){
				DuaRequirement dua = (DuaRequirement) suspiciousElement;
				if (faltyLineNumber.equals(dua.getDef()))
					return true;
				if (faltyLineNumber.equals(dua.getUse()))
					return true;
				if (faltyLineNumber.equals(dua.getTarget()))
					return true;
			}else if (suspiciousElement instanceof LineRequirement){
				if (faltyLineNumber.equals(suspiciousElement.getLocation()))
					return true;
			}
		}
		return false;
	}

	private List<? extends SuspiciousElement> getElements(FaultClassification resultXml) {
		if (resultXml instanceof HierarchicalFaultClassification){
			HierarchicalFaultClassification hierachicalXml = (HierarchicalFaultClassification) resultXml;
			Collection<Package> packages = hierachicalXml.getPackages();
			return Report.extractElementsFromPackages(packages);
		}else if(resultXml instanceof FlatFaultClassification){
			FlatFaultClassification flatXml = (FlatFaultClassification) resultXml;
			return flatXml.getRequirements();
		}else{
			throw new RuntimeException("Unknown type of FaultClassification objetc");
		}
	}

}