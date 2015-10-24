package br.usp.each.saeg.jaguar.codefores.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

public class FaultClassificationTest {

	@Test
	public void equals(){
		HierarchicalFaultClassification fc1 = new HierarchicalFaultClassification();
		HierarchicalFaultClassification fc2 = fc1;
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc1 = new HierarchicalFaultClassification();
		fc2 = new HierarchicalFaultClassification();
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc1.setProject("project");
		fc2.setProject("project");
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc1.setHeuristic("heuristicType");
		fc2.setHeuristic("heuristicType");
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		fc1.setRequirementType(Type.LINE);
		fc2.setRequirementType(Type.LINE);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		Set<Package> packageSet1 = new HashSet<Package>();
		fc1.setPackages(packageSet1);
		fc2.setPackages(packageSet1);
		Assert.assertTrue(fc1.equals(fc2));
		Assert.assertEquals(fc1.hashCode(),fc2.hashCode());
		
		Set<Package> packagelist2 = new HashSet<Package>();
		fc2.setPackages(packagelist2);
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
		HierarchicalFaultClassification fc1 = new HierarchicalFaultClassification();
		HierarchicalFaultClassification fc2 = null;
		
		fc2 = new HierarchicalFaultClassification();
		fc1.setProject("project");
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		fc2.setProject("project");
		fc1.setHeuristic("heuristicType");
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
	 	fc2.setHeuristic("heuristicType");
		fc1.setRequirementType(Type.LINE);
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
		
		fc2.setRequirementType(Type.LINE);
		Set<Package> packagelist1 = new HashSet<Package>();
		fc1.setPackages(packagelist1);
		
		fc2.setPackages(packagelist1);
		Set<Package> packagelist2 = new HashSet<Package>();
		fc2.setPackages(packagelist2);
		Package package1 = new Package();
		packagelist1.add(package1);
		Assert.assertFalse(fc1.equals(fc2));
		Assert.assertNotEquals(fc1.hashCode(),fc2.hashCode());
	}
}
