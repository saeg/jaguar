package br.usp.each.saeg.jaguar.main;

import java.io.File;
import java.io.IOException;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;

import br.usp.each.saeg.jaguar.core.LineCoverageStatus;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.DRTHeuristic;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

public class Jaguar {

	public static void main(final String[] args) throws IOException {
		Heuristic heuristic = new DRTHeuristic();
		heuristic.eval(1, 2, 3, 3);

		JacocoTCPClient tcpClient = new JacocoTCPClient();
		Jaguar jaguar = new Jaguar();
		jaguar.analyze(tcpClient.read());
		
		heuristic.eval(0,0,0,0);
		jaguar.analyze(tcpClient.read());
		
		tcpClient.closeSocket();
	}

	public void analyze(final ExecutionDataStore executionData)
			throws IOException {
		final CoverageBuilder coverageVisitor = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageVisitor);
		int nClass = analyzer.analyzeAll(new File("/home/unknown/workspace/jaguar/target/classes/"));
		System.out.println(nClass + " classes analizadas");

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
