package br.usp.each.saeg.jaguar.core;

import org.junit.Assert;
import org.junit.Test;

public class RequirementTest {

	
	private static final String CLASS_NAME1 = "br.usp.each.saeg.jaguar.test1";

	@Test
	public void increaseCef(){
		TestRequirement requirement = new TestRequirement(CLASS_NAME1, 10);
		Assert.assertEquals(0, requirement.getCef());
		
		requirement.increaseFailed();
		Assert.assertEquals(1 , requirement.getCef());

		requirement.increasePassed();
		Assert.assertEquals(1 , requirement.getCef());
		
		requirement.increaseFailed();
		requirement.increaseFailed();
		Assert.assertEquals(3 , requirement.getCef());
	}
	
	@Test
	public void increaseCep(){
		TestRequirement requirement = new TestRequirement(CLASS_NAME1, 10);
		Assert.assertEquals(0, requirement.getCep());
		
		requirement.increasePassed();
		Assert.assertEquals(1 , requirement.getCep());

		requirement.increaseFailed();
		Assert.assertEquals(1 , requirement.getCep());
		
		requirement.increasePassed();
		requirement.increasePassed();
		Assert.assertEquals(3 , requirement.getCep());
	}
	
	@Test
	public void className(){
		TestRequirement requirement = new TestRequirement(CLASS_NAME1, 10);
		requirement.increaseFailed();
		requirement.increasePassed();
		requirement.setSuspiciousness(1);
		Assert.assertEquals(CLASS_NAME1 , requirement.getClassName());
	}
	
	@Test
	public void suspiciousness(){
		TestRequirement requirement = new TestRequirement(CLASS_NAME1, 10);
		requirement.setSuspiciousness(1);
		Assert.assertEquals(1 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0.555);
		Assert.assertEquals(0.555 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0);
		Assert.assertEquals(0 , requirement.getSuspiciousness(), 0.001);
	}
	
	@Test
	public void lineNumber(){
		TestRequirement requirement = new TestRequirement(CLASS_NAME1, 1000);
		requirement.increaseFailed();
		requirement.increasePassed();
		requirement.setSuspiciousness(1);
		Assert.assertEquals(new Integer(1000), requirement.getLineNumber());
	}
	
	@Test
	public void notEqualsClassNameNotEqual(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1 + " ", 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsLineNumberNotEqual(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1001);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsNull(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(null));
	}
	
	@Test
	public void notEqualsClassNameNull(){
		TestRequirement requirement1 = new TestRequirement(null, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsLineNumberNull(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, null);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsDifferentClass(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(new Integer(0)));
	}
	
	@Test
	public void equals1(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1000);
		requirement1.increaseFailed();
		requirement1.increasePassed();
		requirement1.setSuspiciousness(1);
		Assert.assertTrue(requirement1.equals(requirement2));
	}
	
	@Test
	public void equals2(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		Assert.assertTrue(requirement1.equals(requirement1));
	}
	
	@Test
	public void hashCodeNotEqualsClassNameNotEqual(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1 + " ", 1000);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsLineNumberNotEqual(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1001);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeEquals(){
		TestRequirement requirement1 = new TestRequirement(CLASS_NAME1, 1000);
		TestRequirement requirement2 = new TestRequirement(CLASS_NAME1, 1000);
		requirement1.increaseFailed();
		requirement1.increasePassed();
		requirement1.setSuspiciousness(1);
		Assert.assertEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
}
