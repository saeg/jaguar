package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.TarantulaHeuristic;

public class TarantulaTest {

	Heuristic heuristic = new TarantulaHeuristic();

	@Test
	public void testCalculateTarantulaAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefCnfHaveValues() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepNotHaveValue() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefCepHaveValues() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCnfNotHaveValue() {
		double expectedSusp = 0.666;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCnpNotHaveValue() {
		double expectedSusp = 0.333;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefHaveBiggestValue() {
		double expectedSusp = 1;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepHaveBiggestValue() {
		double expectedSusp = 0.231;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.526;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.666;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.259;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefMoreFrequentlyExecuted() {
		double expectedSusp = 1;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.576;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCep() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCep2() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepMoreExecutedThanCef() {
		double expectedSusp = 0.5;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefNotExecuted() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.403;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.977;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateTarantulaCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.631;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
