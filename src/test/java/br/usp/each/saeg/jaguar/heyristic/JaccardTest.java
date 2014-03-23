package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class JaccardTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateJaccardAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateJaccard(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnfHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateJaccard(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateJaccard(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateJaccard(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateJaccard(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateJaccard(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateJaccard(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnpNotHaveValue() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateJaccard(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateJaccard(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepHaveBiggestValue() {
		double expectedSusp = 0.111;
		double actualSusp = calculator.calculateJaccard(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateJaccard(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.625;
		double actualSusp = calculator.calculateJaccard(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.071;
		double actualSusp = calculator.calculateJaccard(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		double actualSusp = calculator.calculateJaccard(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.769;
		double actualSusp = calculator.calculateJaccard(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCep() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateJaccard(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCep2() {
		double expectedSusp = 0.769;
		double actualSusp = calculator.calculateJaccard(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateJaccard(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepMoreExecutedThanCef() {
		double expectedSusp = 0.476;
		double actualSusp = calculator.calculateJaccard(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefNotExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateJaccard(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.00985;
		double actualSusp = calculator.calculateJaccard(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.8;
		double actualSusp = calculator.calculateJaccard(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.193;
		double actualSusp = calculator.calculateJaccard(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
