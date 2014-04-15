package br.usp.each.saeg.jaguar.heuristic.impl;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.HeuristicEnum;

public class OpHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef >= 0) {
			suspiciousness = (cef) - ((double) cep / (cep + cnp + 1));
		}
		return suspiciousness;
	}

	public HeuristicEnum getEnum() {
		return HeuristicEnum.OP;
	}
}
