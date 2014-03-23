package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class OpTest {

	Calculator calculator = new Calculator();


	@Test
	public void testCalculateOpAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOp(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOp(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOp(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOp(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOp(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepHaveValueMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateOp(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOp(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefCnfHaveValues() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOp(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = -0.333;
		double actualSusp = calculator.calculateOp(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOp(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepNotHaveValue() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOp(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateOp(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCnfNotHaveValue() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateOp(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateOp(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCnpNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateOp(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefHaveBiggestValue() {
		double expectedSusp = 5;
		double actualSusp = calculator.calculateOp(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepHaveBiggestValue() {
		double expectedSusp = 0.285;
		double actualSusp = calculator.calculateOp(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpAllCoeficientsHaveEqualValues() {
		double expectedSusp = 1.5;
		double actualSusp = calculator.calculateOp(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 9.75;
		double actualSusp = calculator.calculateOp(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateOp(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefMoreFrequentlyExecuted() {
		double expectedSusp = 10;
		double actualSusp = calculator.calculateOp(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 9.5;
		double actualSusp = calculator.calculateOp(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefMoreExecutedThanCep() {
		double expectedSusp = 9.333;
		double actualSusp = calculator.calculateOp(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefMoreExecutedThanCep2() {
		double expectedSusp = 9.25;
		double actualSusp = calculator.calculateOp(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefCepEqualExecuted() {
		double expectedSusp = 9.090;
		double actualSusp = calculator.calculateOp(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepMoreExecutedThanCef() {
		double expectedSusp = 9.083;
		double actualSusp = calculator.calculateOp(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefNotExecuted() {
		double expectedSusp = -0.625;
		double actualSusp = calculator.calculateOp(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 9.015;
		double actualSusp = calculator.calculateOp(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 99.980;
		double actualSusp = calculator.calculateOp(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOpCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 11.834;
		double actualSusp = calculator.calculateOp(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
