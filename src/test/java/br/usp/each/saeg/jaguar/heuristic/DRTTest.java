package br.usp.each.saeg.jaguar.heuristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.DRTHeuristic;

public class DRTTest {

	Heuristic heuristic = new DRTHeuristic();

	@Test
	public void testCalculateDRTAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnfHaveValues() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepNotHaveValue() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCepHaveValues() {
		double expectedSusp = 0.666;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfNotHaveValue() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.8;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCnpNotHaveValue() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefHaveBiggestValue() {
		double expectedSusp = 5;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepHaveBiggestValue() {
		double expectedSusp = 0.666;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTAllCoeficientsHaveEqualValues() {
		double expectedSusp = 1.454;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 9.473;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.642;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreFrequentlyExecuted() {
		double expectedSusp = 10;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 8.75;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCep() {
		double expectedSusp = 8.571;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefMoreExecutedThanCep2() {
		double expectedSusp = 8.125;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefCepEqualExecuted() {
		double expectedSusp = 6.666;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepMoreExecutedThanCef() {
		double expectedSusp = 6.562;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefNotExecuted() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 5.073;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 98.425;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateDRTCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 10.681;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}