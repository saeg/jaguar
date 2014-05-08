package br.usp.each.saeg.jaguar.infra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {
	
	@Test
	public void findTestClasses() throws ClassNotFoundException{
		Class[] classes = FileUtils.findTestClasses(this.getClass());
		Assert.assertTrue(Arrays.asList(classes).contains(this.getClass()));
	}
}
