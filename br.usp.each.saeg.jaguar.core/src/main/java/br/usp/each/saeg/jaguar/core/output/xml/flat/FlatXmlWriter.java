package br.usp.each.saeg.jaguar.core.output.xml.flat;

import java.util.ArrayList;

import br.usp.each.saeg.jaguar.core.output.xml.XmlBuilder;
import br.usp.each.saeg.jaguar.core.output.xml.XmlWriter;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;

public class FlatXmlWriter extends XmlWriter {

	public FlatXmlWriter(ArrayList<AbstractTestRequirement> testRequirements, Heuristic currentHeuristic, Long coverageTime) {
		super(testRequirements, currentHeuristic, coverageTime);
	}

	@Override
	protected XmlBuilder createXmlBuilder() {
		XmlBuilder xmlBuilder = new FlatXmlBuilder();
		xmlBuilder.project("fault localization");
		xmlBuilder.heuristic(currentHeuristic);
		xmlBuilder.timeSpent(coverageTime);
		xmlBuilder.requirementType(getType());

		for (AbstractTestRequirement testRequirement : testRequirements) {
			xmlBuilder.addTestRequirement(testRequirement);
		}

		return xmlBuilder;
	}

}