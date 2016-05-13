package br.usp.each.saeg.jaguar.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.Signature;
import org.jacoco.core.analysis.AbstractAnalyzer;
import org.jacoco.core.analysis.ControlFlowAnalyzer;
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
import org.jacoco.core.data.ControlFlowExecutionData;
import org.jacoco.core.data.ControlFlowExecutionDataStore;
import org.jacoco.core.data.DataFlowExecutionDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.HeuristicCalculator;
import br.usp.each.saeg.jaguar.core.model.core.CoverageStatus;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;
import br.usp.each.saeg.jaguar.core.output.xml.flat.FlatXmlWriter;
import br.usp.each.saeg.jaguar.core.output.xml.hierarchical.HierarchicalXmlWriter;

/**
 * This class store the coverage information received from Jacoco and generate a
 * suspicious test requirement rank based on one SFL Heuristic.
 * 
 * @author Henrique Ribeiro
 */
public class Jaguar {

	private final static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private static final String XML_NAME = "jaguar_output";
	private int nTests = 0;
	private int nTestsFailed = 0;
	private HashMap<Integer, AbstractTestRequirement> testRequirements = new HashMap<Integer, AbstractTestRequirement>();
	private Heuristic currentHeuristic;
	private Map<String, File> classFilesCache;

	private Long startTime;
	private Long totalTimeSpent;

	/**
	 * Construct the Jaguar object.
	 * 
	 * @param heuristic
	 *            the heuristic to be used on the fault localization rank.
	 * @param targetDir
	 *            the target dir created by eclipse
	 */
	public Jaguar(Heuristic heuristic, File classesDir) {
		this.currentHeuristic = heuristic;
		this.startTime = System.currentTimeMillis();

		classFilesCache = new HashMap<String, File>();
		populateClassFilesCache(classesDir, "");
		logger.debug("ClassFilesCache size = {}", classFilesCache.size());

	}

