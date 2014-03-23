package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class MinusTest {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateMinusAllCoeficientsZeroMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateMinus(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusANegativeValueInCefMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateMinus(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateMinus(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateMinus(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCnfHaveValueMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateMinus(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepHaveValueMustBeZero() {
		double expectedSusp = -1.0;
		double actualSusp = calculator.calculateMinus(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCnpHaveValueMustBeZero() {
		double expectedSusp = -0.5;
		double actualSusp = calculator.calculateMinus(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefCnfHaveValues() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateMinus(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = -0.666;
		double actualSusp = calculator.calculateMinus(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateMinus(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepNotHaveValue() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateMinus(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMinus(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCnfNotHaveValue() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateMinus(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusAllCoeficientsHaveValueOne() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateMinus(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCnpNotHaveValue() {
		double expectedSusp = -0.666;
		double actualSusp = calculator.calculateMinus(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefHaveBiggestValue() {
		double expectedSusp = 0.857;
		double actualSusp = calculator.calculateMinus(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepHaveBiggestValue() {
		double expectedSusp = -0.587;
		double actualSusp = calculator.calculateMinus(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.071;
		double actualSusp = calculator.calculateMinus(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateMinus(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = -0.464;
		double actualSusp = calculator.calculateMinus(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.916;
		double actualSusp = calculator.calculateMinus(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.362;
		double actualSusp = calculator.calculateMinus(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefMoreExecutedThanCep() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMinus(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefMoreExecutedThanCep2() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMinus(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMinus(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepMoreExecutedThanCef() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateMinus(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefNotExecuted() {
		double expectedSusp = -0.75;
		double actualSusp = calculator.calculateMinus(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = -0.553;
		double actualSusp = calculator.calculateMinus(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.860;
		double actualSusp = calculator.calculateMinus(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateMinusCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.170;
		double actualSusp = calculator.calculateMinus(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
