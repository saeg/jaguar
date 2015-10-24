package br.usp.each.saeg.jaguar.cli;

import org.junit.Test;

import br.usp.each.saeg.jaguar.core.cli.JaguarRunnerOptions;

public class JaguarRunnerOptionsTest {

	@Test
	public void toStringNotNullTest(){
		JaguarRunnerOptions jaguarRunnerOptions = new JaguarRunnerOptions();
		System.out.println(jaguarRunnerOptions.toString());
	}
	
}