	private void populateClassFilesCache(File dir, String path) {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				populateClassFilesCache(file, path + file.getName() + "/");
			} else if (file.getName().endsWith(".class")) {
				String className = path + StringUtils.removeEnd(file.getName(), ".class");
				classFilesCache.put(className, file);
				logger.debug("Added {} to classFilesCache", className);
			}
		}
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
		logger.debug("Test # {}", nTests);
		if (executionData instanceof DataFlowExecutionDataStore) {

			long startTime = System.currentTimeMillis();
			DuaCoverageBuilder duaCoverageBuilder = new DuaCoverageBuilder();
			AbstractAnalyzer analyzer = new DataflowAnalyzer(executionData, duaCoverageBuilder);
			analyzeCoveredClasses(executionData, analyzer);
			logger.debug("Time to analyze DF data: {}", System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			collectDuaCoverage(currentTestFailed, duaCoverageBuilder);
			logger.debug("Time to read and store data: {} , from {} classes", System.currentTimeMillis() - startTime, duaCoverageBuilder
					.getClasses().size());

		} else if (executionData instanceof ControlFlowExecutionDataStore) {

			long startTime = System.currentTimeMillis();
			CoverageBuilder coverageBuilder = new CoverageBuilder();
			AbstractAnalyzer analyzer = new ControlFlowAnalyzer(executionData, coverageBuilder);
			analyzeCoveredClasses(executionData, analyzer);
			logger.debug("Time to analyze CF data: {}", System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			collectLineCoverage(currentTestFailed, coverageBuilder);
			logger.debug("Time to read and store data: {} , from {} classes", System.currentTimeMillis() - startTime, coverageBuilder
					.getClasses().size());

		} else {
			logger.error("Unknown DataStore - {}", executionData.getClass().getName());
		}

	}

	private void analyzeCoveredClasses(AbstractExecutionDataStore executionData, AbstractAnalyzer analyzer) {
		Collection<File> classFiles = classFilesOfStore(executionData);
		for (File classFile : classFiles) {
			try (InputStream inputStream = new FileInputStream(classFile)) {
				analyzer.analyzeClass(inputStream, classFile.getPath());
			} catch (IOException e) {
				logger.warn("Exception during analysis of file " + classFile.getAbsolutePath(), e);
			}
		}
	}

	private Collection<File> classFilesOfStore(AbstractExecutionDataStore executionDataStore) {
		Collection<File> result = new ArrayList<File>();
		for (ControlFlowExecutionData data : executionDataStore.getContents()) {
			String vmClassName = data.getName();
			File classFile = classFilesCache.get(vmClassName);
			if (classFile != null) {
				result.add(classFile);
			}
		}
		return result;
	}

	private void collectDuaCoverage(boolean currentTestFailed, DuaCoverageBuilder coverageVisitor) {
		int totalDuas = 0;
		int totalDuasCovered = 0; 
		for (IDuaClassCoverage clazz : coverageVisitor.getClasses()) {
			logger.debug("Collecting duas from class  {}", clazz.getName());
			for (IDuaMethodCoverage method : clazz.getMethods()) {
				logger.debug("Collecting duas from method  {}", method.getSignature());
				for (IDua dua : method.getDuas()) {
					totalDuas++;
					logger.trace("Collecting information from dua {}", dua);
					CoverageStatus coverageStatus = CoverageStatus.as(dua.getStatus());
					if (CoverageStatus.FULLY_COVERED == coverageStatus) {
						totalDuasCovered++;
						updateRequirement(clazz, method, dua, currentTestFailed);
					}
				}
			}
		}
		logger.debug("#duas = {}, #coveredDuas = {}", totalDuas, totalDuasCovered);
	}

	private void updateRequirement(IDuaClassCoverage clazz, IDuaMethodCoverage method, IDua dua, boolean failed) {
		if (dua.getVar().startsWith("random_")) {
			return;
		}

		AbstractTestRequirement testRequirement = new DuaTestRequirement(clazz.getName(), dua.getIndex(), dua.getDef(), dua.getUse(),
				dua.getTarget(), dua.getVar());
		AbstractTestRequirement foundRequirement = testRequirements.get(testRequirement.hashCode());

		if (foundRequirement == null) {
			testRequirement.setClassFirstLine(0);
			testRequirement.setMethodLine(dua.getDef());
			String methodSignature = Signature.toString(method.getDesc(), method.getName(), null, false, true);
			testRequirement.setMethodSignature(extractName(methodSignature, clazz.getName()));
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
		logger.trace("Added information from covered dua to TestRequirement {}", testRequirement.toString());

	}

	private void collectLineCoverage(boolean currentTestFailed, CoverageBuilder coverageVisitor) {
		int totalLines = 0;
		int totalLinesCovered = 0;
		for (IClassCoverage clazz : coverageVisitor.getClasses()) {
			// logger.debug("Collecting lines from class " + clazz.getName());
			CoverageStatus coverageStatus = CoverageStatus.as(clazz.getClassCounter().getStatus());
			if (CoverageStatus.FULLY_COVERED == coverageStatus || CoverageStatus.PARTLY_COVERED == coverageStatus) {
				int firstLine = clazz.getFirstLine();
				int lastLine = clazz.getLastLine();
				if (firstLine >= 0) {
					for (int currentLine = firstLine; currentLine <= lastLine; currentLine++) {
						totalLines++;
						ILine line = clazz.getLine(currentLine);
						logger.trace("Collecting information from line {}", currentLine);
						coverageStatus = CoverageStatus.as(line.getStatus());
						if (CoverageStatus.FULLY_COVERED == coverageStatus || CoverageStatus.PARTLY_COVERED == coverageStatus) {
							totalLinesCovered++;
							updateRequirement(clazz, currentLine, currentTestFailed);
						}
					}
				}
			}
		}
		logger.debug("#lines = {}, #coveredlines = {}", totalLines, totalLinesCovered);

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
					String methodSignature = Signature.toString(method.getDesc(), method.getName(), null, false, true);
					testRequirement.setMethodSignature(extractName(methodSignature, clazz.getName()));
					testRequirement.setMethodId(methodId);
					break;
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

		logger.trace("Added information from covered line to TestRequirement {}", testRequirement.toString());
	}

	/**
	 * Remove the return value and replace method name by Class name if it is
	 * init();
	 * 
	 * @param methodName
	 *            method complete signature
	 * @param className
	 * @return method name without return value
	 */
	private String extractName(String methodName, String className) {
		methodName = methodName.substring(StringUtils.indexOf(methodName, " ") + 1);
		if (methodName.equals("<init>()")) {
			String[] classNameSplited = className.split("/");
			methodName = classNameSplited[classNameSplited.length - 1] + "()";
		}
		return methodName;
	}

	/**
	 * Calculate the rank based on the heuristic and testRequirements. Return
	 * the rank in descending order.
	 * 
	 * @return the rank in descending order.
	 * 
	 */
	public ArrayList<AbstractTestRequirement> generateRank() {
		logger.debug("Rank calculation started...");
		HeuristicCalculator calc = new HeuristicCalculator(currentHeuristic, testRequirements.values(), nTests - nTestsFailed, nTestsFailed);
		ArrayList<AbstractTestRequirement> result = calc.calculateRank();
		logger.debug("Rank calculation finished.");
		return result;
	}

	/**
	 * Use the given testRequirements to generate the Flat output XML. Using the
	 * default name.
	 * 
	 * @param testRequirements
	 *            the testRequirements
	 * @param projectDir
	 *            the directory in which the output folder and files will be
	 *            written
	 * 
	 */
	public void generateFlatXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir) {
		generateFlatXML(testRequirements, projectDir, XML_NAME);
	}

	/**
	 * Use the given testRequirements to generate the Flat output XML, using the
	 * paramenter fileName.
	 * 
	 * @param testRequirements
	 *            the testRequirements
	 * @param projectDir
	 *            the directory in which the output folder and files will be
	 *            written
	 * @param fileName
	 *            the name of the output xml file
	 * 
	 */
	public void generateFlatXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir, String fileName) {
		FlatXmlWriter xmlWriter = new FlatXmlWriter(testRequirements, currentHeuristic, totalTimeSpent);
		xmlWriter.generateXML(projectDir, fileName);
	}

	/**
	 * Use the given testRequirements to generate the Hierarchical output XML.
	 * Using the default name.
	 * 
	 * @param testRequirements
	 *            the testRequirements
	 * @param projectDir
	 *            the directory in which the output folder and files will be
	 *            written
	 * 
	 */
	public void generateHierarchicalXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir) {
		generateHierarchicalXML(testRequirements, projectDir, XML_NAME);
	}

	/**
	 * Use the given testRequirements to generate the Hierarchical output XML,
	 * using the paramenter fileName.
	 * 
	 * @param testRequirements
	 *            the testRequirements
	 * @param projectDir
	 *            the directory in which the output folder and files will be
	 *            written
	 * @param fileName
	 *            the name of the output xml file
	 * 
	 */
	public void generateHierarchicalXML(ArrayList<AbstractTestRequirement> testRequirements, File projectDir, String fileName) {
		HierarchicalXmlWriter xmlWriter = new HierarchicalXmlWriter(testRequirements, currentHeuristic, totalTimeSpent);
		xmlWriter.generateXML(projectDir, fileName);
	}

	/**
	 * Currently only used to save the total time spent since Jaguar was
	 * created.
	 * 
	 */
	public void finish() {
		totalTimeSpent = System.currentTimeMillis() - startTime;
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
