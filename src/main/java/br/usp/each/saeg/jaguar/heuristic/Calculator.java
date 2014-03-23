package br.usp.each.saeg.jaguar.heuristic;

public class Calculator {

	public double calculateTarantula(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			if (cep > 0) {
				suspiciousness = ((double) cef / (cef + cnf))
						/ (((double) cef / (cef + cnf)) + ((double) cep / (cep + cnp)));
			} else {
				suspiciousness = (double) 1;
			}
		}
		return suspiciousness;
	}

	public double calculateDRT(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = ((double) cef / (1 + ((double) cep / (cef + cep
					+ cnf + cnp))));
		}
		return suspiciousness;
	}

	public double calculateOchiai(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = (cef)
					/ (double) Math.sqrt((cef + cnf) * (cef + cep));
		}
		return suspiciousness;
	}

	public double calculateJaccard(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = ((double) cef) / (cef + cnf + cep);
		}
		return suspiciousness;
	}

	public double calculateZoltar(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = ((double) cef)
					/ (cef + cnf + cep + (10000 * ((cnf * cep / (double) cef))));
		}
		return suspiciousness;
	}

	public double calculateOp(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef >= 0) {
			suspiciousness = (cef) - ((double) cep / (cep + cnp + 1));
		}
		return suspiciousness;
	}

	public double calculateMinus(int cef, int cnf, int cep, int cnp) {
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

	public double calculateKulczynski2(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if (cef > 0) {
			suspiciousness = 0.5 * (((double) cef / (cef + cnf)) + ((double) cef / (cef + cep)));
		}
		return suspiciousness;
	}

	public double calculateMcCon(int cef, int cnf, int cep, int cnp) {
		double suspiciousness = 0.0d;

		if ((cef + cnf > 0) && (cef + cep > 0)) {
			suspiciousness = (double) ((cef * cef) - (cnf * cep))
					/ (double) ((cef + cnf) * (cef + cep));
		}
		return suspiciousness;
	}

	public double calculateWong3(int cef, int cnf, int cep, int cnp) {
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

}
