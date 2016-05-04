package br.usp.each.saeg.jaguar.codefores.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;

public class HierarchicalFaultClassificationTest {

	@Test
	public void extractElementsFromPackages() throws ClassNotFoundException {
		final LineRequirement requirement_P1_C1_M1_R1 = new LineRequirement();
		requirement_P1_C1_M1_R1.setLocation(1111);
		requirement_P1_C1_M1_R1.setName("1111");
		requirement_P1_C1_M1_R1.setSuspiciousValue(0.0);

		final LineRequirement requirement_P1_C1_M1_R2 = new LineRequirement();
		requirement_P1_C1_M1_R2.setLocation(1112);
		requirement_P1_C1_M1_R2.setName("1112");
		requirement_P1_C1_M1_R2.setSuspiciousValue(0.0);

		final List<Requirement> requirementList_P1_C1_M1 = new ArrayList<Requirement>();
		requirementList_P1_C1_M1.add(requirement_P1_C1_M1_R1);
		requirementList_P1_C1_M1.add(requirement_P1_C1_M1_R2);

		final Method method_P1_C1_M1 = new Method();
		method_P1_C1_M1.setId(1);
		method_P1_C1_M1.setLocation(5);
		method_P1_C1_M1.setName("method_P1_C1_M1");
		method_P1_C1_M1.setNumber(1);
		method_P1_C1_M1.setPosition(1);
		method_P1_C1_M1.setSuspiciousValue(0.0);
		method_P1_C1_M1.setRequirements(requirementList_P1_C1_M1);
		
		final LineRequirement requirement_P1_C1_M2_R1 = new LineRequirement();
		requirement_P1_C1_M2_R1.setLocation(1121);
		requirement_P1_C1_M2_R1.setName("1121");
		requirement_P1_C1_M2_R1.setSuspiciousValue(0.0);
		
		final List<Requirement> requirementList_P1_C1_M2 = new ArrayList<Requirement>();
		requirementList_P1_C1_M2.add(requirement_P1_C1_M2_R1);

		final Method method_P1_C1_M2 = new Method();
		method_P1_C1_M2.setId(0);
		method_P1_C1_M2.setLocation(44);
		method_P1_C1_M2.setName("method_P1_C1_M2");
		method_P1_C1_M2.setNumber(2);
		method_P1_C1_M2.setPosition(111);
		method_P1_C1_M2.setSuspiciousValue(0.0);
		method_P1_C1_M2.setRequirements(requirementList_P1_C1_M2);

		final List<Method> methodList_P1_C1 = new ArrayList<Method>();
		methodList_P1_C1.add(method_P1_C1_M1);
		methodList_P1_C1.add(method_P1_C1_M2);

		final Class class_P1_C1 = new Class();
		class_P1_C1.setLocation(19);
		class_P1_C1.setName("class_P1_C1");
		class_P1_C1.setNumber(13);
		class_P1_C1.setSuspiciousValue(0.0);
		class_P1_C1.setMethods(methodList_P1_C1);

		final LineRequirement requirement_P1_C2_M1_R1 = new LineRequirement();
		requirement_P1_C2_M1_R1.setLocation(1211);
		requirement_P1_C2_M1_R1.setName("1211");
		requirement_P1_C2_M1_R1.setSuspiciousValue(0.0);

		final LineRequirement requirement_P1_C2_M1_R2 = new LineRequirement();
		requirement_P1_C2_M1_R2.setLocation(1212);
		requirement_P1_C2_M1_R2.setName("1212");
		requirement_P1_C2_M1_R2.setSuspiciousValue(0.0);

		final List<Requirement> requirementList_P1_C2_M1 = new ArrayList<Requirement>();
		requirementList_P1_C2_M1.add(requirement_P1_C2_M1_R1);
		requirementList_P1_C2_M1.add(requirement_P1_C2_M1_R2);

		final Method method_P1_C2_M1 = new Method();
		method_P1_C2_M1.setId(1);
		method_P1_C2_M1.setLocation(60);
		method_P1_C2_M1.setName("method_P1_C2_M1");
		method_P1_C2_M1.setNumber(11);
		method_P1_C2_M1.setPosition(115);
		method_P1_C2_M1.setSuspiciousValue(0.0);
		method_P1_C2_M1.setRequirements(requirementList_P1_C2_M1);
		
		final LineRequirement requirement_P1_C2_M2_R1 = new LineRequirement();
		requirement_P1_C2_M2_R1.setLocation(1221);
		requirement_P1_C2_M2_R1.setName("1221");
		requirement_P1_C2_M2_R1.setSuspiciousValue(0.0);
		
		final List<Requirement> requirementList_P1_C2_M2 = new ArrayList<Requirement>();
		requirementList_P1_C2_M2.add(requirement_P1_C2_M2_R1);

		final Method method_P1_C2_M2 = new Method();
		method_P1_C2_M2.setId(0);
		method_P1_C2_M2.setLocation(44);
		method_P1_C2_M2.setName("method_P1_C2_M2");
		method_P1_C2_M2.setNumber(2);
		method_P1_C2_M2.setPosition(111);
		method_P1_C2_M2.setSuspiciousValue(0.0);
		method_P1_C2_M2.setRequirements(requirementList_P1_C2_M2);

		final List<Method> methodList_P1_C2 = new ArrayList<Method>();
		methodList_P1_C2.add(method_P1_C2_M1);
		methodList_P1_C2.add(method_P1_C2_M2);
		
		final Class class_P1_C2 = new Class();
		class_P1_C2.setLocation(10);
		class_P1_C2.setName("class_P1_C2");
		class_P1_C2.setNumber(5);
		class_P1_C2.setSuspiciousValue(0.0);
		class_P1_C2.setMethods(methodList_P1_C2);

		final List<Class> classList_P1 = new ArrayList<Class>();
		classList_P1.add(class_P1_C1);
		classList_P1.add(class_P1_C2);

		final Package package_P1 = new Package();
		package_P1.setName("package_P1");
		package_P1.setNumber(359);
		package_P1.setSuspiciousValue(0.0);
		package_P1.setClasses(classList_P1);
		
		final LineRequirement requirement_P2_C1_M1_R1 = new LineRequirement();
		requirement_P2_C1_M1_R1.setLocation(2111);
		requirement_P2_C1_M1_R1.setName("2111");
		requirement_P2_C1_M1_R1.setSuspiciousValue(0.0);

		final List<Requirement> requirementList_P2_C1_M1 = new ArrayList<Requirement>();
		requirementList_P2_C1_M1.add(requirement_P2_C1_M1_R1);

		final Method method_P2_C1_M1 = new Method();
		method_P2_C1_M1.setId(1);
		method_P2_C1_M1.setLocation(5);
		method_P2_C1_M1.setName("method_P2_C1_M1");
		method_P2_C1_M1.setNumber(1);
		method_P2_C1_M1.setPosition(1);
		method_P2_C1_M1.setSuspiciousValue(0.0);
		method_P2_C1_M1.setRequirements(requirementList_P2_C1_M1);
		
		final LineRequirement requirement_P2_C1_M2_R1 = new LineRequirement();
		requirement_P2_C1_M2_R1.setLocation(2121);
		requirement_P2_C1_M2_R1.setName("2121");
		requirement_P2_C1_M2_R1.setSuspiciousValue(0.0);
		
		final List<Requirement> requirementList_P2_C1_M2 = new ArrayList<Requirement>();
		requirementList_P2_C1_M2.add(requirement_P2_C1_M2_R1);

		final Method method_P2_C1_M2 = new Method();
		method_P2_C1_M2.setId(0);
		method_P2_C1_M2.setLocation(44);
		method_P2_C1_M2.setName("method_P1_C1_M2");
		method_P2_C1_M2.setNumber(2);
		method_P2_C1_M2.setPosition(111);
		method_P2_C1_M2.setSuspiciousValue(0.0);
		method_P2_C1_M2.setRequirements(requirementList_P2_C1_M2);

		final List<Method> methodList_P2_C1 = new ArrayList<Method>();
		methodList_P2_C1.add(method_P2_C1_M1);
		methodList_P2_C1.add(method_P2_C1_M2);

		final Class class_P2_C1 = new Class();
		class_P2_C1.setLocation(19);
		class_P2_C1.setName("class_P2_C1");
		class_P2_C1.setNumber(13);
		class_P2_C1.setSuspiciousValue(0.0);
		class_P2_C1.setMethods(methodList_P2_C1);

		final LineRequirement requirement_P2_C2_M1_R1 = new LineRequirement();
		requirement_P2_C2_M1_R1.setLocation(2211);
		requirement_P2_C2_M1_R1.setName("2211");
		requirement_P2_C2_M1_R1.setSuspiciousValue(0.0);

		final LineRequirement requirement_P2_C2_M1_R2 = new LineRequirement();
		requirement_P2_C2_M1_R2.setLocation(2212);
		requirement_P2_C2_M1_R2.setName("2212");
		requirement_P2_C2_M1_R2.setSuspiciousValue(0.0);

		final List<Requirement> requirementList_P2_C2_M1 = new ArrayList<Requirement>();
		requirementList_P2_C2_M1.add(requirement_P2_C2_M1_R1);
		requirementList_P2_C2_M1.add(requirement_P2_C2_M1_R2);

		final Method method_P2_C2_M1 = new Method();
		method_P2_C2_M1.setId(1);
		method_P2_C2_M1.setLocation(60);
		method_P2_C2_M1.setName("method_P2_C2_M1");
		method_P2_C2_M1.setNumber(11);
		method_P2_C2_M1.setPosition(115);
		method_P2_C2_M1.setSuspiciousValue(0.0);
		method_P2_C2_M1.setRequirements(requirementList_P2_C2_M1);
		
		final LineRequirement requirement_P2_C2_M2_R1 = new LineRequirement();
		requirement_P2_C2_M2_R1.setLocation(2221);
		requirement_P2_C2_M2_R1.setName("2221");
		requirement_P2_C2_M2_R1.setSuspiciousValue(0.0);
		
		final List<Requirement> requirementList_P2_C2_M2 = new ArrayList<Requirement>();
		requirementList_P2_C2_M2.add(requirement_P2_C2_M2_R1);

		final Method method_P2_C2_M2 = new Method();
		method_P2_C2_M2.setId(0);
		method_P2_C2_M2.setLocation(44);
		method_P2_C2_M2.setName("method_P1_C2_M2");
		method_P2_C2_M2.setNumber(2);
		method_P2_C2_M2.setPosition(111);
		method_P2_C2_M2.setSuspiciousValue(0.0);
		method_P2_C2_M2.setRequirements(requirementList_P2_C2_M2);

		final List<Method> methodList_P2_C2 = new ArrayList<Method>();
		methodList_P2_C2.add(method_P2_C2_M1);
		methodList_P2_C2.add(method_P2_C2_M2);
		
		final Class class_P2_C2 = new Class();
		class_P2_C2.setLocation(10);
		class_P2_C2.setName("class_P2_C2");
		class_P2_C2.setNumber(5);
		class_P2_C2.setSuspiciousValue(0.0);
		class_P2_C2.setMethods(methodList_P2_C2);

		final List<Class> classList_P2 = new ArrayList<Class>();
		classList_P2.add(class_P2_C1);
		classList_P2.add(class_P2_C2);

		final Package package_P2 = new Package();
		package_P2.setName("package_P2");
		package_P2.setNumber(293);
		package_P2.setSuspiciousValue(0.0);
		package_P2.setClasses(classList_P2);

		final List<Package> packageSet = new ArrayList<Package>();
		packageSet.add(package_P1);
		packageSet.add(package_P2);
		
		HierarchicalFaultClassification faultClassification = new HierarchicalFaultClassification();
		faultClassification.setPackages(packageSet);
		List<SuspiciousElement> elements = faultClassification.getSuspiciousElementList();
		
		Assert.assertEquals(11, elements.size());
		
		SuspiciousElement element = elements.get(0);
		Assert.assertEquals("class_P1_C1", element.getName());
		Assert.assertTrue(element.getLocation().equals(1111));
		
		element = elements.get(1);
		Assert.assertEquals("class_P1_C1", element.getName());
		Assert.assertTrue(element.getLocation().equals(1112));

		element = elements.get(2);
		Assert.assertEquals("class_P1_C1", element.getName());
		Assert.assertTrue(element.getLocation().equals(1121));
		
		element = elements.get(3);
		Assert.assertEquals("class_P1_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(1211));
		
		element = elements.get(4);
		Assert.assertEquals("class_P1_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(1212));
		
		element = elements.get(5);
		Assert.assertEquals("class_P1_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(1221));
		
		element = elements.get(6);
		Assert.assertEquals("class_P2_C1", element.getName());
		Assert.assertTrue(element.getLocation().equals(2111));
		
		element = elements.get(7);
		Assert.assertEquals("class_P2_C1", element.getName());
		Assert.assertTrue(element.getLocation().equals(2121));
		
		element = elements.get(8);
		Assert.assertEquals("class_P2_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(2211));
		
		element = elements.get(9);
		Assert.assertEquals("class_P2_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(2212));
		
		element = elements.get(10);
		Assert.assertEquals("class_P2_C2", element.getName());
		Assert.assertTrue(element.getLocation().equals(2221));
	}
	
	
}