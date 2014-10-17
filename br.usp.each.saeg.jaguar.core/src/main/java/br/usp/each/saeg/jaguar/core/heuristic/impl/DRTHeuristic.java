package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class DRTHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = ((double) cef / (1 + ((double) cep / (cef + cep
					+ cnf + cnp))));
		}
		return suspiciousness;
	}

}
