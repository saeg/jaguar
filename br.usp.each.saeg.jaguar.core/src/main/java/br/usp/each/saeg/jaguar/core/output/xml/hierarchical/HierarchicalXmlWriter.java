package br.usp.each.saeg.jaguar.core.output.xml.hierarchical;

import java.util.ArrayList;

import br.usp.each.saeg.jaguar.core.output.xml.XmlBuilder;
import br.usp.each.saeg.jaguar.core.output.xml.XmlWriter;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;

public class HierarchicalXmlWriter extends XmlWriter {

	public HierarchicalXmlWriter(ArrayList<AbstractTestRequirement> testRequirements, Heuristic currentHeuristic, Long coverageTime) {
		super(testRequirements, currentHeuristic, coverageTime);
	}

	@Override
	protected XmlBuilder createXmlBuilder() {
		XmlBuilder xmlBuilder = new HierarchicalXmlBuilder();
		xmlBuilder.project("fault localization");
		xmlBuilder.heuristic(currentHeuristic);
		xmlBuilder.timeSpent(coverageTime);
		xmlBuilder.requirementType(getType());

		for (AbstractTestRequirement testRequirement : testRequirements) {
			if (Math.abs(testRequirement.getSuspiciousness()) > 0){
				xmlBuilder.addTestRequirement(testRequirement);
			}
		}

		return xmlBuilder;
	}
}
