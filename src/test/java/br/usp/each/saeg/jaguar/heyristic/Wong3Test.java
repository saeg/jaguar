package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class Wong3Test {

	Calculator calculator = new Calculator();


	@Test
	public void testCalculateWong3AllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3ANegativeValueInCefMustBeZero() {
		double expectedSusp = -1;
		double actualSusp = calculator.calculateWong3(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateWong3(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepHaveValueMustBeZero() {
		double expectedSusp = -1;
		double actualSusp = calculator.calculateWong3(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnfHaveValues() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateWong3(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = -1;
		double actualSusp = calculator.calculateWong3(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateWong3(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepNotHaveValue() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateWong3(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCepHaveValues() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfNotHaveValue() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsHaveValueOne() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnpNotHaveValue() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateWong3(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefHaveBiggestValue() {
		double expectedSusp = 5;
		double actualSusp = calculator.calculateWong3(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepHaveBiggestValue() {
		double expectedSusp = -1.3;
		double actualSusp = calculator.calculateWong3(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsHaveEqualValues() {
		double expectedSusp = -0.1;
		double actualSusp = calculator.calculateWong3(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 9;
		double actualSusp = calculator.calculateWong3(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepCnpMoreFrequentlyExecuted() {
		double expectedSusp = -1.8;
		double actualSusp = calculator.calculateWong3(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreFrequentlyExecuted() {
		double expectedSusp = 10;
		double actualSusp = calculator.calculateWong3(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 8;
		double actualSusp = calculator.calculateWong3(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCep() {
		double expectedSusp = 8;
		double actualSusp = calculator.calculateWong3(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCep2() {
		double expectedSusp = 7.9;
		double actualSusp = calculator.calculateWong3(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCepEqualExecuted() {
		double expectedSusp = 7.2;
		double actualSusp = calculator.calculateWong3(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepMoreExecutedThanCef() {
		double expectedSusp = 7.199;
		double actualSusp = calculator.calculateWong3(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefNotExecuted() {
		double expectedSusp = -2.8;
		double actualSusp = calculator.calculateWong3(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 6.210;
		double actualSusp = calculator.calculateWong3(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 97.200;
		double actualSusp = calculator.calculateWong3(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 9.190;
		double actualSusp = calculator.calculateWong3(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
