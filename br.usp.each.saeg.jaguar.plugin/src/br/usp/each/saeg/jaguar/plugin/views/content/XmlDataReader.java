package br.usp.each.saeg.jaguar.plugin.views.content;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;

import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class XmlDataReader {

	private HierarchicalFaultClassification xmlRootElement;
	
	//private final File testFile = new File("/home/higor/workspace/jaguar/br.usp.each.saeg.jaguar.codeforest/src/test/resources/codeForestTest.xml");
	private final File testFile = new File("/home/higor/workspace/commons-math-sir/jaguar.xml");
	
	//public Object[] readPackage(File file){
	public Object[] readXmlFile(){
		xmlRootElement = JAXB.unmarshal(testFile, HierarchicalFaultClassification.class);
		HierarchicalFaultClassification criteria =  xmlRootElement;
		return criteria.getPackages().toArray();
	}
	
	public static HierarchicalFaultClassification readXml(File fileReport){
		 try {
			 	JAXBContext context = JAXBContext.newInstance(HierarchicalFaultClassification.class);
			 	HierarchicalFaultClassification faultClassification =  (HierarchicalFaultClassification) context.createUnmarshaller().unmarshal(fileReport);
	            return faultClassification;
	        } catch (Exception e) {
	        	JaguarPlugin.log(e);
	            throw new RuntimeException(e);
	            
	        }
	}
}
