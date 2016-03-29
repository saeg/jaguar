package br.usp.each.saeg.jaguar.core.results;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class ReportManualRunner {

	public static void main(String[] args) throws FileNotFoundException{
		final String rootFolder = "C:\\Users\\unknown\\workspace\\luna\\runtime-New_configuration\\";
		
		class Program {
			final private String name;
			final private String faultyClass;
			final private Integer faultyLine;
			public Program(String name, String faultyClass, Integer faultyLine) {
				super();
				this.name = name;
				this.faultyClass = faultyClass;
				this.faultyLine = faultyLine;
			}
			/**
			 * @return the name
			 */
			public String getName() {
				return name;
			}

			/**
			 * @return the faultyClass
			 */
			public String getFaultyClass() {
				return faultyClass;
			}

			/**
			 * @return the faultyLine
			 */
			public Integer getFaultyLine() {
				return faultyLine;
			}
		}
		
		Collection<Program> programs = new ArrayList<Program>();
//		programs.add(new Program("chart_1_buggy", "??", ??));
//		programs.add(new Program("jsoup-1_3_4-1", "org.jsoup.nodes.Node", 175));
//		programs.add(new Program("jsoup-1_3_4-2", "org.jsoup.nodes.Entities", 66));
//		programs.add(new Program("jsoup-1_4_2-1", "org.jsoup.nodes.Entities", 76));
//		programs.add(new Program("jsoup-1_4_2-2", "org.jsoup.select.Selector", 139));
//		programs.add(new Program("jsoup-1_5_2-1", "org.jsoup.nodes.Element", 762));
//		programs.add(new Program("jsoup-1_5_2-2", "org.jsoup.nodes.Element", 960));
//		programs.add(new Program("jsoup-1_5_2-3", "org.jsoup.nodes.Element", 732));
//		programs.add(new Program("jsoup-1_5_2-4", "org.jsoup.parser.Tag", 309));
//		programs.add(new Program("jsoup-1_5_2-5", "org.jsoup.select.CombiningEvaluator$Or", 57));
//		programs.add(new Program("jsoup-1_5_3", "org.jsoup.nodes.Node", 73));
//		programs.add(new Program("jsoup-1_6_1-1", "org.jsoup.parser.CharacterReader", 19));
		programs.add(new Program("jsoup-1_6_1-2", "org.jsoup.parser.TreeBuilderState$7", 250));
		//org.jsoup.parser.TreeBuilderState 1451
		//org.jsoup.parser.TreeBuilderState$7 250
		//org.jsoup.parser.TreeBuilderState$10 907
		//org.jsoup.parser.TreeBuilderState$16 1211
//		programs.add(new Program("jsoup-1_6_1-3", "org.jsoup.nodes.DocumentType", 35));
//		programs.add(new Program("jsoup-1_6_1-4", "org.jsoup.parser.CharacterReader", 32));
//		programs.add(new Program("jsoup-1_6_1-5", "org.jsoup.parser.TreeBuilderState$7", 283));
//		programs.add(new Program("jsoup-1_6_1-6", "org.jsoup.parser.CharacterReader", 99));
//		programs.add(new Program("jsoup-1_6_3-1", "org.jsoup.safety.Whitelist", 334));
//		programs.add(new Program("jsoup-1_6_3-2", "org.jsoup.parser.TokeniserState$27", 558));
//		programs.add(new Program("jsoup-1_6_3-3", "org.jsoup.parser.Tokeniser", 135));
//		programs.add(new Program("jsoup-1_6_4-1", "org.jsoup.helper.StringUtil", 113));
//		programs.add(new Program("jsoup-1_6_4-2", "org.jsoup.helper.DataUtil", 131));
//		programs.add(new Program("jsoup-1_7_2-1", "org.jsoup.parser.HtmlTreeBuilder", 148));
//		programs.add(new Program("jsoup-1_7_2-2", "org.jsoup.nodes.Element", 1138));
//		programs.add(new Program("jsoup-1_7_3-1", "org.jsoup.parser.HtmlTreeBuilder", 163));
//		programs.add(new Program("jsoup-1_7_3-2", "org.jsoup.parser.HtmlTreeBuilderState$7", 725));
//		programs.add(new Program("jsoup-1_7_3-3", "org.jsoup.parser.CharacterReader", 89));
//		programs.add(new Program("jsoup-1_7_4-1", "org.jsoup.safety.Cleaner$CleaningVisitor", 105));
//		programs.add(new Program("jsoup-1_7_4-2", "org.jsoup.helper.DataUtil", 121));
//		programs.add(new Program("jsoup-1_7_4-3", "org.jsoup.parser.HtmlTreeBuilderState$7", 456));
//		programs.add(new Program("jsoup-1_7_4-4", "org.jsoup.nodes.Element", 1101));
//		programs.add(new Program("jsoup-1_8_2-1", "org.jsoup.nodes.FormElement", 99));
//		programs.add(new Program("jsoup-1_8_2-2", "org.jsoup.nodes.Element", 574));
//		programs.add(new Program("jsoup-1_8_3-1", "org.jsoup.nodes.Entities", 118));
//		programs.add(new Program("jsoup-1_8_3-2", "org.jsoup.nodes.Entities", 122));
//		programs.add(new Program("jsoup-1_8_3-3", "org.jsoup.parser.HtmlTreeBuilder", 394));
//		programs.add(new Program("jsoup-1_8_4-1", "org.jsoup.select.StructuralEvaluator$Parent", 61));
//		programs.add(new Program("jsoup-1_8_4-2", "org.jsoup.select.Selector", 132));
//		programs.add(new Program("jsoup-1_8_4-3", "org.jsoup.helper.HttpConnection$Response", 773));
		
		for (Program program : programs) {
			String programFolder = program.getName();
			String faultyClass = program.getFaultyClass();
			Integer faultyLine = program.getFaultyLine();
			new Report().createReport(rootFolder, programFolder, faultyClass, faultyLine);			
		}
		
	}
	
}
