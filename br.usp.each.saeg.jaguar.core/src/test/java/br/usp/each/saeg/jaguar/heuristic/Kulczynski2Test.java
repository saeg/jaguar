package br.usp.each.saeg.jaguar.heuristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.impl.Kulczynski2Heuristic;

public class Kulczynski2Test {

	Heuristic heuristic = new Kulczynski2Heuristic();

	@Test
	public void testCalculateKulczynski2AllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2ANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		int cef = -1;
		int cnf = -1;
		int cep = -1;
		int cnp = -1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnfHaveValues() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		int cef = 1;
		int cnf = 0;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepNotHaveValue() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCepHaveValues() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfNotHaveValue() {
		double expectedSusp = 0.75;
		int cef = 1;
		int cnf = 0;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnpNotHaveValue() {
		double expectedSusp = 0.5;
		int cef = 1;
		int cnf = 1;
		int cep = 1;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefHaveBiggestValue() {
		double expectedSusp = 0.916;
		int cef = 5;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepHaveBiggestValue() {
		double expectedSusp = 0.208;
		int cef = 1;
		int cnf = 3;
		int cep = 5;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2AllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.533;
		int cef = 2;
		int cnf = 1;
		int cep = 3;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.787;
		int cef = 10;
		int cnf = 5;
		int cep = 1;
		int cnp = 2;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.17;
		int cef = 1;
		int cnf = 3;
		int cep = 10;
		int cnp = 4;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreFrequentlyExecuted() {
		double expectedSusp = 0.954;
		int cef = 10;
		int cnf = 1;
		int cep = 0;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.871;
		int cef = 10;
		int cnf = 1;
		int cep = 2;
		int cnp = 1;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCep() {
		double expectedSusp = 0.916;
		int cef = 10;
		int cnf = 0;
		int cep = 2;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefMoreExecutedThanCep2() {
		double expectedSusp = 0.884;
		int cef = 10;
		int cnf = 0;
		int cep = 3;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefCepEqualExecuted() {
		double expectedSusp = 0.75;
		int cef = 10;
		int cnf = 0;
		int cep = 10;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepMoreExecutedThanCef() {
		double expectedSusp = 0.738;
		int cef = 10;
		int cnf = 0;
		int cep = 11;
		int cnp = 0;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefNotExecuted() {
		double expectedSusp = 0;
		int cef = 0;
		int cnf = 10;
		int cep = 10;
		int cnp = 5;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.338;
		int cef = 10;
		int cnf = 5;
		int cep = 1000;
		int cnp = 15;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.889;
		int cef = 100;
		int cnf = 15;
		int cep = 10;
		int cnp = 500;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

	@Test
	public void testCalculateKulczynski2CnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.330;
		int cef = 12;
		int cnf = 30;
		int cep = 20;
		int cnp = 100;
		double actualSusp = heuristic.eval(cef, cnf, cep, cnp);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}

}
