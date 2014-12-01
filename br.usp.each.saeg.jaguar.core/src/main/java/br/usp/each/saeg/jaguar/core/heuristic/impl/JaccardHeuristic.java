package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class JaccardHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef > 0) {
			final double dCef = cef;
			susp = dCef / (cef + cnf + cep);
		}
		return susp;
	}

}
