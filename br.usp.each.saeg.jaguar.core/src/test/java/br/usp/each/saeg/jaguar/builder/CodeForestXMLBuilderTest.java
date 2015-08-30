package br.usp.each.saeg.jaguar.builder;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;
import br.usp.each.saeg.jaguar.core.output.xml.hierarchical.HierarchicalXmlBuilder;

public class CodeForestXMLBuilderTest {

	private static final String PROJECT_NAME = "projeto";
	private static final Type REQUIREMENT_TYPE = Type.LINE;
	private static final String CLASS_NAME1 = "br.usp.each.saeg.jaguar.class1";
	private static final Integer CLASS_FIRST_LINE = 5;
	private static final Integer METHOD_ID = 0;
	private static final Integer METHOD_FIRST_LINE = 8;
	private static final String METHOD_SIGNATURE = "main()";
	private static final Double SUSPICIOUSNESS = 0.91;
	private static final Integer REQUIREMENT_LINE_NUMBER = 10;
	
	private HierarchicalXmlBuilder createSimpleXmlBuilder(){
		HierarchicalXmlBuilder xml = new HierarchicalXmlBuilder();
		xml.project(PROJECT_NAME);
		xml.requirementType(REQUIREMENT_TYPE);
		xml.heuristic(new TarantulaHeuristic());
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, REQUIREMENT_LINE_NUMBER);
		requirement1.setClassFirstLine(CLASS_FIRST_LINE);
		requirement1.setMethodId(METHOD_ID);
		requirement1.setMethodLine(METHOD_FIRST_LINE);
		requirement1.setMethodSignature(METHOD_SIGNATURE);
		requirement1.setSuspiciousness(SUSPICIOUSNESS);
		xml.addTestRequirement(requirement1);
		return xml;
	}

	@Test
	public void simple() {
		HierarchicalFaultClassification simpleXml = createSimpleXmlBuilder().build();
		
		// Assert equals
		// Project
		Assert.assertEquals(PROJECT_NAME, simpleXml.getProject());

		// TestCriteria
		Assert.assertEquals(REQUIREMENT_TYPE, simpleXml.getTestCriteria().getRequirementType());
		Assert.assertEquals("TARANTULA", simpleXml.getTestCriteria().getHeuristicType());

		// Package
		Assert.assertEquals(1, simpleXml.getTestCriteria().getPackages().size());
		Package package1 = simpleXml.getTestCriteria().getPackages().iterator().next();
		Assert.assertEquals(new Integer(1), package1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, package1.getSuspiciousValue());
		Assert.assertEquals(null, package1.getLocation());

		// Class
		Assert.assertEquals(1, package1.getClasses().size());
		Class class1 = package1.getClasses().iterator().next();
		Assert.assertEquals(new Integer(1), class1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, class1.getSuspiciousValue());
		Assert.assertEquals(CLASS_FIRST_LINE, class1.getLocation());
		Assert.assertEquals(CLASS_NAME1, class1.getName());

		// Method
		Assert.assertEquals(1, class1.getMethods().size());
		Method method1 = class1.getMethods().iterator().next();
		Assert.assertEquals(new Integer(1), method1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getSuspiciousValue());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getMethodsusp());
		Assert.assertEquals(METHOD_FIRST_LINE, method1.getLocation());
		Assert.assertEquals(METHOD_SIGNATURE, method1.getName());
		Assert.assertEquals(METHOD_ID, method1.getId());
		Assert.assertEquals(new Integer(1), method1.getPosition());

		// Requirement
		Assert.assertEquals(1, method1.getRequirements().size());
		Requirement requirementExpected1 = method1.getRequirements().iterator().next();
		Assert.assertEquals(REQUIREMENT_LINE_NUMBER.toString(), requirementExpected1.getName());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected1.getSuspiciousValue());
		Assert.assertEquals(REQUIREMENT_LINE_NUMBER, requirementExpected1.getLocation());
	}

	@Test
	@Ignore //TODO teste falha dependendo da versao do java
	public void complex() {
		HierarchicalXmlBuilder xmlBuilder = createSimpleXmlBuilder();
		// variaveis
		String className2 = CLASS_NAME1 + 1;
		String className3 = "br.usp.each.saeg.jaguar.core.element";

		// Requisitos
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 9);
		requirement2.setClassFirstLine(CLASS_FIRST_LINE);
		requirement2.setMethodId(METHOD_ID);
		requirement2.setMethodLine(METHOD_FIRST_LINE);
		requirement2.setMethodSignature(METHOD_SIGNATURE);
		requirement2.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement2);

		AbstractTestRequirement requirement3 = new LineTestRequirement(CLASS_NAME1, 21);
		requirement3.setClassFirstLine(CLASS_FIRST_LINE);
		requirement3.setMethodId(1);
		requirement3.setMethodLine(21);
		requirement3.setMethodSignature("calc()");
		requirement3.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement3);

		AbstractTestRequirement requirement4 = new LineTestRequirement(className2, 5);
		requirement4.setClassFirstLine(3);
		requirement4.setMethodId(0);
		requirement4.setMethodLine(5);
		requirement4.setMethodSignature("order()");
		requirement4.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement4);

		AbstractTestRequirement requirement5 = new LineTestRequirement(className3, 13);
		requirement5.setClassFirstLine(10);
		requirement5.setMethodId(0);
		requirement5.setMethodLine(13);
		requirement5.setMethodSignature("clean()");
		requirement5.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement5);

		AbstractTestRequirement requirement6 = new LineTestRequirement(className3, 14);
		requirement6.setClassFirstLine(10);
		requirement6.setMethodId(0);
		requirement6.setMethodLine(13);
		requirement6.setMethodSignature("clean()");
		requirement6.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement6);

		AbstractTestRequirement requirement7 = new LineTestRequirement(className3, 15);
		requirement7.setClassFirstLine(10);
		requirement7.setMethodId(0);
		requirement7.setMethodLine(13);
		requirement7.setMethodSignature("clean()");
		requirement7.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement7);

		AbstractTestRequirement requirement8 = new LineTestRequirement(className3, 16);
		requirement8.setClassFirstLine(10);
		requirement8.setMethodId(0);
		requirement8.setMethodLine(13);
		requirement8.setMethodSignature("clean()");
		requirement8.setSuspiciousness(SUSPICIOUSNESS);
		xmlBuilder.addTestRequirement(requirement8);

		HierarchicalFaultClassification complexXml = xmlBuilder.build();

		// Assert equals
		// Project
		Assert.assertEquals(PROJECT_NAME, complexXml.getProject());

		// TestCriteria
		Assert.assertEquals(REQUIREMENT_TYPE, complexXml.getTestCriteria().getRequirementType());
		Assert.assertEquals("TARANTULA", complexXml.getTestCriteria().getHeuristicType());

		// Package 1
		Assert.assertEquals(2, complexXml.getTestCriteria().getPackages().size());
		Iterator<Package> packageIterator = complexXml.getTestCriteria().getPackages().iterator();
		Package package1 = packageIterator.next();
		Assert.assertEquals(new Integer(4), package1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, package1.getSuspiciousValue());
		Assert.assertEquals(null, package1.getLocation());

		// Class 3
		Assert.assertEquals(1, package1.getClasses().size());
		Class class3 = package1.getClasses().iterator().next();
		Assert.assertEquals(new Integer(4), class3.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, class3.getSuspiciousValue());
		Assert.assertEquals(new Integer(10), class3.getLocation());
		Assert.assertEquals(className3, class3.getName());

		// Method 1
		Assert.assertEquals(1, class3.getMethods().size());
		Method method4 = class3.getMethods().iterator().next();
		Assert.assertEquals(new Integer(4), method4.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, method4.getSuspiciousValue());
		Assert.assertEquals(SUSPICIOUSNESS, method4.getMethodsusp());
		Assert.assertEquals(new Integer(13), method4.getLocation());
		Assert.assertEquals("clean()", method4.getName());
		Assert.assertEquals(new Integer(0), method4.getId());
		Assert.assertEquals(new Integer(4), method4.getPosition());

		// Requirement 5
		Assert.assertEquals(4, method4.getRequirements().size());
		Iterator<? extends Requirement> requirementIterator = method4.getRequirements().iterator();
		Requirement requirementExpected5 = requirementIterator.next();
		Assert.assertEquals("13", requirementExpected5.getName());
		Assert.assertEquals(new Integer(13), requirementExpected5.getLocation());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected5.getSuspiciousValue());

		// Requirement 6
		Assert.assertEquals(4, method4.getRequirements().size());
		Requirement requirementExpected6 = requirementIterator.next();
		Assert.assertEquals("14", requirementExpected6.getName());
		Assert.assertEquals(new Integer(14), requirementExpected6.getLocation());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected6.getSuspiciousValue());

		// Requirement 7
		Assert.assertEquals(4, method4.getRequirements().size());
		Requirement requirementExpected7 = requirementIterator.next();
		Assert.assertEquals("15", requirementExpected7.getName());
		Assert.assertEquals(new Integer(15), requirementExpected7.getLocation());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected7.getSuspiciousValue());

		// Requirement 8
		Assert.assertEquals(4, method4.getRequirements().size());
		Requirement requirementExpected8 = requirementIterator.next();
		Assert.assertEquals("16", requirementExpected8.getName());
		Assert.assertEquals(new Integer(16), requirementExpected8.getLocation());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected8.getSuspiciousValue());

		// Package 2
		Package package2 = packageIterator.next();
		Assert.assertEquals(new Integer(4), package2.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, package2.getSuspiciousValue());
		Assert.assertEquals(null, package2.getLocation());

		// Class 1
		Assert.assertEquals(2, package2.getClasses().size());
		Iterator<Class> classIterator = package2.getClasses().iterator();
		Class class1 = classIterator.next();
		Assert.assertEquals(new Integer(3), class1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, class1.getSuspiciousValue());
		Assert.assertEquals(CLASS_FIRST_LINE, class1.getLocation());
		Assert.assertEquals(CLASS_NAME1, class1.getName());

		// Method 1
		Assert.assertEquals(2, class1.getMethods().size());
		Iterator<Method> methodIterator = class1.getMethods().iterator();
		Method method1 = methodIterator.next();
		Assert.assertEquals(new Integer(2), method1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getSuspiciousValue());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getMethodsusp());
		Assert.assertEquals(METHOD_FIRST_LINE, method1.getLocation());
		Assert.assertEquals(METHOD_SIGNATURE, method1.getName());
		Assert.assertEquals(METHOD_ID, method1.getId());
		Assert.assertEquals(new Integer(1), method1.getPosition());

		// Requirement 1
		Assert.assertEquals(2, method1.getRequirements().size());
		requirementIterator = method1.getRequirements().iterator();
		Requirement requirementExpected1 = requirementIterator.next();
		Assert.assertEquals(REQUIREMENT_LINE_NUMBER.toString(), requirementExpected1.getName());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected1.getSuspiciousValue());
		Assert.assertEquals(REQUIREMENT_LINE_NUMBER, requirementExpected1.getLocation());

		// Requirement 2
		Assert.assertEquals(2, method1.getRequirements().size());
		Requirement requirementExpected2 = requirementIterator.next();
		Assert.assertEquals("9", requirementExpected2.getName());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected2.getSuspiciousValue());
		Assert.assertEquals(new Integer(9), requirementExpected2.getLocation());

		// Method 2
		Assert.assertEquals(2, class1.getMethods().size());
		Method method2 = methodIterator.next();
		Assert.assertEquals(new Integer(1), method2.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, method2.getSuspiciousValue());
		Assert.assertEquals(SUSPICIOUSNESS, method2.getMethodsusp());
		Assert.assertEquals(new Integer(21), method2.getLocation());
		Assert.assertEquals("calc()", method2.getName());
		Assert.assertEquals(new Integer(1), method2.getId());
		Assert.assertEquals(new Integer(2), method2.getPosition());

		// Requirement 3
		Assert.assertEquals(1, method2.getRequirements().size());
		Requirement requirementExpected3 = method2.getRequirements().iterator().next();
		Assert.assertEquals("21", requirementExpected3.getName());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected3.getSuspiciousValue());
		Assert.assertEquals(new Integer(21), requirementExpected3.getLocation());

		// Class 2
		Assert.assertEquals(2, package2.getClasses().size());
		Class class2 = classIterator.next();
		Assert.assertEquals(new Integer(1), class2.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, class2.getSuspiciousValue());
		Assert.assertEquals(new Integer(3), class2.getLocation());
		Assert.assertEquals(className2, class2.getName());

		// Method 1
		Assert.assertEquals(1, class2.getMethods().size());
		method1 = class2.getMethods().iterator().next();
		Assert.assertEquals(new Integer(1), method1.getNumber());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getSuspiciousValue());
		Assert.assertEquals(SUSPICIOUSNESS, method1.getMethodsusp());
		Assert.assertEquals(new Integer(5), method1.getLocation());
		Assert.assertEquals("order()", method1.getName());
		Assert.assertEquals(new Integer(0), method1.getId());
		Assert.assertEquals(new Integer(3), method1.getPosition());

		// Requirement 4
		Assert.assertEquals(1, method1.getRequirements().size());
		Requirement requirementExpected4 = method1.getRequirements().iterator().next();
		Assert.assertEquals("5", requirementExpected4.getName());
		Assert.assertEquals(SUSPICIOUSNESS, requirementExpected4.getSuspiciousValue());
		Assert.assertEquals(new Integer(5), requirementExpected4.getLocation());
	}
}
