package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class Wong3Heuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double passed = 0.0d;
		if (cep > 2 && cep <= 10) {
			passed = 2 + (0.1 * (cep - 2));
		} else if (cep > 10) {
			passed = 2.8 + (0.001 * (cep - 10));
		} else {
			passed = cep;
		}
		return cef - passed;
	}

}
