package br.usp.each.saeg.jaguar.main;

import java.io.File;
import java.io.IOException;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;

import br.usp.each.saeg.jaguar.core.LineCoverageStatus;

public class Jaguar {

	public void analyse(final ExecutionDataStore executionData) {
		final CoverageBuilder coverageVisitor = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageVisitor);
		int nClass = 0;
		try {
			nClass = analyzer.analyzeAll(new File("/home/unknown/workspace/jaguar/target/classes/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(nClass + " classes analisadas");

		for (IClassCoverage clazz : coverageVisitor.getClasses()) {
			int firstLine = clazz.getFirstLine();
			int lastLine = clazz.getLastLine();
			if (firstLine >= 0) {
				for (int i = firstLine; i <= lastLine; i++) {
					ILine line = clazz.getLine(i);
					LineCoverageStatus coverageStatus = LineCoverageStatus.as(line.getStatus());
					if (LineCoverageStatus.FULLY_COVERED == coverageStatus || 
							LineCoverageStatus.PARTLY_COVERED == coverageStatus){
						System.out.println(clazz.getName() + "[" + i + "]="	+ coverageStatus);
					}
				}
			}
		}
	}

}
