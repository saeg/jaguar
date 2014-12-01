package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class McConHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		final int cefPlusCnf = cef + cnf;
		final int cefPlusCep = cef + cep;
		if (cefPlusCnf > 0 && cefPlusCep > 0) {
			susp = (double) (cef * cef - cnf * cep) / (cefPlusCnf * cefPlusCep);
		}
		return susp;
	}

}
