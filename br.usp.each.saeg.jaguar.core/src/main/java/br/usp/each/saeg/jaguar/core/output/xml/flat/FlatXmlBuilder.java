package br.usp.each.saeg.jaguar.core.output.xml.flat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.FlatFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class FlatXmlBuilder {

	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private String project;
	private Heuristic heuristic;
	private Requirement.Type requirementType;
	private Long timeSpent;
	
	private Integer absolutePosition = 1;
	private Integer tiedPosition = 1;
	private Double previousSuspicious = 1D;
	
	private List<Requirement> requirements = new ArrayList<Requirement>();
	
	public FlatXmlBuilder() {
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
			
			logger.trace("Adding DuaTestRequirement requirement to FlatXmlBuilder {}", testRequirement.toString());
			DuaTestRequirement duaRequirement = (DuaTestRequirement) testRequirement;
			DuaRequirement requirement = new DuaRequirement();

			requirement.setNumber(getPosition(testRequirement.getSuspiciousness()));
			requirement.setLocation(duaRequirement.getDef());
			requirement.setName(duaRequirement.getClassName().replace('/', '.'));
			
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
			
			requirements.add(requirement);
			logger.trace("Added DuaRequirement to FlatXmlBuilder {}", requirement.toString());

		} else if (testRequirement instanceof LineTestRequirement) {
			
			logger.trace("Adding LineTestRequirement requirement to FlatXmlBuilder {}", testRequirement.toString());
			LineTestRequirement lineRequirement = (LineTestRequirement) testRequirement;
			LineRequirement requirement = new LineRequirement();

			requirement.setNumber(getPosition(testRequirement.getSuspiciousness()));
			requirement.setLocation(lineRequirement.getLineNumber());
			requirement.setName(lineRequirement.getClassName().replace('/', '.'));

			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());
			requirement.setCef(lineRequirement.getCef());
			requirement.setCep(lineRequirement.getCep());
			requirement.setCnf(lineRequirement.getCnf());
			requirement.setCnp(lineRequirement.getCnp());
			
			requirements.add(requirement);
			logger.trace("Added LineRequirement to FlatlXmlBuilder {}", requirement.toString());
			
		} else {
			logger.error("Unknown TestRequirement, it will not be added to FlatXmlBuilder - {}", testRequirement.toString());
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
