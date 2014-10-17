package br.usp.each.saeg.jaguar.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.model.codeforest.FaultClassification;
import br.usp.each.saeg.jaguar.core.model.codeforest.Package;
import br.usp.each.saeg.jaguar.core.model.codeforest.TestCriteria;

public class FaultClassificationTest {

	@Test
	public void equals(){
		FaultClassification fc1 = new FaultClassification();
		FaultClassification fc2 = fc1;
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc2 = new FaultClassification();
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc1.setProject("project");
		fc2.setProject("project");
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		TestCriteria tc1 = new TestCriteria();
		fc1.setTestCriteria(tc1);
		fc2.setTestCriteria(tc1);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		TestCriteria tc2 = new TestCriteria();
		fc2.setTestCriteria(tc2);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		tc1.setHeuristicType("heuristicType");
		tc2.setHeuristicType("heuristicType");
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		tc1.setRequirementType("requirementType");
		tc2.setRequirementType("requirementType");
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		Set<Package> packageSet1 = new HashSet<Package>();
		tc1.setPackages(packageSet1);
		tc2.setPackages(packageSet1);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		Set<Package> packagelist2 = new HashSet<Package>();
		tc2.setPackages(packagelist2);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		Package package1 = new Package();
		packageSet1.add(package1);
		packagelist2.add(package1);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		Assert.assertEquals(fc1.toString(),fc2.toString());
	}
	
	@Test
	public void notEquals(){
		FaultClassification fc1 = new FaultClassification();
		FaultClassification fc2 = null;
		
		fc2 = new FaultClassification();
		fc1.setProject("project");
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		fc2.setProject("project");
		TestCriteria tc1 = new TestCriteria();
		fc1.setTestCriteria(tc1);
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		fc2.setTestCriteria(tc1);
		TestCriteria tc2 = new TestCriteria();
		fc2.setTestCriteria(tc2);
		tc1.setHeuristicType("heuristicType");
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		tc2.setHeuristicType("heuristicType");
		tc1.setRequirementType("requirementType");
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		tc2.setRequirementType("requirementType");
		Set<Package> packagelist1 = new HashSet<Package>();
		tc1.setPackages(packagelist1);
		
		tc2.setPackages(packagelist1);
		Set<Package> packagelist2 = new HashSet<Package>();
		tc2.setPackages(packagelist2);
		Package package1 = new Package();
		packagelist1.add(package1);
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
	}
}
