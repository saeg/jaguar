package br.usp.each.saeg.jaguar.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;

import br.usp.each.saeg.jaguar.core.LineCoverageStatus;
import br.usp.each.saeg.jaguar.core.TestRequirement;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.HeuristicCalculator;

public class Jaguar {

	private int nTests;
	private int nTestsFailed;
	private Heuristic heuristic;
	private HashMap<Integer, TestRequirement> testRequirements = new HashMap<Integer, TestRequirement>();

	public Jaguar(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	public void analyse(final ExecutionDataStore executionData, boolean currentTestFailed) {
		final CoverageBuilder coverageVisitor = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageVisitor);
		try {
			analyzer.analyzeAll(new File("/home/unknown/workspace/jaguar/target/classes/"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (IClassCoverage clazz : coverageVisitor.getClasses()) {
			int firstLine = clazz.getFirstLine();
			int lastLine = clazz.getLastLine();
			if (firstLine >= 0) {
				for (int currentLine = firstLine; currentLine <= lastLine; currentLine++) {
					ILine line = clazz.getLine(currentLine);
					LineCoverageStatus coverageStatus = LineCoverageStatus.as(line.getStatus());
					if (LineCoverageStatus.FULLY_COVERED == coverageStatus || LineCoverageStatus.PARTLY_COVERED == coverageStatus) {
						TestRequirement testRequirement = new TestRequirement(clazz.getName(), currentLine);
						TestRequirement foundTest = testRequirements.get(testRequirement.hashCode());

						if (foundTest == null) {
							testRequirements.put(testRequirement.hashCode(), testRequirement);
						} else {
							testRequirement = foundTest;
						}

						if (currentTestFailed) {
							testRequirement.increaseFailed();
						} else {
							testRequirement.increasePassed();
						}
					}
				}
			}
		}
	}

	public void generateRank() {
		HeuristicCalculator calc = new HeuristicCalculator(heuristic, testRequirements.values(), nTests - nTestsFailed, nTestsFailed);
		ArrayList<TestRequirement> result = calc.calculateRank();
		System.out.println("Heuristica = " + heuristic.getEnum());
		printRank(result);
	}

	private void printRank(ArrayList<TestRequirement> result) {
		for (TestRequirement testRequirement : result) {
			System.out.println(testRequirement.getClassName() + "[" + testRequirement.getLineNumber() + "]:"
					+ testRequirement.getSuspiciousness());
		}
	}

	public int getnTests() {
		return nTests;
	}

	public int getnTestsFailed() {
		return nTestsFailed;
	}

	public int increaseNTests() {
		return ++nTests;
	}

	public int increaseNTestsFailed() {
		return ++nTestsFailed;
	}

}
