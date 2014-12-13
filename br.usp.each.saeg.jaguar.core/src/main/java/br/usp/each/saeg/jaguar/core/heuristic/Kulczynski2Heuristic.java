package br.usp.each.saeg.jaguar.core.heuristic;


public class Kulczynski2Heuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef > 0) {
			final double dCef = cef;
			susp = 0.5 * ((dCef / (cef + cnf)) + (dCef / (cef + cep)));
		}
		return susp;
	}

}
