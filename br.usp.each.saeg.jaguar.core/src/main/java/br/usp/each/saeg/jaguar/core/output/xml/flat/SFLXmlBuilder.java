package br.usp.each.saeg.jaguar.core.output.xml.flat;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.codeforest.model.TestCriteria;

public class SFLXmlBuilder {

	private String project;
	private Heuristic heuristic;
	private Requirement.Type requirementType;
	private Long timeSpent;

	private Collection<Requirement> requirements = new ArrayList<Requirement>();
	
	public SFLXmlBuilder() {
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
		addRequirement(testRequirement);
	}


	/**
	 * Create a requirement.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 */
	private void addRequirement(AbstractTestRequirement testRequirement) {
		if (testRequirement instanceof DuaTestRequirement) {

			DuaTestRequirement duaRequirement = (DuaTestRequirement) testRequirement;
			DuaRequirement requirement = new DuaRequirement();

			requirement.setDef(duaRequirement.getDef());
			requirement.setUse(duaRequirement.getUse());
			requirement.setTarget(duaRequirement.getTarget());
			requirement.setVar(duaRequirement.getVar());

			requirement.setName(duaRequirement.getClassName());	//String.format("%s : (%s,", args)duaRequirement.getClassName() + " (" + duaRequirement.getVar() +);
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(duaRequirement.getCef());
			requirement.setCep(duaRequirement.getCep());
			requirement.setCnf(duaRequirement.getCnf());
			requirement.setCnp(duaRequirement.getCnp());
			
			requirements.add(requirement);
		} else if (testRequirement instanceof LineTestRequirement) {

			LineTestRequirement lineRequirement = (LineTestRequirement) testRequirement;
			LineRequirement requirement = new LineRequirement();

			requirement.setName(lineRequirement.getClassName());
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(lineRequirement.getCef());
			requirement.setCep(lineRequirement.getCep());
			requirement.setCnf(lineRequirement.getCnf());
			requirement.setCnp(lineRequirement.getCnp());
			
			requirements.add(requirement);
		}
	}

	/**
	 * Create the object used to generate the CodeForest xml.
	 */
	public FaultClassification build() {

		TestCriteria testCriteria = new TestCriteria();
		if (heuristic != null) {
			testCriteria.setHeuristicType(StringUtils.upperCase(StringUtils
					.removeEndIgnoreCase(heuristic.getClass().getSimpleName(),
							"heuristic")));
		}
		testCriteria.setTimeSpent(timeSpent);
		testCriteria.setRequirementType(requirementType);
		//testCriteria.setRequirements(requirements);

		FaultClassification faultClassification = new FaultClassification();
		faultClassification.setProject(project);
		faultClassification.setTestCriteria(testCriteria);

		return faultClassification;
	}

}
