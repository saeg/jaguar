package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class OchiaiHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef > 0) {
			susp = cef / Math.sqrt((cef + cnf) * (cef + cep));
		}
		return susp;
	}

}
