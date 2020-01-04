package br.usp.each.saeg.jaguar.core.output.xml.flat;

import java.util.ArrayList;
import java.util.List;

import br.usp.each.saeg.jaguar.codeforest.model.*;
import br.usp.each.saeg.jaguar.core.output.xml.XmlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class FlatXmlBuilder extends XmlBuilder {

	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private List<Requirement> requirements = new ArrayList<Requirement>();

	/**
	 * Add the test requirement to the code forest structure.
	 */
	@Override
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
		}
		else if (testRequirement instanceof LineTestRequirement) {
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
			logger.trace("Added LineRequirement to FlatXmlBuilder {}", requirement.toString());
		}
		else {
			logger.error("Unknown TestRequirement, it will not be added to FlatXmlBuilder - {}", testRequirement.toString());
		}
	}

	/**
	 * Create the object used to generate the CodeForest xml.
	 */
	@Override
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
