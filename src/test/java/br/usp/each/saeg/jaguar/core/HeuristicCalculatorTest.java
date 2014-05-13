package br.usp.each.saeg.jaguar.core;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.HeuristicCalculator;
import br.usp.each.saeg.jaguar.heuristic.impl.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.model.core.TestRequirement;

public class HeuristicCalculatorTest {

	private static HeuristicCalculator heuristicCalculator;
	
	@BeforeClass
	public static void beforeClass(){
		int nTestsPassed = 3;
		int nTestsFailed = 2;
		Heuristic heuristic = new TarantulaHeuristic();
		HashSet<TestRequirement> requirements = new HashSet<TestRequirement>();
		
		TestRequirement requirement1 = new TestRequirement("br.usp.each.saeg.jaguar.TarantulaTest", 1);
		requirement1.increasePassed();
		requirement1.increasePassed();
		requirement1.increasePassed();
		requirement1.increaseFailed();
		requirement1.increaseFailed();
		requirements.add(requirement1);
		
		TestRequirement requirement2 = new TestRequirement("br.usp.each.saeg.jaguar.TarantulaTest", 2);
		requirement2.increasePassed();
		requirement2.increasePassed();
		requirement2.increasePassed();
		requirement2.increaseFailed();
		requirement2.increaseFailed();
		requirements.add(requirement2);
		
		TestRequirement requirement3 = new TestRequirement("br.usp.each.saeg.jaguar.TarantulaTest", 3);
		requirement3.increasePassed();
		requirement3.increasePassed();
		requirement3.increasePassed();
		requirement3.increaseFailed();
		requirements.add(requirement3);
		
		TestRequirement requirement4 = new TestRequirement("br.usp.each.saeg.jaguar.TarantulaTest", 4);
		requirement4.increasePassed();
		requirement4.increasePassed();
		requirement4.increasePassed();
		requirement4.increaseFailed();
		requirements.add(requirement4);

		TestRequirement requirement5 = new TestRequirement("br.usp.each.saeg.jaguar.TarantulaTest", 5);
		requirement5.increasePassed();
		requirement5.increasePassed();
		requirement5.increasePassed();
		requirement5.increaseFailed();
		requirements.add(requirement5);
		
		heuristicCalculator = new HeuristicCalculator(heuristic, requirements, nTestsPassed, nTestsFailed);
	}
	
	@Test
	public void tarantulaRank(){
		ArrayList<TestRequirement> rank = heuristicCalculator.calculateRank();
		Assert.assertEquals(0.5, rank.get(0).getSuspiciousness(), 0.001);
		Assert.assertEquals(0.5, rank.get(1).getSuspiciousness(), 0.001);
		Assert.assertEquals(0.333, rank.get(2).getSuspiciousness(), 0.001);
		Assert.assertEquals(0.333, rank.get(3).getSuspiciousness(), 0.001);
		Assert.assertEquals(0.333, rank.get(4).getSuspiciousness(), 0.001);
	}
	
}