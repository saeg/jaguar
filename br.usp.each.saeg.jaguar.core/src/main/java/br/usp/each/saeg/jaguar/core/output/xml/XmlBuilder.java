package br.usp.each.saeg.jaguar.core.output.xml;

import br.usp.each.saeg.jaguar.codeforest.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;

public abstract class XmlBuilder {

    private static Logger logger = LoggerFactory.getLogger("JaguarLogger");

    protected String project;
    protected Heuristic heuristic;
    protected Requirement.Type requirementType;
    protected Long timeSpent;

    private Integer absolutePosition = 1;
    private Integer tiedPosition = 1;
    private Double previousSuspicious = 1D;

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

    protected Integer getPosition(double currentSuspicious) {
        if (previousSuspicious.equals(currentSuspicious)) {
            absolutePosition++;
        }
        else {
            previousSuspicious = currentSuspicious;
            tiedPosition = absolutePosition++;
        }

        return tiedPosition;
    }

    /**
     * Add the test requirement to the code forest structure.
     */
    public abstract void addTestRequirement(AbstractTestRequirement testRequirement);

    /**
     * Create the object used to generate the CodeForest xml.
     */
    public abstract FaultClassification build();
}
