package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class ZoltarTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateZoltarAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateZoltar(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnfHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateZoltar(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateZoltar(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateZoltar(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateZoltar(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateZoltar(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.00009997;
		double actualSusp = calculator.calculateZoltar(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnpNotHaveValue() {
		double expectedSusp = 0.00009997;
		double actualSusp = calculator.calculateZoltar(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateZoltar(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepHaveBiggestValue() {
		double expectedSusp = 0.0000066663;
		double actualSusp = calculator.calculateZoltar(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.000133;
		double actualSusp = calculator.calculateZoltar(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.00199;
		double actualSusp = calculator.calculateZoltar(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.0000033332;
		double actualSusp = calculator.calculateZoltar(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		double actualSusp = calculator.calculateZoltar(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.00496;
		double actualSusp = calculator.calculateZoltar(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCep() {
		double expectedSusp = 0.833;
		double actualSusp = calculator.calculateZoltar(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCep2() {
		double expectedSusp = 0.769;
		double actualSusp = calculator.calculateZoltar(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateZoltar(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepMoreExecutedThanCef() {
		double expectedSusp = 0.476;
		double actualSusp = calculator.calculateZoltar(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefNotExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateZoltar(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.00000199;
		double actualSusp = calculator.calculateZoltar(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.00661;
		double actualSusp = calculator.calculateZoltar(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.00002399;
		double actualSusp = calculator.calculateZoltar(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
