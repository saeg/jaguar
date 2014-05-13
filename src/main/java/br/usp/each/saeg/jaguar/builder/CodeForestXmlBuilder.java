package br.usp.each.saeg.jaguar.builder;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.model.codeforest.Class;
import br.usp.each.saeg.jaguar.model.codeforest.FaultClassification;
import br.usp.each.saeg.jaguar.model.codeforest.Method;
import br.usp.each.saeg.jaguar.model.codeforest.Package;
import br.usp.each.saeg.jaguar.model.codeforest.Requirement;
import br.usp.each.saeg.jaguar.model.codeforest.TestCriteria;
import br.usp.each.saeg.jaguar.model.core.TestRequirement;

public class CodeForestXmlBuilder {

	private Integer methodPosition = 1;
	private String project;
	private Heuristic heuristic;
	private String requirementType;
	private Map<Integer, Package> packageMap = new HashMap<Integer, Package>();

	public CodeForestXmlBuilder() {
		super();
	}

	public void project(String project) {
		this.project = project;
	}

	public void heuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	public void requirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public void addTestRequirement(TestRequirement testRequirement) {
		String packageName = getPackageName(testRequirement.getClassName());
		Package currentPackage = packageMap.get(packageName.hashCode());
		if (currentPackage == null) {
			currentPackage = new Package();
			currentPackage.setName(packageName);
			packageMap.put(packageName.hashCode(), currentPackage);
		}

		String className = getClassName(testRequirement.getClassName());
		Class currentClass = null;
		for (Class clazz : currentPackage.getClasses()) {
			if (clazz.getName().equals(className)) {
				currentClass = clazz;
			}
		}

		if (currentClass == null) {
			currentClass = new Class();
			currentClass.setName(className);
			currentClass.setLocation(new Integer(testRequirement.getClassFirstLine()));
			currentPackage.getClasses().add(currentClass);
		}

		String methodName = testRequirement.getMethodSignature();
		Method currentMethod = null;
		for (Method method : currentClass.getMethods()) {
			if (method.getName().equals(methodName)) {
				currentMethod = method;
			}
		}

		if (currentMethod == null) {
			currentMethod = new Method();
			currentMethod.setName(methodName);
			currentMethod.setLocation(testRequirement.getMethodLine());
			currentMethod.setId(testRequirement.getMethodId());
			currentMethod.setPosition(methodPosition++);
			currentClass.getMethods().add(currentMethod);
		}

		Requirement requirement = new Requirement();
		requirement.setName(testRequirement.getLineNumber().toString());
		requirement.setLocation(testRequirement.getLineNumber());
		requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
		currentMethod.getRequirements().add(requirement);
	}

	private String getClassName(String className) {
		return className.replace('/', '.');
	}

	private String getPackageName(String className) {
		className = className.replace('/', '.');
		return StringUtils.substringBeforeLast(className, ".");
	}

	public FaultClassification build() {

		Double maxSuspPackage = 0.0;
		Integer maxNumPackage = 0;
		for (Package currentPackage : packageMap.values()) {
			Double maxSuspClass = 0.0;
			Integer maxNumClass = 0;
			for (Class currentClass : currentPackage.getClasses()) {
				Double maxSuspMethod = 0.0;
				Integer maxNumMethod = 0;
				for (Method currentMethod : currentClass.getMethods()) {
					Double maxSuspReq = 0.0;
					Integer maxNumReq = 0;
					for (Requirement currentRequirement : currentMethod.getRequirements()) {
						if (currentRequirement.getSuspiciousValue() > maxSuspReq) {
							maxSuspReq = currentRequirement.getSuspiciousValue();
							maxNumReq = 1;
						} else if (currentRequirement.getSuspiciousValue().equals(maxSuspReq)) {
							maxNumReq++;
						}
					}
					currentMethod.setSuspiciousValue(maxSuspReq);
					currentMethod.setMethodsusp(maxSuspReq);
					currentMethod.setNumber(maxNumReq);
					if (currentMethod.getSuspiciousValue() > maxSuspMethod) {
						maxSuspMethod = currentMethod.getSuspiciousValue();
						maxNumMethod = currentMethod.getNumber();
					} else if (currentMethod.getSuspiciousValue().equals(maxSuspMethod)) {
						maxNumMethod += currentMethod.getNumber();
					}
				}
				currentClass.setSuspiciousValue(maxSuspMethod);
				currentClass.setNumber(maxNumMethod);
				if (currentClass.getSuspiciousValue() > maxSuspClass) {
					maxSuspClass = currentClass.getSuspiciousValue();
					maxNumClass = currentClass.getNumber();
				} else if (currentClass.getSuspiciousValue().equals(maxSuspClass)) {
					maxNumClass += currentClass.getNumber();
				}
			}
			currentPackage.setSuspiciousValue(maxSuspClass);
			currentPackage.setNumber(maxNumClass);
			if (currentPackage.getSuspiciousValue() > maxSuspPackage) {
				maxSuspPackage = currentPackage.getSuspiciousValue();
				maxNumPackage = currentPackage.getNumber();
			} else if (currentPackage.getSuspiciousValue().equals(maxSuspPackage)) {
				maxNumPackage += currentPackage.getNumber();
			}
		}

		TestCriteria testCriteria = new TestCriteria();
		testCriteria.setHeuristicType(StringUtils.upperCase(StringUtils.removeEndIgnoreCase(heuristic.getClass()
				.getSimpleName(), "heuristic")));
		testCriteria.setRequirementType(requirementType);
		testCriteria.setPackages(packageMap.values());

		FaultClassification faultClassification = new FaultClassification();
		faultClassification.setProject(project);
		faultClassification.setTestCriteria(testCriteria);

		return faultClassification;

	}

}
