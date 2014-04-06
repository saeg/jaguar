package br.usp.each.saeg.jaguar.heuristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.JaccardHeuristic;

public class JaccardTest {

	Heuristic heuristic = new JaccardHeuristic();

	@Test
	public void testCalculateJaccardAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnfHaveValues() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepNotHaveValue() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCepHaveValues() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfNotHaveValue() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.333;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnpNotHaveValue() {
		double expectedSusp = 0.333;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepHaveBiggestValue() {
		double expectedSusp = 0.111;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.333;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.625;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.071;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.769;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCep() {
		double expectedSusp = 0.833;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefMoreExecutedThanCep2() {
		double expectedSusp = 0.769;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepMoreExecutedThanCef() {
		double expectedSusp = 0.476;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefNotExecuted() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.00985;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.8;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateJaccardCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.193;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
