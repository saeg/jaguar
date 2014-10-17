package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class McConHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if ((cef + cnf > 0) && (cef + cep > 0)) {
			suspiciousness = (double) ((cef * cef) - (cnf * cep))
					/ (double) ((cef + cnf) * (cef + cep));
		}
		return suspiciousness;
	}

}
