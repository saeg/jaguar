package br.usp.each.saeg.jaguar.core.results;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationEntry;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationReport;

public class Summarizer {

	private Collection<FaultLocalizationEntry> reportEntries = new ArrayList<FaultLocalizationEntry>();
	private final List<FaultClassification> jaguarFiles;
	private final SuspiciousElement faultyElement;
	
	public Summarizer(List<FaultClassification> jaguarFiles, SuspiciousElement faultyElement) {
		super();
		this.jaguarFiles = jaguarFiles;
		this.faultyElement = faultyElement;
	}

	public FaultLocalizationReport summarizePerformResults() throws FileNotFoundException {
		for (FaultClassification faultClassification : jaguarFiles) {
			List<SuspiciousElement> elements = getElements(faultClassification);
			Collections.sort(elements);
			
			FaultLocalizationEntry reportEntry = new FaultLocalizationEntry();
			reportEntry.setCoverageType(faultClassification.getTestCriteria().getRequirementType().name());
			reportEntry.setHeuristic(faultClassification.getTestCriteria().getHeuristicType());

			boolean faultFound = false;
			for (SuspiciousElement suspiciousElement : elements) {
				if (!faultFound) {
				
					if (containTheFault(faultyElement, suspiciousElement)) {
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
		String lineName = suspiciousElement.getName();
		if (suspiciousElement instanceof DuaRequirement){
			DuaRequirement dua = (DuaRequirement) suspiciousElement;
			reportEntry.addLine(lineName + ":" + dua.getDef(), dua.getSuspiciousValue());
			reportEntry.addLine(lineName + ":" + dua.getUse(), dua.getSuspiciousValue());
			if (dua.getTarget() != -1){
				reportEntry.addLine(lineName + ":" + dua.getTarget(), dua.getSuspiciousValue());
			}
		}else if (suspiciousElement instanceof LineRequirement){
			reportEntry.addLine(lineName + ":" + suspiciousElement.getLocation(), suspiciousElement.getSuspiciousValue());
		}
	}
	
	private boolean containTheFault(SuspiciousElement faultyElement, SuspiciousElement suspiciousElement) {
		if (suspiciousElement.getName().equals(faultyElement.getName())){
			if (suspiciousElement instanceof DuaRequirement){
				if (((DuaRequirement) suspiciousElement).getDef() == faultyElement.getLocation())
					return true;
				if (((DuaRequirement) suspiciousElement).getUse() == faultyElement.getLocation())
					return true;
				if (((DuaRequirement) suspiciousElement).getTarget() == faultyElement.getLocation())
					return true;
			}else if (suspiciousElement instanceof LineRequirement){
				if (suspiciousElement.getLocation().equals(faultyElement.getLocation()))
					return true;
			}
		}
		return false;
	}

	private List<SuspiciousElement> getElements(FaultClassification resultXml) {
		Collection<Package> packages = resultXml.getTestCriteria().getPackages();
		List<SuspiciousElement> elements = Report.extractElementsFromPackages(packages);
		return elements;
	}

}