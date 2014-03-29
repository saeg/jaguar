package br.usp.each.saeg.jaguar.heyristic;


import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class OchiaiTest {

Calculator calculator = new Calculator();
	@Test
	public void testCalculateOchiaiAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfHaveValueMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepHaveValueMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnpHaveValueMustBeZero() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnfHaveValues() {
		double expectedSusp = 0.707;;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepNotHaveValue() {
		double expectedSusp = 0.707;;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCepHaveValues() {
		double expectedSusp = 0.707;;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfNotHaveValue() {
		double expectedSusp = 0.707;;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnpNotHaveValue() {
		double expectedSusp = 0.5;;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefHaveBiggestValue() {
		double expectedSusp = 0.833;;
		int cef = 5;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepHaveBiggestValue() {
		double expectedSusp = 0.353;;
		int cef = 1;
		int cnf = 3;
		int cep = 1;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.5;;
		int cef = 2;
		int cnf = 2;
		int cep = 2;
		int cnp = 2;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.778;;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.150;;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.953;;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.870;;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCep() {
		double expectedSusp = 0.912;;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefMoreExecutedThanCep2() {
		double expectedSusp = 0.877;;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefCepEqualExecuted() {
		double expectedSusp = 0.707;;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepMoreExecutedThanCef() {
		double expectedSusp = 0.690;;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefNotExecuted() {
		double expectedSusp = 0;;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.081;;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.889;;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateOchiaiCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.327;;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = calculator.calculateOchiai(cef,cnf,cep,cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
