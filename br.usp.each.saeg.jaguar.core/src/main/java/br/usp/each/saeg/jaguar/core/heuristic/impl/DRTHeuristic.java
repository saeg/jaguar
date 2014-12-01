package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class DRTHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef > 0) {
			final double dCef = cef;
			final double dCep = cep;
			susp = dCef / (1 + dCep / (cef + cep + cnf + cnp));
		}
		return susp;
	}

}
