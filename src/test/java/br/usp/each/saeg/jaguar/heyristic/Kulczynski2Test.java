package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class Kulczynski2Test {

	Calculator calculator = new Calculator();

	@Test
	public void testCalculateKulczynski2AllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2ANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateKulczynski2(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnfHaveValues() {
		double expectedSusp = 0.75;
		double actualSusp = calculator.calculateKulczynski2(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateKulczynski2(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepNotHaveValue() {
		double expectedSusp = 0.75;
		double actualSusp = calculator.calculateKulczynski2(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCepHaveValues() {
		double expectedSusp = 0.75;
		double actualSusp = calculator.calculateKulczynski2(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfNotHaveValue() {
		double expectedSusp = 0.75;
		double actualSusp = calculator.calculateKulczynski2(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateKulczynski2(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnpNotHaveValue() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateKulczynski2(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefHaveBiggestValue() {
		double expectedSusp = 0.916;
		double actualSusp = calculator.calculateKulczynski2(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepHaveBiggestValue() {
		double expectedSusp = 0.208;
		double actualSusp = calculator.calculateKulczynski2(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.533;
		double actualSusp = calculator.calculateKulczynski2(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.787;
		double actualSusp = calculator.calculateKulczynski2(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.17;
		double actualSusp = calculator.calculateKulczynski2(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreFrequentlyExecuted() {
		double expectedSusp = 0.954;
		double actualSusp = calculator.calculateKulczynski2(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.871;
		double actualSusp = calculator.calculateKulczynski2(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCep() {
		double expectedSusp = 0.916;
		double actualSusp = calculator.calculateKulczynski2(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCep2() {
		double expectedSusp = 0.884;
		double actualSusp = calculator.calculateKulczynski2(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCepEqualExecuted() {
		double expectedSusp = 0.75;
		double actualSusp = calculator.calculateKulczynski2(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepMoreExecutedThanCef() {
		double expectedSusp = 0.738;
		double actualSusp = calculator.calculateKulczynski2(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefNotExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateKulczynski2(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.338;
		double actualSusp = calculator.calculateKulczynski2(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.889;
		double actualSusp = calculator.calculateKulczynski2(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.330;
		double actualSusp = calculator.calculateKulczynski2(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
