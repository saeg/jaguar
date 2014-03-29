package br.usp.each.saeg.jaguar.heyristic;


import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class Wong3Test {

Calculator calculator = new Calculator();
	@Test
	public void testCalculateWong3AllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3ANegativeValueInCefMustBeZero() {
		double expectedSusp = -1;;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfHaveValueMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepHaveValueMustBeZero() {
		double expectedSusp = -1;;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnpHaveValueMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnfHaveValues() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = -1;;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepNotHaveValue() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCepHaveValues() {
		double expectedSusp = 0;;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfNotHaveValue() {
		double expectedSusp = 0;;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsHaveValueOne() {
		double expectedSusp = 0;;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnpNotHaveValue() {
		double expectedSusp = 0;;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefHaveBiggestValue() {
		double expectedSusp = 5;;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepHaveBiggestValue() {
		double expectedSusp = -1.3;;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3AllCoeficientsHaveEqualValues() {
		double expectedSusp = -0.1;;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 9;;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepCnpMoreFrequentlyExecuted() {
		double expectedSusp = -1.8;;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreFrequentlyExecuted() {
		double expectedSusp = 10;;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 8;;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCep() {
		double expectedSusp = 8;;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefMoreExecutedThanCep2() {
		double expectedSusp = 7.9;;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefCepEqualExecuted() {
		double expectedSusp = 7.2;;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepMoreExecutedThanCef() {
		double expectedSusp = 7.199;;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefNotExecuted() {
		double expectedSusp = -2.8;;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 6.210;;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 97.200;;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateWong3CnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 9.190;;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = calculator.calculateWong3(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
