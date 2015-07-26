package br.usp.each.saeg.jaguar.heuristic;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.HeuristicCalculator;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.LineTestRequirement;

public class SuspiciousNormalizerTest {

	private static HeuristicCalculator heuristicCalculator;
	
	@BeforeClass
	public static void beforeClass(){
		int nTestsPassed = 3;
		int nTestsFailed = 2;
		Heuristic heuristic = new TarantulaHeuristic();
		HashSet<AbstractTestRequirement> requirements = new HashSet<AbstractTestRequirement>();
		
		heuristicCalculator = new HeuristicCalculator(heuristic, requirements, nTestsPassed, nTestsFailed);
	}
	
	@Test
	public void normalize_only_positives(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(10);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(3);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(0);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(0.3, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(0, rankList.get(2).getSuspiciousness(), 0.01);
	}
	
	@Test
	public void normalize_only_negatives(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(-1);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(-8);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(-11);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(0.3, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(0, rankList.get(2).getSuspiciousness(), 0.01);
	}
	
	@Test
	public void normalize_postives_and_negatives(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(10);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(0);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(-10);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(0.5, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(0, rankList.get(2).getSuspiciousness(), 0.01);
	}
	
	@Test
	public void normalize_postives_and_negatives2(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(50);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(25);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(0);
		
		LineTestRequirement req4 = new LineTestRequirement("class", 1);
		req4.setSuspiciousness(-100);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		rankList.add(req4);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(0.83, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(0.66, rankList.get(2).getSuspiciousness(), 0.01);
		Assert.assertEquals(0, rankList.get(3).getSuspiciousness(), 0.01);
	}
	
	@Test
	public void normalize_all_equal_positive(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(22);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(22);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(22);
		
		LineTestRequirement req4 = new LineTestRequirement("class", 1);
		req4.setSuspiciousness(22);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		rankList.add(req4);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(2).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(3).getSuspiciousness(), 0.01);
	}
	
	@Test
	public void normalize_all_equal_negative(){
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		
		LineTestRequirement req1 = new LineTestRequirement("class", 1);
		req1.setSuspiciousness(-9);
		
		LineTestRequirement req2 = new LineTestRequirement("class", 1);
		req2.setSuspiciousness(-9);
		
		LineTestRequirement req3 = new LineTestRequirement("class", 1);
		req3.setSuspiciousness(-9);
		
		LineTestRequirement req4 = new LineTestRequirement("class", 1);
		req4.setSuspiciousness(-9);
		
		rankList.add(req1);
		rankList.add(req2);
		rankList.add(req3);
		rankList.add(req4);
		
		heuristicCalculator.normalize(rankList);
		
		Assert.assertEquals(1, rankList.get(0).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(1).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(2).getSuspiciousness(), 0.01);
		Assert.assertEquals(1, rankList.get(3).getSuspiciousness(), 0.01);
	}
}