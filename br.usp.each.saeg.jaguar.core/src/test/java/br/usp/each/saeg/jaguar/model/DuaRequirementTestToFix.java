package br.usp.each.saeg.jaguar.model;


public class DuaRequirementTestToFix {

	
//	private static final String CLASS_NAME1 = "br.usp.each.saeg.jaguar.test1";
//	
//	private static final Set<Integer> DEF = new TreeSet<Integer>(Arrays.asList(new Integer[]{1,2,3}));
//	private static final Set<Integer> DEF2 = new TreeSet<Integer>(Arrays.asList(new Integer[]{1,2}));
//	
//	private static final Set<Integer> USE = new TreeSet<Integer>(Arrays.asList(new Integer[]{4,5,6}));
//	private static final Set<Integer> USE2 = new TreeSet<Integer>(Arrays.asList(new Integer[]{4,5}));
//	
//	private static final Set<Integer> TARGET1 = new TreeSet<Integer>(Arrays.asList(new Integer[]{4,5,6}));
//	private static final Set<Integer> TARGET2 = new TreeSet<Integer>(Arrays.asList(new Integer[]{-1}));
//	private static final String VAR = "test";
//	
	

//	@Test
//	public void increaseCef(){
//		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(0, requirement.getCef());
//		
//		requirement.increaseFailed();
//		Assert.assertEquals(1 , requirement.getCef());
//
//		requirement.increasePassed();
//		Assert.assertEquals(1 , requirement.getCef());
//		
//		requirement.increaseFailed();
//		requirement.increaseFailed();
//		Assert.assertEquals(3 , requirement.getCef());
//	}
//	
//	@Test
//	public void increaseCep(){
//		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(0, requirement.getCep());
//		
//		requirement.increasePassed();
//		Assert.assertEquals(1 , requirement.getCep());
//
//		requirement.increaseFailed();
//		Assert.assertEquals(1 , requirement.getCep());
//		
//		requirement.increasePassed();
//		requirement.increasePassed();
//		Assert.assertEquals(3 , requirement.getCep());
//	}
//	
//	@Test
//	public void className(){
//		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//
//		Assert.assertEquals(CLASS_NAME1 , requirement.getClassName());
//	}
//	
//	@Test
//	public void def(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(DEF , requirement.getDef());
//	}
//	
//	@Test
//	public void use(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(USE , requirement.getUse());
//	}
//	
//	@Test
//	public void target1(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(TARGET1 , requirement.getTarget());
//	}
//	
//	@Test
//	public void var(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(VAR, requirement.getVar());
//	}
//	
//	@Test
//	public void setClassName(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setClassName(CLASS_NAME1+" ");
//		Assert.assertEquals(CLASS_NAME1+" ", requirement.getClassName());
//	}
//	
//	@Test
//	public void setDEF(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setDef(DEF2);
//		Assert.assertEquals(DEF2, requirement.getDef());
//	}
//	
//	@Test
//	public void setUSE(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setUse(USE2);
//		Assert.assertEquals(USE2, requirement.getUse());
//	}
//	
//	@Test
//	public void setTARGET(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setTarget(TARGET2);
//		Assert.assertEquals(TARGET2, requirement.getTarget());
//	}
//	
//	@Test
//	public void setVAR(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setVar(VAR + " ");
//		Assert.assertEquals(VAR + " ", requirement.getVar());
//	}
//	
//	@Test
//	public void getType(){
//		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertEquals(Type.DUA, requirement.getType());
//	}
//	
//	
//	
//	
//	@Test
//	public void suspiciousness(){
//		AbstractTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement.setSuspiciousness(1);
//		Assert.assertEquals(1 , requirement.getSuspiciousness(), 0.001);
//		
//		requirement.setSuspiciousness(0.555);
//		Assert.assertEquals(0.555 , requirement.getSuspiciousness(), 0.001);
//		
//		requirement.setSuspiciousness(0);
//		Assert.assertEquals(0 , requirement.getSuspiciousness(), 0.001);
//	}
////	
////	@Test
////	public void lineNumber(){
////		DuaTestRequirement requirement = new DuaTestRequirement(CLASS_NAME1, 1000);
////		requirement.increaseFailed();
////		requirement.increasePassed();
////		requirement.setSuspiciousness(1);
////		Assert.assertEquals(new Integer(1000), requirement.getLineNumber());
////	}
////	
//	@Test
//	public void notEqualsClassNameNotEqual(){
//		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1+" ", DEF,USE,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//	}
//	
//	@Test
//	public void notEqualsDEFNotEqual(){
//		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF2,USE,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//	}
//	
//	@Test
//	public void notEqualsUSENotEqual(){
//		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE2,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//	}
//	
//	@Test
//	public void notEqualsTARGETNotEqual(){
//		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET2,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//	}
//	
//	@Test
//	public void notEqualsVARNotEqual(){
//		DuaTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		DuaTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR+" ");
//		Assert.assertFalse(requirement1.equals(requirement2));
//	}
//	
//	
//	
////	
////	@Test
////	public void notEqualsLineNumberNotEqual(){
////		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, 1000);
////		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, 1001);
////		Assert.assertFalse(requirement1.equals(requirement2));
////	}
////	
//	@Test
//	public void notEqualsNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(null));
//	}
////	
//	@Test
//	public void notEqualsClassNameNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(null, DEF,USE,TARGET1,VAR);
//		
//		Assert.assertFalse(requirement1.equals(requirement2));
//		Assert.assertFalse(requirement2.equals(requirement1));
//		
//	}
//	
//	@Test
//	public void notEqualsDEFNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, null,USE,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//		Assert.assertFalse(requirement2.equals(requirement1));
//	}
//	
//	@Test
//	public void notEqualsUSENull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,null,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//		Assert.assertFalse(requirement2.equals(requirement1));
//	}
//	
//	@Test
//	public void notEqualsTARGETNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,null,VAR);
//		Assert.assertFalse(requirement1.equals(requirement2));
//		Assert.assertFalse(requirement2.equals(requirement1));
//	}
//	
//	@Test
//	public void notEqualsVARNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,null);
//		Assert.assertFalse(requirement1.equals(requirement2));
//		Assert.assertFalse(requirement2.equals(requirement1));
//	}
//	
////	
////	@Test
////	public void notEqualsLineNumberNull(){
////		AbstractTestRequirement requirement1 = new LineTestRequirement(CLASS_NAME1, null);
////		AbstractTestRequirement requirement2 = new LineTestRequirement(CLASS_NAME1, 1000);
////		Assert.assertFalse(requirement1.equals(requirement2));
////	}
////	
//	@Test
//	public void notEqualsDifferentClass(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertFalse(requirement1.equals(new Integer(0)));
//	}
//	
//	@Test
//	public void equals1(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		requirement1.increaseFailed();
//		requirement1.increasePassed();
//		requirement1.setSuspiciousness(1);
//		Assert.assertTrue(requirement1.equals(requirement2));
//	}
//	
//	@Test
//	public void equals2(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		Assert.assertTrue(requirement1.equals(requirement1));
//	}
//	
//	@Test
//	public void hashCodeNotEqualsClassNameNotEqual(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1+" ", DEF,USE,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeNotEqualsDEFNotEqual(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF2,USE,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeNotEqualsUSENotEqual(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE2,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeNotEqualsTARGETNotEqual(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET2,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeNotEqualsVARNotEqual(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR+" ");
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeEquals(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//
//		Assert.assertEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeClassNameNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(null, DEF,USE,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeDEFNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, null,USE,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeUSENull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,null,TARGET1,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeTARGETNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,null,VAR);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	@Test
//	public void hashCodeVARNull(){
//		AbstractTestRequirement requirement1 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,VAR);
//		AbstractTestRequirement requirement2 = new DuaTestRequirement(CLASS_NAME1, DEF,USE,TARGET1,null);
//		Assert.assertNotEquals(requirement1.hashCode(), requirement2.hashCode());
//	}
//	
//	
	
	
	
}
