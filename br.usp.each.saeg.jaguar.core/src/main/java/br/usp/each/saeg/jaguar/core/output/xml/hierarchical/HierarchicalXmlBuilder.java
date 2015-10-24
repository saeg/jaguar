package br.usp.each.saeg.jaguar.core.output.xml.hierarchical;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class HierarchicalXmlBuilder {

	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private Integer methodPosition = 1;
	private String project;
	private Heuristic heuristic;
	private Requirement.Type requirementType;
	private Long timeSpent;

	private Integer absolutePosition = 1;
	private Integer tiedPosition = 1;
	private Double previousSuspicious = 1D;
	
	private Map<Integer, Package> packageMap = new HashMap<Integer, Package>();

	public HierarchicalXmlBuilder() {
		super();
	}

	/**
	 * Set the project name.
	 */
	public void project(String project) {
		this.project = project;
	}

	/**
	 * Set the Heuristic used to calculate the suspicious value.
	 */
	public void heuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Set the type of requirement (e.g Line, Node, Dua)
	 */
	public void requirementType(Requirement.Type requirementType) {
		this.requirementType = requirementType;
	}
	
	/**
	 * Set the total time spent to calculate everything.
	 */
	public void timeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}

	/**
	 * Add the test requirement to the code forest structure.
	 */
	public void addTestRequirement(AbstractTestRequirement testRequirement) {
		addRequirement(testRequirement,
				addMethod(testRequirement,
						addClass(testRequirement,
								addPackage(testRequirement))));
	}

	/**
	 * If the package does not exist, create. If it already exist, return it.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @return the package
	 */
	private Package addPackage(AbstractTestRequirement testRequirement) {
		String packageName = getPackageName(testRequirement.getClassName());
		Package currentPackage = packageMap.get(packageName.hashCode());
		if (currentPackage == null) {
			currentPackage = new Package();
			currentPackage.setName(packageName);
			packageMap.put(packageName.hashCode(), currentPackage);
		}
		return currentPackage;
	}

	/**
	 * If the class does not exist, create and add it to the given package. If
	 * it already exist, only add it to the package.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param pakkage
	 *            the package to add the class
	 * @return
	 */
	private Class addClass(AbstractTestRequirement testRequirement,
			Package pakkage) {
		String className = replaceSlashByDot(testRequirement.getClassName());
		Class currentClass = null;
		for (Class clazz : pakkage.getClasses()) {
			if (clazz.getName().equals(className)) {
				currentClass = clazz;
			}
		}

		if (currentClass == null) {
			currentClass = new Class();
			currentClass.setName(className);
			currentClass.setLocation(new Integer(testRequirement
					.getClassFirstLine()));
			pakkage.getClasses().add(currentClass);
		}
		return currentClass;
	}

	/**
	 * If the method does not exist, create and add it to the given class. If it
	 * already exist, only add it to the class.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param currentClass
	 *            the class to add the method.
	 * 
	 * @return return the method
	 */
	private Method addMethod(AbstractTestRequirement testRequirement,
			Class currentClass) {
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
			currentMethod.setId(testRequirement.getMethodId());
			currentMethod.setPosition(methodPosition++);
			currentClass.getMethods().add(currentMethod);
		}
		currentMethod.setLocation(testRequirement.getMethodLine());
		return currentMethod;
	}

	/**
	 * Create a requirement and add it to the given method.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param currentMethod
	 *            the method to add the requirement.
	 */
	private void addRequirement(AbstractTestRequirement testRequirement, Method currentMethod) {
		
		if (testRequirement instanceof DuaTestRequirement) {
			
			logger.trace("Adding DuaTestRequirement requirement to HierarchicalXmlBuilder {}", testRequirement.toString());
			DuaTestRequirement duaRequirement = (DuaTestRequirement) testRequirement;
			DuaRequirement requirement = new DuaRequirement();

			requirement.setNumber(getPosition(testRequirement.getSuspiciousness()));
			Integer firstDefLine = duaRequirement.getDef();
			requirement.setName(firstDefLine.toString());
			requirement.setLocation(firstDefLine);

			requirement.setIndex(duaRequirement.getIndex());
			requirement.setDef(duaRequirement.getDef());
			requirement.setUse(duaRequirement.getUse());
			requirement.setTarget(duaRequirement.getTarget());
			requirement.setVar(duaRequirement.getVar());
			
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(duaRequirement.getCef());
			requirement.setCep(duaRequirement.getCep());
			requirement.setCnf(duaRequirement.getCnf());
			requirement.setCnp(duaRequirement.getCnp());

			currentMethod.getRequirements().add(requirement);
			logger.trace("Added DuaRequirement to HierarchicalXmlBuilder {}", requirement.toString());

		} else if (testRequirement instanceof LineTestRequirement) {
			
			logger.trace("Adding LineTestRequirement requirement to HierarchicalXmlBuilder {}", testRequirement.toString());

			LineTestRequirement lineRequirement = (LineTestRequirement) testRequirement;
			LineRequirement requirement = new LineRequirement();

			requirement.setNumber(getPosition(testRequirement.getSuspiciousness()));
			requirement.setName(lineRequirement.getLineNumber().toString());
			requirement.setLocation(lineRequirement.getLineNumber());
			
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(lineRequirement.getCef());
			requirement.setCep(lineRequirement.getCep());
			requirement.setCnf(lineRequirement.getCnf());
			requirement.setCnp(lineRequirement.getCnp());

			currentMethod.getRequirements().add(requirement);
			logger.trace("Added LineRequirement to HierarchicalXmlBuilder {}", requirement.toString());
			
		} else {
			logger.error("Unknown TestRequirement, it will not be added to HierarchicalXmlBuilder - {}", testRequirement.toString());
		}
	}
	
	private Integer getPosition(double currentSuspicious) {
		if (previousSuspicious.equals(currentSuspicious)){
			absolutePosition++;
		}else{
			previousSuspicious = currentSuspicious;
			tiedPosition = absolutePosition++;
		}
		return tiedPosition;
	}


	/**
	 * Replace '\' by '.'. and remove last word (the class name).
	 * 
	 * @param className
	 *            class name including package
	 * @return only the package
	 */
	private String getPackageName(String className) {
		className = replaceSlashByDot(className);
		return StringUtils.substringBeforeLast(className, ".");
	}

	private String replaceSlashByDot(String className) {
		return className.replace('/', '.');
	}

	/**
	 * Create the object used to generate the CodeForest xml.
	 */
	public HierarchicalFaultClassification build() {

		for (Package currentPackage : packageMap.values()) {
			setSuspicous(currentPackage);
		}

		HierarchicalFaultClassification faultClassification = new HierarchicalFaultClassification();
		if (heuristic != null) {
			faultClassification.setHeuristic(StringUtils.upperCase(StringUtils
					.removeEndIgnoreCase(heuristic.getClass().getSimpleName(),
							"heuristic")));
		}
		faultClassification.setTimeSpent(timeSpent);
		faultClassification.setRequirementType(requirementType);
		faultClassification.setPackages(packageMap.values());
		faultClassification.setProject(project);

		return faultClassification;
	}

	/**
	 * Set the suspicious value based on the children. The object will have its
	 * children maximum suspicious value.
	 * 
	 * @param element
	 */
	private void setSuspicous(SuspiciousElement element) {
		for (SuspiciousElement child : element.getChildren()) {
			setSuspicous(child);
			element.updateSupicousness(child.getSuspiciousValue(),
					child.getNumber());
		}
	}

}
