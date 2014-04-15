package br.usp.each.saeg.jaguar.heuristic.impl;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.HeuristicEnum;

public class Wong3Heuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;
		double passed = 0.0d;

		passed = (double) cep;

		if (cep > 2 && cep <= 10) {
			passed = (double) (2 + (0.1 * (cep - 2)));
		}

		if (cep > 10) {
			passed = (double) (2.8 + (0.001 * (cep - 10)));
		}

		suspiciousness = (double) (cef - passed);

		return suspiciousness;
	}

	public HeuristicEnum getEnum() {
		return HeuristicEnum.WONG3;
	}

}
