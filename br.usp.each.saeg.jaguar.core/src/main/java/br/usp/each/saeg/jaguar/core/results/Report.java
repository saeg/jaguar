package br.usp.each.saeg.jaguar.core.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.FlatFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationReport;

public class Report {

	private static final String DEFUALT_RESULT_FILE = "\\JaguarReport.xml";
	private static final String DEFAULT_FOLDER = ".\\.jaguar\\";

	public void createReport(final File folder, final File reportFile, String className, Integer line) throws FileNotFoundException {

		Map<String, FaultClassification> jaguarFileList = getJaguarFiles(folder, reportFile);

		Summarizer summarizer = new Summarizer(jaguarFileList, className, line);
		FaultLocalizationReport faultLocalizationReport = summarizer.summarizePerformResults();

		JAXB.marshal(faultLocalizationReport, reportFile);

	}

	/**
	 * Extract the FaultClassification object of each Xml file inside the given
	 * folder. Except for fault.xml and the file with the report output name.
	 * 
	 * @param folder
	 *            The fodler to be searched.
	 * @param reportFile
	 *            The current report output file.
	 * @return A list of FaultLocalization objects.
	 */
	private Map<String, FaultClassification> getJaguarFiles(final File folder, final File reportFile) {

		List<File> resultFiles = FileUtils.findFilesEndingWith(folder, new String[] { ".xml" });
		Map<String, FaultClassification> jaguarFileMap = new HashMap<String, FaultClassification>();

		for (File file : resultFiles) {
			if (!file.getName().equals("fault.xml") && !file.getName().equals(reportFile.getName())) {
				try{
			        javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(
			        		FlatFaultClassification.class,
			        		HierarchicalFaultClassification.class
			        );
			        javax.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();
					jaguarFileMap.put(file.getName(), (FaultClassification) unmarshaller.unmarshal(file));
				}catch(DataBindingException e){
					continue;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		}

		return jaguarFileMap;
	}

	public static List<SuspiciousElement> extractElementsFromPackages(Collection<Package> packages) {
		List<SuspiciousElement> elements = new ArrayList<SuspiciousElement>();
		for (Package currentPackage : packages) {
			for (Class currentClass : currentPackage.getClasses()) {
				for (Method currentMethod : currentClass.getMethods()) {
					for (Requirement requirement : currentMethod.getRequirements()) {
						requirement.setName(currentClass.getName());
						elements.add(requirement);
					}
				}
			}
		}
		return elements;
	}

	/**
	 * First arg (arg[0]) is the falty class name (including package) and the
	 * second arg (arg[1]) is the falty line number.
	 * 
	 * If the first arg is not present, it is used the default = .\jaguar\ If
	 * the second arg is not present, it is used the default =
	 * {arg0}\JaguarReport.xml
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
		File folder = new File(DEFAULT_FOLDER);
		File resultFile = new File(folder.getAbsolutePath() + DEFUALT_RESULT_FILE);
		if (args.length < 2) {
			throw new IllegalArgumentException("Not enougth information, please add the Falty class name and line number.");
		}
		String className = args[0];
		Integer line = Integer.valueOf(args[1]);
		new Report().createReport(folder, resultFile, className, line);
	}

}