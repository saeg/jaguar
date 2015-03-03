package br.usp.each.saeg.jaguar.core.results.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FaultLocalizationEntry")
public class FaultLocalizationEntry {

	private Long totalTime = 0L;
	private Long maxTotalCost = 0L;
	private Long minTotalCost = 0L;
	private String heuristic;
	private String coverageType;

	/**
	 * The total time spent to calculate the fault localization rank
	 * 
	 * @return time in milliseconds
	 */
	@XmlAttribute
	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * Cost to find the fault. It is the number of lines of code needed to be inspected to find the fault;
	 * Consider that all lines with the same cost need to be inspected.
	 * 
	 * @return total number of lines needed to be inspected to find the fault;
	 */
	@XmlAttribute
	public Long getMaxTotalCost() {
		return maxTotalCost;
	}

	public void setMaxTotalCost(Long maxTotalCost) {
		this.maxTotalCost = maxTotalCost;
	}

	/** 
	 * Cost to find the fault. It is the number of lines of code needed to be inspected to find the fault;
	 * Consider that the fault is in the first line from the elements with the same cost.
	 * 
	 * @return total number of lines needed to be inspected to find the fault;
	 */
	@XmlAttribute
	public Long getMinTotalCost() {
		return minTotalCost;
	}

	public void setMinTotalCost(Long minTotalCost) {
		this.minTotalCost = minTotalCost;
	}

	/**
	 * The heuristic used to calculate the element suspicious value
	 * 
	 * @return the heuristic
	 */
	@XmlAttribute
	public String getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * The type of element coverage.
	 * 
	 * @return Dua or Line
	 */
	@XmlAttribute
	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	/**
	 * Increase by 1 both max and min total cost.
	 */
	public void increaseCost() {
		this.minTotalCost++;
		this.maxTotalCost++;
	}

	/**
	 * Increase by 1 only the maxTotalCost.
	 */
	public void increaseMaxCost() {
		this.maxTotalCost++;
	}

	/**
	 * Increase by 1 only the minTotalCost.
	 */
	public void increseMinCost() {
		this.minTotalCost++;
	}

}