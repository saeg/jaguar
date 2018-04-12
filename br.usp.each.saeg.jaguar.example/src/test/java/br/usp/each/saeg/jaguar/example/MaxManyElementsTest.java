package br.usp.each.saeg.jaguar.example;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.example.Max;

/**
 * Unit test for Max.
 */
public class MaxManyElementsTest {

	@Test
	public void test1() {
		int expected = 3;
		int actual = Max.max(new int[] { 1, 2, 3 });
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test2() {
		int expected = 6;
		int actual = Max.max(new int[] { 5, 5, 6 });
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test3() {
		int expected = 10;
		int actual = Max.max(new int[] { 2, 1, 10 });
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test4() {
		int expected = 4;
		int actual = Max.max(new int[] { 4, 3, 2 });
		Assert.assertEquals(expected, actual);
	}
	
}
