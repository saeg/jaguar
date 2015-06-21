package br.usp.each.saeg.jaguar.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.eclipse.jdt.core.Signature;
import org.jacoco.core.analysis.AbstractAnalyzer;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.DataflowAnalyzer;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.analysis.dua.DuaCoverageBuilder;
import org.jacoco.core.analysis.dua.IDua;
import org.jacoco.core.analysis.dua.IDuaClassCoverage;
import org.jacoco.core.analysis.dua.IDuaMethodCoverage;
import org.jacoco.core.data.AbstractExecutionDataStore;
import org.jacoco.core.data.dua.DataflowExecutionDataStore;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.core.builder.CodeForestXmlBuilder;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.HeuristicCalculator;
import br.usp.each.saeg.jaguar.core.model.core.CoverageStatus;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

/**
 * This class store the coverage information received from Jacoco and generate a
 * rank of more suspicious test requirement based on one SFL Heuristic.
 * 
 * @author Henrique Ribeiro
 */
public class Jaguar {

	private static final String XML_NAME = "codeforest";
	private static final String FOLDER_NAME = ".jaguar";
	private int nTests = 0;
	private int nTestsFailed = 0;
	private HashMap<Integer, AbstractTestRequirement> testRequirements = new HashMap<Integer, AbstractTestRequirement>();
	private Heuristic currentHeuristic;
	private File classesDir;
	private Long startTime;

