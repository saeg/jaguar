package br.usp.each.saeg.jaguar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;

import br.usp.each.saeg.jaguar.core.CoverageStatus;
import br.usp.each.saeg.jaguar.core.TestRequirement;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.HeuristicCalculator;
import br.usp.each.saeg.jaguar.infra.FileUtils;

/**
 * This class store the coverage information received from Jacoco and generate a
 * rank of more suspicious test requirement based on one SFL Heuristic.
 * 
 * @author Henrique Ribeiro
 */
public class Jaguar {

	private int nTests = 0;
	private int nTestsFailed = 0;
	private HashMap<Integer, TestRequirement> testRequirements = new HashMap<Integer, TestRequirement>();
	private Heuristic heuristic;
	private File classesDir;

	/**
	 * Construct the Jaguar object.
	 * 
	 * @param heuristic
	 *            the heuristic to be used on the fault localization rank.
	 * @param targetDir
	 *            the target dir created by eclipse
	 */
	public Jaguar(Heuristic heuristic, File targetDir) {
		this.heuristic = heuristic;
		this.classesDir = FileUtils.getFile(targetDir, "classes");
	}

	/**
	 * Receive the coverage information and store it on Test Requiremtns.
	 * 
	 * @param executionData
	 *            the covarege data from Jacoco
	 * @param currentTestFailed
	 *            result of the test
	 * 
	 */
	public void collect(final ExecutionDataStore executionData, boolean currentTestFailed) {
		final CoverageBuilder coverageVisitor = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageVisitor);

		try {
			analyzer.analyzeAll(classesDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (IClassCoverage clazz : coverageVisitor.getClasses()) {
			CoverageStatus coverageStatus = CoverageStatus.as(clazz.getClassCounter().getStatus());
			if (CoverageStatus.FULLY_COVERED == coverageStatus || CoverageStatus.PARTLY_COVERED == coverageStatus) {
				int firstLine = clazz.getFirstLine();
				int lastLine = clazz.getLastLine();
				if (firstLine >= 0) {
					for (int currentLine = firstLine; currentLine <= lastLine; currentLine++) {
						ILine line = clazz.getLine(currentLine);
						coverageStatus = CoverageStatus.as(line.getStatus());
						if (CoverageStatus.FULLY_COVERED == coverageStatus || CoverageStatus.PARTLY_COVERED == coverageStatus) {
							updateRequirement(clazz.getName(), currentLine, currentTestFailed);
						}
					}
				}
			}
		}
	}

	/**
	 * Update the testRequirement info. If it does not exist, create a new one.
	 * If the test has failed, increment the cef (coefficient of executed and
	 * failed) If the test has passed, increment the cep (coefficient of
	 * executed and passed)
	 * 
	 * @param className
	 *            the class name, including package
	 * @param lineNumber
	 *            the line number
	 * @param failed
	 *            if the test has failed
	 * 
	 */
	private void updateRequirement(String className, int lineNumber, boolean failed) {
		TestRequirement testRequirement = new TestRequirement(className, lineNumber);
		TestRequirement foundRequirement = testRequirements.get(testRequirement.hashCode());

		if (foundRequirement == null) {
			testRequirements.put(testRequirement.hashCode(), testRequirement);
		} else {
			testRequirement = foundRequirement;
		}

		if (failed) {
			testRequirement.increaseFailed();
		} else {
			testRequirement.increasePassed();
		}
	}

	/**
	 * Calculate the rank based on the heuristic and testRequirements. Print the
	 * rank in descending order.
	 * 
	 */
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
