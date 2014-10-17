package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class Kulczynski2Heuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = 0.5 * (((double) cef / (cef + cnf)) + ((double) cef / (cef + cep)));
		}
		return suspiciousness;
	}

}
