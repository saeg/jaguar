package br.usp.each.saeg.jaguar.core.results.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationEntry;
import br.usp.each.saeg.jaguar.core.results.model.FaultLocalizationReport;

/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	public static void writeCsvFile(String programFolder, FaultLocalizationReport faultLocalizationReport, File reportFile) {
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
				
		try {
			
			//initialize FileWriter object
			fileWriter = new FileWriter(reportFile);
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			
			//Write a new student object list to the CSV file
			for (FaultLocalizationEntry entry : faultLocalizationReport.getEntries()) {
				List<String> entryDataRecord = new ArrayList<String>();
	            entryDataRecord.add(entry.getCoverageType());
	            entryDataRecord.add(String.valueOf(entry.getFaultSuspiciousValue()));
	            entryDataRecord.add(programFolder);
	            entryDataRecord.add(entry.getHeuristic());
	            entryDataRecord.add(String.valueOf(entry.getMaxCost()));
	            entryDataRecord.add(String.valueOf(entry.getMinCost()));
	            entryDataRecord.add(String.valueOf(entry.getTotalTimeInMs()));
	            entryDataRecord.add(entry.getReadableTime());
	            csvFilePrinter.printRecord(entryDataRecord);
			}

			System.out.println("CSV file was created successfully at " + reportFile);
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
			}
		}
	}
}