package br.usp.each.saeg.jaguar.model;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class LineRequirementTest {

	
	private static final String CLASS_NAME1 = "br.usp.each.saeg.jaguar.test1";

	@Test
	public void increaseCef(){
		AbstractTestRequirement requirement = new LineTestRequirement(CLASS_NAME1, 10);
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
		AbstractTestRequirement requirement = new LineTestRequirement(CLASS_NAME1, 10);
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
		AbstractTestRequirement requirement = new LineTestRequirement(CLASS_NAME1, 10);
		requirement.increaseFailed();
		requirement.increasePassed();
		requirement.setSuspiciousness(1);
		Assert.assertEquals(CLASS_NAME1 , requirement.getClassName());
	}
	
	@Test
	public void suspiciousness(){
		AbstractTestRequirement requirement = new LineTestRequirement(CLASS_NAME1, 10);
		requirement.setSuspiciousness(1);
		Assert.assertEquals(1 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0.555);
		Assert.assertEquals(0.555 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0);
		Assert.assertEquals(0 , requirement.getSuspiciousness(), 0.001);
	}
	
	@Test
	public void lineNumber(){
		LineTestRequirement requirement = new LineTestRequirement(CLASS_NAME1, 1000);
		requirement.increaseFailed();
		requirement.increasePassed();
		requirement.setSuspiciousness(1);
		Assert.assertEquals(new Integer(1000), requirement.getLineNumber());
	}
	
	@Test
	public void notEqualsClassNameNotEqual(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1 + " ", 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsLineNumberNotEqual(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1001);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsNull(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(null));
	}
	
	@Test
	public void notEqualsClassNameNull(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(null, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsLineNumberNull(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, null);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1000);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void equals1(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1000);
		requirement1.increaseFailed();
		requirement1.increasePassed();
		requirement1.setSuspiciousness(1);
		Assert.assertTrue(requirement1.equals(requirement2));
	}
	
	@Test
	public void equals2(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		Assert.assertTrue(requirement1.equals(requirement1));
	}
	
	@Test
	public void hashCodeNotEqualsClassNameNotEqual(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1 + " ", 1000);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsLineNumberNotEqual(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1001);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeEquals(){
		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, 1000);
		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1000);
		requirement1.increaseFailed();
		requirement1.increasePassed();
		requirement1.setSuspiciousness(1);
		Assert.assertEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
}
