package br.usp.each.saeg.jaguar.example;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.example.Max;

/**
 * Unit test for Max.
 */
public class MaxOneElementTest {

	@Test
	public void test5() {
		int expected = 4;
		int actual = Max.max(new int[] { 4 });
		Assert.assertEquals(expected, actual);
	}
	
}
