package br.usp.each.saeg.jaguar.core.output.xml.hierarchical;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXB;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

public class XmlWriter {

	private static final String FOLDER_NAME = ".jaguar";

	private ArrayList<AbstractTestRequirement> testRequirements;
	private Heuristic currentHeuristic;
	private Long coverageTime;
	
	public XmlWriter(ArrayList<AbstractTestRequirement> testRequirements, Heuristic currentHeuristic, Long coverageTime) {
		super();
		this.testRequirements = testRequirements;
		this.currentHeuristic = currentHeuristic;
		this.coverageTime = coverageTime;
	}

	public void generateXML(File projectDir, String fileName) {
		CodeForestXmlBuilder xmlBuilder = createXmlBuilder();
		File xmlFile = write(xmlBuilder, projectDir, fileName);
		System.out.println("Output xml created at: " + xmlFile.getAbsolutePath());
	}

	private File write(CodeForestXmlBuilder xmlBuilder, File projectDir, String fileName) {
		projectDir = new File(projectDir.getPath() + System.getProperty("file.separator") + FOLDER_NAME);
		if (!projectDir.exists()){
			projectDir.mkdirs();
		}
		
		File xmlFile = new File(projectDir.getAbsolutePath() + System.getProperty("file.separator") + fileName + ".xml");
		JAXB.marshal(xmlBuilder.build(), xmlFile);
		return xmlFile;
	}
	
	private CodeForestXmlBuilder createXmlBuilder() {
		CodeForestXmlBuilder xmlBuilder = new CodeForestXmlBuilder();
		xmlBuilder.project("fault localization");
		xmlBuilder.heuristic(currentHeuristic);
		xmlBuilder.timeSpent(coverageTime);
		xmlBuilder.requirementType(getType());
		
		for (AbstractTestRequirement testRequirement : testRequirements) {
			if (Math.abs(testRequirement.getSuspiciousness()) > 0){
				xmlBuilder.addTestRequirement(testRequirement);
			}
		}
		return xmlBuilder;
	}
	
	private Type getType() {
		if (testRequirements.isEmpty()){
			return null;
		}
		
		if (AbstractTestRequirement.Type.LINE == testRequirements.iterator().next().getType()){
			return Requirement.Type.LINE;
		}else if(AbstractTestRequirement.Type.DUA == testRequirements.iterator().next().getType()){
			return Requirement.Type.DUA;
		}
		
		return null;
	}
	
}
