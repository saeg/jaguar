package br.usp.each.saeg.jaguar.infra;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.utils.FileUtils;

public class FileUtilsTest {
	
	@SuppressWarnings("rawtypes")
	@Test
	public void findTestClasses() throws ClassNotFoundException{
		Class[] classes = FileUtils.findTestClasses(this.getClass());
		Assert.assertTrue(Arrays.asList(classes).contains(this.getClass()));
	}
}
