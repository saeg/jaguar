package br.usp.each.saeg.jaguar.core.output.xml.flat;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.FlatFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.TestCriteria;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class SFLXmlBuilder {

	private  Logger logger = LoggerFactory.getLogger("JaguarLogger");

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
			logger.debug("Adding DuaTestRequirement requirement to FlatXmlBuilder {}", testRequirement.toString());
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
			logger.debug("Adding LineTestRequirement requirement to FlatXmlBuilder {}", testRequirement.toString());
			LineTestRequirement lineRequirement = (LineTestRequirement) testRequirement;
			LineRequirement requirement = new LineRequirement();

			requirement.setName(lineRequirement.getClassName());
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(lineRequirement.getCef());
			requirement.setCep(lineRequirement.getCep());
			requirement.setCnf(lineRequirement.getCnf());
			requirement.setCnp(lineRequirement.getCnp());
			
			requirements.add(requirement);
		} else {
			logger.error("Unknown TestRequirement, it will not be added to FlatXmlBuilder - {}", testRequirement.toString());
		}
	}

	/**
	 * Create the object used to generate the CodeForest xml.
	 */
	public FlatFaultClassification build() {

		FlatFaultClassification faultClassification = new FlatFaultClassification();
		if (heuristic != null) {
			faultClassification.setHeuristic(StringUtils.upperCase(StringUtils
					.removeEndIgnoreCase(heuristic.getClass().getSimpleName(),
							"heuristic")));
		}
		faultClassification.setTimeSpent(timeSpent);
		faultClassification.setRequirementType(requirementType);
		faultClassification.setRequirements(requirements);
		faultClassification.setProject(project);

		return faultClassification;
	}

}
