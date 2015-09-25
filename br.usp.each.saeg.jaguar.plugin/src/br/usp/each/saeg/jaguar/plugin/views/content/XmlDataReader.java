package br.usp.each.saeg.jaguar.plugin.views.content;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;

import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.TestCriteria;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class XmlDataReader {

	private FaultClassification xmlRootElement;
	
	//private final File testFile = new File("/home/higor/workspace/jaguar/br.usp.each.saeg.jaguar.codeforest/src/test/resources/codeForestTest.xml");
	private final File testFile = new File("/home/higor/workspace/commons-math-sir/codeforest.xml");
	
	//public Object[] readPackage(File file){
	public Object[] readXmlFile(){
		xmlRootElement = JAXB.unmarshal(testFile, FaultClassification.class);
		TestCriteria criteria =  xmlRootElement.getTestCriteria();
		return criteria.getPackages().toArray();
	}
	
	public static TestCriteria readXml(File fileReport){
		 try {
	            /*JAXBContext context = JAXBContext.newInstance(TestCriteria.class);
	            return (TestCriteria) context.createUnmarshaller().unmarshal(fileReport);*/
			 JAXBContext context = JAXBContext.newInstance(FaultClassification.class);
	            FaultClassification faultClassification =  (FaultClassification) context.createUnmarshaller().unmarshal(fileReport);
	            return faultClassification.getTestCriteria();
	        } catch (Exception e) {
	        	JaguarPlugin.log(e);
	            throw new RuntimeException(e);
	            
	        }
	}
}
