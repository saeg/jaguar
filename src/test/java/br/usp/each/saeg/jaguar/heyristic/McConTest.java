package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class McConTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateMcConAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateMcCon(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefCnfHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMcCon(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = -1;
		double actualSusp = calculator.calculateMcCon(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateMcCon(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMcCon(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMcCon(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCnfNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMcCon(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConAllCoeficientsHaveValueOne() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCnpNotHaveValue() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMcCon(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateMcCon(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepHaveBiggestValue() {
		double expectedSusp = -0.583;
		double actualSusp = calculator.calculateMcCon(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.066;
		double actualSusp = calculator.calculateMcCon(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.575;
		double actualSusp = calculator.calculateMcCon(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = -0.659;
		double actualSusp = calculator.calculateMcCon(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		double actualSusp = calculator.calculateMcCon(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.742;
		double actualSusp = calculator.calculateMcCon(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefMoreExecutedThanCep() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateMcCon(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefMoreExecutedThanCep2() {
		double expectedSusp = 0.769;
		double actualSusp = calculator.calculateMcCon(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMcCon(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepMoreExecutedThanCef() {
		double expectedSusp = 0.476;
		double actualSusp = calculator.calculateMcCon(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefNotExecuted() {
		double expectedSusp = -1;
		double actualSusp = calculator.calculateMcCon(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = -0.323;
		double actualSusp = calculator.calculateMcCon(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.778;
		double actualSusp = calculator.calculateMcCon(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMcConCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = -0.339;
		double actualSusp = calculator.calculateMcCon(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
