package br.usp.each.saeg.jaguar.heuristic;

/**
 * Interface representing an Spectrum-base Fault Localization Heuristic 
 * 
 * @author Henrique Ribeiro
 */
public interface Heuristic {
	
	double eval(int cef, int cnf, int cep, int cnp);
}
