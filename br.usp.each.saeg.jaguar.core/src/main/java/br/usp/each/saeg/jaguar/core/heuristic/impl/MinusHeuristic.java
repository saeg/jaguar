package br.usp.each.saeg.jaguar.core.heuristic.impl;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;

public class MinusHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;
		double suspiciousnessMinus = 0.0d;
		double minusFailed = 0.0d;
		double minusPassed = 0.0d;

		if (cef > 0) {
			if (cep > 0) {
				suspiciousness = ((double) cef / (cef + cnf))
						/ (((double) cef / (cef + cnf)) + ((double) cep / (cep + cnp)));
			} else {
				suspiciousness = (double) 1;
			}
		}

		if ((cef + cnf) > 0) {
			minusFailed = (((double) cef / (cef + cnf)));
		}

		if ((cep + cnp) > 0) {
			minusPassed = (((double) cep / (cep + cnp)));
		}

		if (minusFailed != 1) {
			suspiciousnessMinus = (double) (1 - minusFailed)
					/ ((1 - minusFailed) + (1 - minusPassed));
		}

		suspiciousness -= suspiciousnessMinus;
		return suspiciousness;
	}

}
