package br.usp.each.saeg.jaguar.core.heuristic;


public class OpHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef >= 0) {
			final double dCep = cep;
			susp = cef - dCep / (cep + cnp + 1);
		}
		return susp;
	}

}
