package br.usp.each.saeg.jaguar.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.model.codeforest.Class;
import br.usp.each.saeg.jaguar.model.codeforest.Method;

public class ClassTest {

	@Test
	public void equals(){
		Class class1 = new Class();
		Class class2 = class1;
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		class2 = new Class();
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		class1.setLocation("location");
		class2.setLocation("location");
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		class1.setName("name");
		class2.setName("name");
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		class1.setNumber("number");
		class2.setNumber("number");
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		class1.setSuspiciousValue("suspValue");
		class2.setSuspiciousValue("suspValue");
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		List<Method> methodList = new ArrayList<Method>();
		class1.setMethodList(methodList);
		class2.setMethodList(methodList);
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		List<Method> methodList2 = new ArrayList<Method>();
		class2.setMethodList(methodList2);
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		
		methodList.add(new Method());
		methodList2.add(new Method());
		Assert.assertTrue(class1.equals(class2));
		Assert.assertEquals(class1.hashCode(),class2.hashCode());
		Assert.assertEquals(class1.toString(),class2.toString());
	}
	
	@Test
	public void notEquals(){
		Class class1 = new Class();
		class1.setLocation("location");
		Class class2 = new Class();
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		class2.setLocation("location");
		Assert.assertTrue(class1.equals(class2));
		
		class1.setName("name");
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		class2.setName("name");
		Assert.assertTrue(class1.equals(class2));
		
		class1.setNumber("number");
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		class2.setNumber("number");
		Assert.assertTrue(class1.equals(class2));
		
		class1.setSuspiciousValue("suspValue");
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		class2.setSuspiciousValue("suspValue");
		Assert.assertTrue(class1.equals(class2));
		
		List<Method> methodList = new ArrayList<Method>();
		class1.setMethodList(methodList);
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		class2.setMethodList(methodList);
		Assert.assertTrue(class1.equals(class2));
		
		List<Method> methodList2 = new ArrayList<Method>();
		class2.setMethodList(methodList2);
		Assert.assertTrue(class1.equals(class2));
		
		methodList.add(new Method());
		Assert.assertFalse(class1.equals(class2));
		Assert.assertNotEquals(class1.hashCode(),class2.hashCode());
		
		methodList2.add(new Method());
		Assert.assertTrue(class1.equals(class2));
	}
}
