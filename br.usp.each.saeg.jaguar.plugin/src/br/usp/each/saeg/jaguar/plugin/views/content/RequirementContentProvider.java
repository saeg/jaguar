package br.usp.each.saeg.jaguar.plugin.views.content;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;

public class RequirementContentProvider implements IStructuredContentProvider {

	private final Object[] EMPTY_LIST = new Object[0];
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer tableViewer, Object method, Object none) {
	}

	@Override
	public Object[] getElements(Object method) {
		return EMPTY_LIST;
		//return createRequirements();
	}

	public Object[] createRequirements(){
		List<Requirement>  requirementList = new ArrayList<Requirement> ();
		
		LineRequirement requirement1 = new LineRequirement();
		LineRequirement requirement2 = new LineRequirement();
		LineRequirement requirement3 = new LineRequirement();
		LineRequirement requirement4 = new LineRequirement();
		LineRequirement requirement5 = new LineRequirement();
		LineRequirement requirement6 = new LineRequirement();
		LineRequirement requirement7 = new LineRequirement();
		LineRequirement requirement8 = new LineRequirement();
		LineRequirement requirement9 = new LineRequirement();
		LineRequirement requirement10 = new LineRequirement();
		LineRequirement requirement11 = new LineRequirement();
		LineRequirement requirement12 = new LineRequirement();
		LineRequirement requirement13 = new LineRequirement();
		LineRequirement requirement14 = new LineRequirement();
		LineRequirement requirement15 = new LineRequirement();
		LineRequirement requirement16 = new LineRequirement();
		LineRequirement requirement17 = new LineRequirement();
		LineRequirement requirement18 = new LineRequirement();
		
		requirement1.setName("0");
		requirement1.setSuspiciousValue(1.0);
		requirement2.setName("10");
		requirement2.setSuspiciousValue(1.0);
		requirement3.setName("33");
		requirement3.setSuspiciousValue(0.95);
		requirement4.setName("102");
		requirement4.setSuspiciousValue(0.0);
		requirement5.setName("0");
		requirement5.setSuspiciousValue(0.0);
		requirement6.setName("7");
		requirement6.setSuspiciousValue(0.0);
		requirement7.setName("12");
		requirement7.setSuspiciousValue(0.5);
		requirement8.setName("10");
		requirement8.setSuspiciousValue(0.99);
		requirement9.setName("13");
		requirement9.setSuspiciousValue(0.9);
		requirement10.setName("0");
		requirement10.setSuspiciousValue(0.0);
		requirement11.setName("0");
		requirement11.setSuspiciousValue(0.99);
		requirement12.setName("15");
		requirement12.setSuspiciousValue(0.99);
		requirement13.setName("32");
		requirement13.setSuspiciousValue(0.93);
		requirement14.setName("0");
		requirement14.setSuspiciousValue(0.97);
		requirement15.setName("11");
		requirement15.setSuspiciousValue(0.71);
		requirement16.setName("21");
		requirement16.setSuspiciousValue(0.0);
		requirement17.setName("0");
		requirement17.setSuspiciousValue(0.5);
		requirement18.setName("60");
		requirement18.setSuspiciousValue(0.0);
		
		requirementList.add(requirement1);
		requirementList.add(requirement2);
		requirementList.add(requirement3);
		requirementList.add(requirement4);
		requirementList.add(requirement5);
		requirementList.add(requirement6);
		requirementList.add(requirement7);
		requirementList.add(requirement8);
		requirementList.add(requirement9);
		requirementList.add(requirement10);
		requirementList.add(requirement11);
		requirementList.add(requirement12);
		requirementList.add(requirement13);
		requirementList.add(requirement14);
		requirementList.add(requirement15);
		requirementList.add(requirement16);
		requirementList.add(requirement17);
		requirementList.add(requirement18);
		
		return requirementList.toArray();
	}
	
	public Object[] readXml(){
		XmlDataReader xml = new XmlDataReader();
		return xml.readXmlFile();
	}
	
}