	/**
	 * Construct the Jaguar object.
	 * 
	 * @param heuristic
	 *            the heuristic to be used on the fault localization rank.
	 * @param targetDir
	 *            the target dir created by eclipse
	 * @param isDataflow 
	 * 			  flag to indicate if it is using data-flow coverage
	 */
	public Jaguar(Heuristic heuristic, File classesDir, Boolean isDataflow) {
		this.currentHeuristic = heuristic;
		this.classesDir = classesDir;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Construct the Jaguar object (control-flow).
	 * 
	 * @param heuristic
	 *            the heuristic to be used on the fault localization rank.
	 * @param targetDir
	 *            the target dir created by eclipse
	 */
	public Jaguar(Heuristic heuristic, File classesDir) {
		this(heuristic, classesDir, false);
	}
	
	/**
	 * Receive the coverage information and store it on Test Requiremtns.
	 * 
	 * @param executionData
	 *            the covarege data from Jacoco
	 * @param currentTestFailed
	 *            result of the test
	 * @throws IOException 
	 * 
	 */
	public void collect(final AbstractExecutionDataStore executionData, boolean currentTestFailed) throws IOException {
		if (executionData instanceof DataflowExecutionDataStore) {
			DuaCoverageBuilder duaCoverageBuilder = new DuaCoverageBuilder();
			AbstractAnalyzer analyzer = new DataflowAnalyzer(executionData, duaCoverageBuilder);
			analyzer.analyzeAll(classesDir);
			collectDuaCoverage(currentTestFailed, duaCoverageBuilder);
		} else {
			CoverageBuilder coverageVisitor = new CoverageBuilder();
			AbstractAnalyzer analyzer = new Analyzer(executionData, coverageVisitor);
			analyzer.analyzeAll(classesDir);
			collectLineCoverage(currentTestFailed, coverageVisitor);
		}

	}

	private void collectDuaCoverage(boolean currentTestFailed, DuaCoverageBuilder coverageVisitor) {
		for (IDuaClassCoverage clazz : coverageVisitor.getClasses()) {
			for (IDuaMethodCoverage method : clazz.getMethods()) {
				for (IDua dua : method.getDuas()) {
					CoverageStatus coverageStatus = CoverageStatus.as(dua.getStatus());
					if (CoverageStatus.FULLY_COVERED == coverageStatus) {
						updateRequirement(clazz, method, dua, currentTestFailed);
					}

				}
			}
		}
	}

	private void updateRequirement(IDuaClassCoverage clazz, IDuaMethodCoverage method, IDua dua, boolean failed) {
		AbstractTestRequirement testRequirement = new DuaTestRequirement(clazz.getName(), dua.getDef(), dua.getUse(),
				dua.getTarget(), dua.getVar());
		AbstractTestRequirement foundRequirement = testRequirements.get(testRequirement.hashCode());

		if (foundRequirement == null) {
			testRequirement.setClassFirstLine(0);
			testRequirement.setMethodLine(dua.getDef()); 
			testRequirement.setMethodSignature(Signature.toString(method.getDesc(), method.getName(), null, false, true));
			testRequirement.setMethodId(method.getId());
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

	private void collectLineCoverage(boolean currentTestFailed, CoverageBuilder coverageVisitor) {
		for (IClassCoverage clazz : coverageVisitor.getClasses()) {
			CoverageStatus coverageStatus = CoverageStatus.as(clazz.getClassCounter().getStatus());
			if (CoverageStatus.FULLY_COVERED == coverageStatus || CoverageStatus.PARTLY_COVERED == coverageStatus) {
				int firstLine = clazz.getFirstLine();
				int lastLine = clazz.getLastLine();
				if (firstLine >= 0) {
					for (int currentLine = firstLine; currentLine <= lastLine; currentLine++) {
						ILine line = clazz.getLine(currentLine);
						coverageStatus = CoverageStatus.as(line.getStatus());
						if (CoverageStatus.FULLY_COVERED == coverageStatus
								|| CoverageStatus.PARTLY_COVERED == coverageStatus) {
							updateRequirement(clazz, currentLine, currentTestFailed);
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
	 * @param clazz
	 *            the class name, including package
	 * @param lineNumber
	 *            the line number
	 * @param failed
	 *            if the test has failed
	 * 
	 */
	private void updateRequirement(IClassCoverage clazz, int lineNumber, boolean failed) {
		AbstractTestRequirement testRequirement = new LineTestRequirement(clazz.getName(), lineNumber);
		AbstractTestRequirement foundRequirement = testRequirements.get(testRequirement.hashCode());

		if (foundRequirement == null) {
			testRequirement.setClassFirstLine(clazz.getFirstLine());
			Collection<IMethodCoverage> methods = clazz.getMethods();
			Integer methodId = 0;
			for (IMethodCoverage method : methods) {
				methodId++;
				if (method.getLine(lineNumber) != org.jacoco.core.internal.analysis.LineImpl.EMPTY) {
					testRequirement.setMethodLine(method.getFirstLine());
					testRequirement.setMethodSignature(Signature.toString(method.getDesc(), method.getName(), null, false, true));
					testRequirement.setMethodId(methodId);
				}
			}
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
	 * @return
	 * 
	 */
	public ArrayList<AbstractTestRequirement> generateRank() {
		System.out.println("Rank calculation started...");
		HeuristicCalculator calc = new HeuristicCalculator(currentHeuristic, testRequirements.values(), nTests - nTestsFailed,
				nTestsFailed);
		ArrayList<AbstractTestRequirement> result = calc.calculateRank();
		System.out.println("Rank calculation finished.");
		return result;
	}

	// TODO javadoc
	/**
	 * 
	 * @param testRequirements
	 * @param projectDir
	 */
	public void generateXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir) {
		generateXML(testRequirements, projectDir, XML_NAME);
	}
	
	// TODO javadoc
	/**
	 * 
	 * @param testRequirements
	 * @param projectDir
	 */
	public void generateXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir, String fileName) {
		System.out.println("XML generation started.");
		
		CodeForestXmlBuilder xmlBuilder = createXmlBuilder(testRequirements);
		for (AbstractTestRequirement testRequirement : testRequirements) {
			if (testRequirement.getSuspiciousness() > 0) xmlBuilder.addTestRequirement(testRequirement);
		}
		
		projectDir = new File(projectDir.getPath() + System.getProperty("file.separator") + FOLDER_NAME);
		if (!projectDir.exists()){
			projectDir.mkdirs();
		}
		
		File xmlFile = new File(projectDir.getAbsolutePath() + System.getProperty("file.separator") + fileName + ".xml");
		JAXB.marshal(xmlBuilder.build(), xmlFile);
		
		System.out.println("XML generation finished at: " + xmlFile.getAbsolutePath());
	}
	
	private CodeForestXmlBuilder createXmlBuilder(ArrayList<AbstractTestRequirement> testRequirements) {
		CodeForestXmlBuilder xmlBuilder = new CodeForestXmlBuilder();
		xmlBuilder.project("fault localization");
		xmlBuilder.heuristic(currentHeuristic);
		xmlBuilder.timeSpent(System.currentTimeMillis() - startTime);
		setType(testRequirements, xmlBuilder);
		return xmlBuilder;
	}
	
	private void setType(ArrayList<AbstractTestRequirement> testRequirements, CodeForestXmlBuilder xmlBuilder) {
		if (testRequirements.isEmpty()){
			return;
		}
		
		if (AbstractTestRequirement.Type.LINE == testRequirements.iterator().next().getType()){
			xmlBuilder.requirementType(Requirement.Type.LINE);
		}else if(AbstractTestRequirement.Type.DUA == testRequirements.iterator().next().getType()){
			xmlBuilder.requirementType(Requirement.Type.DUA);
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

	public void setCurrentHeuristic(Heuristic currentHeuristic) {
		this.currentHeuristic = currentHeuristic;
	}

} 
