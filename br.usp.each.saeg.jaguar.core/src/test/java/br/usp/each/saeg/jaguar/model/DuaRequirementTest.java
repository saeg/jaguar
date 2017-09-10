package br.usp.each.saeg.jaguar.model;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement.Type;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;


public class DuaRequirementTest {

	private static final String CLASS_NAME1 = "br.usp.each.saeg.jaguar.test1";
	
	private static final int INDEX = 0;
	private static final int INDEX2 = 1;
	
	private static final int DEF = 3;
	private static final int DEF2 = 5;
	
	private static final int USE = 5;
	private static final int USE2 = 6;
	
	private static final int TARGET1 = -1;
	private static final int TARGET2 = 7;
	
	private static final String VAR = "test";

	@Test
	public void increaseCef(){
		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
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
		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
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
		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);

		Assert.assertEquals(CLASS_NAME1 , requirement.getClassName());
	}
	
	@Test
	public void def(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertEquals(DEF , requirement.getDef());
	}
	
	@Test
	public void use(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertEquals(USE , requirement.getUse());
	}
	
	@Test
	public void target1(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertEquals(TARGET1 , requirement.getTarget());
	}
	
	@Test
	public void var(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertEquals(VAR, requirement.getVar());
	}
	
	@Test
	public void setClassName(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setClassName(CLASS_NAME1+" ");
		Assert.assertEquals(CLASS_NAME1+" ", requirement.getClassName());
	}
	
	@Test
	public void setDEF(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setDef(DEF2);
		Assert.assertEquals(DEF2, requirement.getDef());
	}
	
	@Test
	public void setUSE(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setUse(USE2);
		Assert.assertEquals(USE2, requirement.getUse());
	}
	
	@Test
	public void setTARGET(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setTarget(TARGET2);
		Assert.assertEquals(TARGET2, requirement.getTarget());
	}
	
	@Test
	public void setVAR(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setVar(VAR + " ");
		Assert.assertEquals(VAR + " ", requirement.getVar());
	}
	
	@Test
	public void getType(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertEquals(Type.DUA, requirement.getType());
	}	
	
	@Test
	public void suspiciousness(){
		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		requirement.setSuspiciousness(1);
		Assert.assertEquals(1 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0.555);
		Assert.assertEquals(0.555 , requirement.getSuspiciousness(), 0.001);
		
		requirement.setSuspiciousness(0);
		Assert.assertEquals(0 , requirement.getSuspiciousness(), 0.001);
	}

	@Test
	public void notEqualsClassNameNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1+" ", INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsDEFNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX2, DEF2,USE,TARGET1,VAR);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsUSENotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE2,TARGET1,VAR);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsTARGETNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET2,VAR);
		Assert.assertFalse(requirement1.equals(requirement2));
	}
	
	@Test
	public void notEqualsVARNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR+" ");
		Assert.assertFalse(requirement1.equals(requirement2));
	}

	@Test
	public void notEqualsNull(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		Assert.assertFalse(requirement1.equals(null));
	}

	@Test
	public void notEqualsClassNameNull(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(null, INDEX, DEF,USE,TARGET1,VAR);
		
		Assert.assertFalse(requirement1.equals(requirement2));
		Assert.assertFalse(requirement2.equals(requirement1));
		
	}
	
	@Test
	public void notEqualsVARNull(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, null);
		Assert.assertFalse(requirement1.equals(requirement2));
		Assert.assertFalse(requirement2.equals(requirement1));
	}
	
	@Test
	public void equals1(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		requirement1.increaseFailed();
		requirement1.increasePassed();
		requirement1.setSuspiciousness(1);
		Assert.assertTrue(requirement1.equals(requirement2));
	}
	
	@Test
	public void equals2(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		Assert.assertTrue(requirement1.equals(requirement1));
	}
	
	@Test
	public void hashCodeNotEqualsClassNameNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1+" ", INDEX, DEF, USE, TARGET1, VAR);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsDEFNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX2, DEF2, USE, TARGET1, VAR);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsUSENotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE2, TARGET1, VAR);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsTARGETNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET2, VAR);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsVARNotEqual(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR+" ");
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeEquals(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF, USE, TARGET1, VAR);
		Assert.assertEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
	@Test
	public void hashCodeNotEqualsVARNull(){
		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,VAR);
		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, INDEX, DEF,USE,TARGET1,null);
		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
	}
	
}
