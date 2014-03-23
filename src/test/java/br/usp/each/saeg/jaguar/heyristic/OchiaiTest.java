package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class OchiaiTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateOchiaiAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 0, 0, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(-1, 0, 0, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(-1, -1, -1, -1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(1, 0, 0, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 1, 0, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 0, 1, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 0, 0, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnfHaveValues() {
		double expectedSusp = 0.707;
		double actualSusp = calculator.calculateOchiai(1, 1, 0, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 1, 1, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 0.707;
		double actualSusp = calculator.calculateOchiai(1, 0, 0, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateOchiai(1, 1, 0, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCepHaveValues() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(1, 0, 1, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfNotHaveValue() {
		double expectedSusp = 0.707;
		double actualSusp = calculator.calculateOchiai(1, 0, 1, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateOchiai(1, 1, 1, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnpNotHaveValue() {
		double expectedSusp = 0.707;
		double actualSusp = calculator.calculateOchiai(1, 1, 1, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateOchiai(5, 1, 0, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepHaveBiggestValue() {
		double expectedSusp = 0.353;
		double actualSusp = calculator.calculateOchiai(1, 3, 5, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.577;
		double actualSusp = calculator.calculateOchiai(2, 2, 2, 2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.745;
		double actualSusp = calculator.calculateOchiai(10, 5, 1, 2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(1, 3, 10, 4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		double actualSusp = calculator.calculateOchiai(10, 1, 0, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.909;
		double actualSusp = calculator.calculateOchiai(10, 1, 2, 1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCep() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(10, 0, 2, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCep2() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(10, 0, 3, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCepEqualExecuted() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(10, 0, 10, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepMoreExecutedThanCef() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateOchiai(10, 0, 11, 0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefNotExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateOchiai(0, 10, 10, 5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.516;
		double actualSusp = calculator.calculateOchiai(10, 5, 1000, 15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.308;
		double actualSusp = calculator.calculateOchiai(100, 15, 10, 500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.174;
		double actualSusp = calculator.calculateOchiai(12, 30, 20, 100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
