package br.usp.each.saeg.jaguar.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;

public class HeuristicCalculator {

	private Heuristic heuristic;
	private HashSet<TestRequirement> requirements = new HashSet<TestRequirement>();
	private Integer nTestsPassed;
	private Integer nTestsFailed;
	
	public HeuristicCalculator(Heuristic heuristic,
			HashSet<TestRequirement> requirements, Integer nTestsPassed,
			Integer nTestsFailed) {
		super();
		this.heuristic = heuristic;
		this.requirements = requirements;
		this.nTestsPassed = nTestsPassed;
		this.nTestsFailed = nTestsFailed;
	}
	
	public ArrayList<TestRequirement> calculateRank(){
		int cef, cnf, cep, cnp;
		ArrayList<TestRequirement> rankList = new ArrayList<TestRequirement>(); 
		
		for (TestRequirement requirement : requirements) {
			cef = requirement.getCef();
			cep = requirement.getCep();
			cnf = nTestsFailed - cef;
			cnp = nTestsPassed - cep;
			Double suspiciousness = heuristic.eval(cef, cnf, cep, cnp);
			requirement.setSuspiciousness(suspiciousness);
			
			if (suspiciousness > 0){
				rankList.add(requirement);
			}
		}
		
		Collections.sort(rankList);
		Collections.reverse(rankList);
		return rankList;
	}
	
}
