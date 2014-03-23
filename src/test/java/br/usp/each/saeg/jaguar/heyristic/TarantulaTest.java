package br.usp.each.saeg.jaguar.heyristic;

import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Calculator;

public class TarantulaTest {
	
	Calculator calculator = new Calculator();
	
	@Test
	public void testCalculateTarantulaAllCoeficientsZeroMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaANegativeValueInCefMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(-1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaAllCoeficientsNegativeMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(-1,-1,-1,-1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefHaveValueMustBeMaxSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(1,0,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCnfHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCnpHaveValueMustBeZero() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefCnfHaveValues() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(1,1,0,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefNotHaveValueMustBeLowSuspicious() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefCnpHaveValuesMustBeHighSuspicious() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(1,0,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepNotHaveValue() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(1,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefCepHaveValues() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(1,0,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCnfNotHaveValue() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateTarantula(1,0,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaAllCoeficientsHaveValueOne() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(1,1,1,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCnpNotHaveValue() {
		double expectedSusp = 0.333;
		double actualSusp = calculator.calculateTarantula(1,1,1,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefHaveBiggestValue() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(5,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepHaveBiggestValue() {
		double expectedSusp = 0.231;
		double actualSusp = calculator.calculateTarantula(1,3,5,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaAllCoeficientsHaveEqualValues() {
		double expectedSusp = 0.526;
		double actualSusp = calculator.calculateTarantula(2,1,3,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefCnfMoreFrequentlyExecuted() {
		double expectedSusp = 0.666;
		double actualSusp = calculator.calculateTarantula(10,5,1,2);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepCnpMoreFrequentlyExecuted() {
		double expectedSusp = 0.259;
		double actualSusp = calculator.calculateTarantula(1,3,10,4);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefMoreFrequentlyExecuted() {
		double expectedSusp = 1;
		double actualSusp = calculator.calculateTarantula(10,1,0,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCnfCepCnp() {
		double expectedSusp = 0.576;
		double actualSusp = calculator.calculateTarantula(10,1,2,1);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCep() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(10,0,2,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefMoreExecutedThanCep2() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(10,0,3,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefCepEqualExecuted() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(10,0,10,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepMoreExecutedThanCef() {
		double expectedSusp = 0.5;
		double actualSusp = calculator.calculateTarantula(10,0,11,0);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefNotExecuted() {
		double expectedSusp = 0;
		double actualSusp = calculator.calculateTarantula(0,10,10,5);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCepWithHighValueMustBeLowSuspiciousness() {
		double expectedSusp = 0.403;
		double actualSusp = calculator.calculateTarantula(10,5,1000,15);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCefWithHighVaValueMustBeHighSuspiciousness() {
		double expectedSusp = 0.977;
		double actualSusp = calculator.calculateTarantula(100,15,10,500);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
	
	@Test
	public void testCalculateTarantulaCnfCnpWithHighValuesMustBeMediumSuspiciousness() {
		double expectedSusp = 0.631;
		double actualSusp = calculator.calculateTarantula(12,30,20,100);
		Assert.assertEquals(expectedSusp, actualSusp, 0.001);
	}
}
