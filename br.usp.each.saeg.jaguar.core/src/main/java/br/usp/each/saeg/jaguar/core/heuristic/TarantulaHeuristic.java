package br.usp.each.saeg.jaguar.core.heuristic;


public class TarantulaHeuristic implements Heuristic {

	public double eval(int cef, int cnf, int cep, int cnp) {
		double susp = 0.0d;
		if (cef > 0) {
			if (cep > 0) {
				final double dCef = cef;
				final double dCep = cep;
				final double value = dCef / (cef + cnf);
				susp = value / (value + dCep / (cep + cnp));
			} else {
				susp = 1;
			}
		}
		return susp;
	}

}
