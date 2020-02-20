package br.usp.each.saeg.jaguar.core;

import java.util.Collection;
import java.util.HashMap;

import br.usp.each.saeg.badua.core.analysis.ClassCoverage;
import br.usp.each.saeg.badua.core.analysis.MethodCoverage;
import br.usp.each.saeg.badua.core.analysis.SourceLineDefUseChain;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.Signature;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.IMethodCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

/**
 * This class store the SFL coverage information.
 * 
 * @author Henrique Ribeiro
 */
public class JaguarSFL {

	private final static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private Integer duaIndex;

	private HashMap<AbstractTestRequirement, AbstractTestRequirement> testRequirements = new HashMap<>();

	public JaguarSFL() {
		this.duaIndex = 0;
	}

	/**
	 * Update the testRequirement info. If it does not exist, create a new one.
	 * If the test has failed, increment the cef (executed and failed coefficient)
	 * If the test has passed, increment the cep (executed and passed coefficient)
	 */
	public void updateRequirement(ClassCoverage clazz, MethodCoverage method, SourceLineDefUseChain dua, boolean failed) {
		if (dua.var.startsWith("random_")) {
			return;
		}

		AbstractTestRequirement testRequirement = new DuaTestRequirement(
				clazz.getName(),
				this.duaIndex++,
				dua.def,
				dua.use,
				dua.target,
				dua.var);

		AbstractTestRequirement foundRequirement = testRequirements.get(testRequirement);
		
		if (foundRequirement == null) {
			testRequirement.setClassFirstLine(0);
			testRequirement.setMethodLine(dua.def);
			
			String methodSignature = Signature.toString(method.getDesc(), method.getName(), null, false, true);		
			testRequirement.setMethodSignature(extractName(methodSignature, clazz.getName()));
//			testRequirement.setMethodId(method.getId());
			testRequirements.put(testRequirement, testRequirement);
		}
		else {
			testRequirement = foundRequirement;
		}

		if (failed) {
			testRequirement.increaseFailed();
		}
		else {
			testRequirement.increasePassed();
		}

		logger.trace("Added information from covered dua to TestRequirement {}", testRequirement.toString());
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
	public void updateRequirement(IClassCoverage clazz, int lineNumber, boolean failed) {
		AbstractTestRequirement testRequirement = new LineTestRequirement(clazz.getName(), lineNumber);
		AbstractTestRequirement foundRequirement = testRequirements.get(testRequirement);

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
			testRequirements.put(testRequirement, testRequirement);
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
	 * @return the testRequirements
	 */
	public HashMap<AbstractTestRequirement, AbstractTestRequirement> getTestRequirements() {
		return testRequirements;
	}


	/**
	 * @param testRequirements the testRequirements to set
	 */
	public void setTestRequirements(HashMap<AbstractTestRequirement, AbstractTestRequirement> testRequirements) {
		this.testRequirements = testRequirements;
	}
	
}
