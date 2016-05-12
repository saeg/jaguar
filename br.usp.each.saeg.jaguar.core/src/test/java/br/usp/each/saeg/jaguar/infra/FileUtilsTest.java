package br.usp.each.saeg.jaguar.infra;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.utils.FileUtils;

public class FileUtilsTest {
	
	private static final String TEST_LIST_FILE = "./src/test/resources/testClassesNames.txt";

	@Test
	public void findTestClasses() throws ClassNotFoundException{
		Class<?>[] classes = FileUtils.findTestClasses(this.getClass());
		Assert.assertTrue(Arrays.asList(classes).contains(this.getClass()));
	}
	
	@Test
	public void findClassesInFile() throws ClassNotFoundException{
		File testsListFile = new File(TEST_LIST_FILE);
		Class<?>[] classes = FileUtils.getClassesInFile(testsListFile);
		Assert.assertEquals(2, classes.length);
	}
}
