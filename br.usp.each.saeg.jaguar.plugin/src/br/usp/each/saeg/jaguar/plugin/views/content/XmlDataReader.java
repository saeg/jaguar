package br.usp.each.saeg.jaguar.plugin.views.content;

import java.io.File;
import javax.xml.bind.JAXB;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.TestCriteria;

public class XmlDataReader {

	private FaultClassification xmlRootElement;
	
	private final File testFile = new File("/home/higor/workspace/jaguar/br.usp.each.saeg.jaguar.codeforest/src/test/resources/codeForestTest.xml");
	//private final File testFile = new File("/home/higor/workspace/commons-math-sir/codeforest.xml");
	
	//public Object[] readPackage(File file){
	public Object[] readXmlFile(){
		xmlRootElement = JAXB.unmarshal(testFile, FaultClassification.class);
		TestCriteria criteria =  xmlRootElement.getTestCriteria();
		return criteria.getPackages().toArray();
	}
}
