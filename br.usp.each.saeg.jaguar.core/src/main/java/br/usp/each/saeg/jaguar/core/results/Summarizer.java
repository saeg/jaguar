package br.usp.each.saeg.jaguar.core.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXB;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationEntry;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationReport;

public class Summarizer {

	private FaultLocalizationReport summarizePerformResults(File folder) throws FileNotFoundException {
		Package fault =	getFault(folder);
		SuspiciousElement faultyElement = extractElementsFromPackages(Collections.singleton(fault)).iterator().next();
		
		Collection<FaultLocalizationEntry> reportEntries = new ArrayList<FaultLocalizationEntry>();
		
		List<File> resultsFile = FileUtils.findFilesEndingWith(folder, new String[]{".xml"});
		for (File file : resultsFile) {
			if (file.getName().equals("fault.xml") || file.getName().equals("report.xml")) continue;
			
			FaultClassification resultXml = JAXB.unmarshal(file, FaultClassification.class);
			Set<SuspiciousElement> elements = getElementTreeSet(resultXml);
			
			FaultLocalizationEntry reportEntry = new FaultLocalizationEntry();
			reportEntry.setCoverageType(resultXml.getTestCriteria().getRequirementType().name());
			reportEntry.setHeuristic(resultXml.getTestCriteria().getHeuristicType());

			boolean foundFault = false;
			
			for (SuspiciousElement suspiciousElement : elements) {
				if (!foundFault){
					if (faultyElement.getName().equals(suspiciousElement.getName())){
						foundFault = true;
					}
					reportEntry.increaseCost();
				}else{
					if (suspiciousElement.getSuspiciousValue().equals(faultyElement.getSuspiciousValue())){
						reportEntry.increaseMaxCost();
					}else{
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

	private Set<SuspiciousElement> getElementTreeSet(
			FaultClassification resultXml) {
		Collection<Package> packages = resultXml.getTestCriteria().getPackages();
		Set<SuspiciousElement> elements = extractElementsFromPackages(packages);
		return elements;
	}

	private Set<SuspiciousElement> extractElementsFromPackages(Collection<Package> packages) {
		Set<SuspiciousElement> elements = new TreeSet<SuspiciousElement>();
		for (Package currentPackage : packages) {
			Collection<Class> classes =  currentPackage.getClasses();
			for (Class currentClass : classes) {
				Collection<Method> methods =  currentClass.getMethods();
				for (Method currentMethod : methods) {
					Collection<Requirement> requirements = currentMethod.getRequirements();
					for (Requirement requirement : requirements) {
						requirement.setName(currentClass.getName() + "." + currentMethod.getName());
						elements.add(requirement);
					}
				}
			}
		}
		return elements;
	}

	private Package getFault(File folder) throws FileNotFoundException {
		File faultFile = FileUtils.getFile(folder, "fault.xml");
		if (faultFile == null){
			throw new FileNotFoundException("No file named 'fault.xml was found");
		}else{
			 return JAXB.unmarshal(faultFile, Package.class);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File folder = args.length > 0 ? new File(args[0]) : new File(".\\jaguar-results\\");
		FaultLocalizationReport finalReport = new Summarizer().summarizePerformResults(folder);
		File xmlFile = new File(folder.getAbsolutePath() + "\\report.xml");
		JAXB.marshal(finalReport, xmlFile);
	}

}