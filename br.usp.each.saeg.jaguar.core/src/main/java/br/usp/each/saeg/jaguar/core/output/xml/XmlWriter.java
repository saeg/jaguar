package br.usp.each.saeg.jaguar.core.output.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

public abstract class XmlWriter {

    private static Logger logger = LoggerFactory.getLogger("JaguarLogger");
    private static final String FOLDER_NAME = ".jaguar";

    protected ArrayList<AbstractTestRequirement> testRequirements;
    protected Heuristic currentHeuristic;
    protected Long coverageTime;

    public XmlWriter(ArrayList<AbstractTestRequirement> testRequirements, Heuristic currentHeuristic, Long coverageTime) {
        this.testRequirements = testRequirements;
        this.currentHeuristic = currentHeuristic;
        this.coverageTime = coverageTime;
    }

    public void generateXML(File projectDir, String fileName) {
        XmlBuilder xmlBuilder = createXmlBuilder();
        File xmlFile = write(xmlBuilder, projectDir, fileName);
        logger.info("Output xml created at: {}", xmlFile.getAbsolutePath());
    }

    private File write(XmlBuilder xmlBuilder, File projectDir, String fileName) {
        projectDir = new File(projectDir.getPath() + System.getProperty("file.separator") + FOLDER_NAME);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }

        File xmlFile = new File(projectDir.getAbsolutePath() + System.getProperty("file.separator") + fileName);
        JAXB.marshal(xmlBuilder.build(), xmlFile);

        return xmlFile;
    }

    protected Type getType() {
        if (testRequirements.isEmpty()) {
            return null;
        }

        if (AbstractTestRequirement.Type.LINE == testRequirements.iterator().next().getType()) {
            return Requirement.Type.LINE;
        }

        if (AbstractTestRequirement.Type.DUA == testRequirements.iterator().next().getType()) {
            return Requirement.Type.DUA;
        }

        return null;
    }

    protected abstract XmlBuilder createXmlBuilder();
}
