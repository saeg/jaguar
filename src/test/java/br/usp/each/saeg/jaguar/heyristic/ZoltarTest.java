package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.ZoltarHeuristic;

public class ZoltarTest {

	Heuristic heuristic = new ZoltarHeuristic();

	@Test
	public void testCalculateZoltarAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnfHaveValues() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepNotHaveValue() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCepHaveValues() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfNotHaveValue() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.00009997;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnpNotHaveValue() {
		double expectedSusp = 0.00009997;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefHaveBiggestValue() {
		double expectedSusp = 0.833;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepHaveBiggestValue() {
		double expectedSusp = 0.0000066663;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.000133;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.00199;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.0000033332;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreFrequentlyExecuted() {
		double expectedSusp = 0.909;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.00496;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCep() {
		double expectedSusp = 0.833;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefMoreExecutedThanCep2() {
		double expectedSusp = 0.769;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepMoreExecutedThanCef() {
		double expectedSusp = 0.476;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefNotExecuted() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.00000199;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.00661;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateZoltarCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.00002399;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
