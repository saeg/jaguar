package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class DRTTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateDRTAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 0, 0, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(-1, 0, 0, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(-1, -1, -1, -1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double susp = calculator.calculateDRT(1, 0, 0, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 1, 0, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 0, 1, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 0, 0, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnfHaveValues() {
		double expectedSusp = 1;
		double susp = calculator.calculateDRT(1, 1, 0, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 1, 1, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double susp = calculator.calculateDRT(1, 0, 0, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepNotHaveValue() {
		double expectedSusp = 1;
		double susp = calculator.calculateDRT(1, 1, 0, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCepHaveValues() {
		double expectedSusp = 0.666;
		double susp = calculator.calculateDRT(1, 0, 1, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfNotHaveValue() {
		double expectedSusp = 0.75;
		double susp = calculator.calculateDRT(1, 0, 1, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.8;
		double susp = calculator.calculateDRT(1, 1, 1, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCnpNotHaveValue() {
		double expectedSusp = 0.75;
		double susp = calculator.calculateDRT(1, 1, 1, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefHaveBiggestValue() {
		double expectedSusp = 5;
		double susp = calculator.calculateDRT(5, 1, 0, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepHaveBiggestValue() {
		double expectedSusp = 0.666;
		double susp = calculator.calculateDRT(1, 3, 5, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsHaveEqualValues() {
		double expectedSusp = 1.454;
		double susp = calculator.calculateDRT(2, 1, 3, 2);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 9.473;
		double susp = calculator.calculateDRT(10, 5, 1, 2);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.642;
		double susp = calculator.calculateDRT(1, 3, 10, 4);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreFrequentlyExecuted() {
		double expectedSusp = 10;
		double susp = calculator.calculateDRT(10, 1, 0, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 8.75;
		double susp = calculator.calculateDRT(10, 1, 2, 1);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCep() {
		double expectedSusp = 8.571;
		double susp = calculator.calculateDRT(10, 0, 2, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCep2() {
		double expectedSusp = 8.125;
		double susp = calculator.calculateDRT(10, 0, 3, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCepEqualExecuted() {
		double expectedSusp = 6.666;
		double susp = calculator.calculateDRT(10, 0, 10, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepMoreExecutedThanCef() {
		double expectedSusp = 6.562;
		double susp = calculator.calculateDRT(10, 0, 11, 0);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefNotExecuted() {
		double expectedSusp = 0;
		double susp = calculator.calculateDRT(0, 10, 10, 5);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 5.073;
		double susp = calculator.calculateDRT(10, 5, 1000, 15);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 98.425;
		double susp = calculator.calculateDRT(100, 15, 10, 500);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 10.681;
		double susp = calculator.calculateDRT(12, 30, 20, 100);
		Assert.assertEquals(expectedSusp, susp, 0.001);
	}
}
