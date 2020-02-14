package br.usp.each.saeg.jaguar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.dua.Dua;
import org.jacoco.core.analysis.dua.IDua;
import org.jacoco.core.analysis.dua.IDuaClassCoverage;
import org.jacoco.core.analysis.dua.IDuaMethodCoverage;
import org.junit.Assert;
import org.junit.Test;

import br.usp.each.saeg.jaguar.core.JaguarSFL;
import br.usp.each.saeg.jaguar.core.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.core.model.core.requirement.DuaTestRequirement;

public class JaguarSFLTest {

	private static final String CLASS_NAME1 = "org/apache/commons/math3/optimization/linear/SimplexTableau";

	private static final int INDEX = 111;
	private static final int INDEX2 = 112;

	private static final int DEF = 206;
	private static final int DEF2 = 206;

	private static final int USE = 206;
	private static final int USE2 = 206;

	private static final int TARGET1 = 238;
	private static final int TARGET2 = 207;

	private static final String VAR = "i";

	@Test
	public void sameHashCodeButNotEqual() {
		JaguarSFL sfl = new JaguarSFL();

		IDuaClassCoverage duaClassCoverage = mock(IDuaClassCoverage.class);
		when(duaClassCoverage.getName()).thenReturn(CLASS_NAME1);

		IDuaMethodCoverage duaMethodCoverage = mock(IDuaMethodCoverage.class);
		when(duaMethodCoverage.getName()).thenReturn("setRowVector");
		when(duaMethodCoverage.getDesc()).thenReturn("(ILorg/apache/commons/math3/linear/RealVector;)V");
		when(duaMethodCoverage.getId()).thenReturn(1);

		IDua dua1 = new Dua(INDEX, DEF, USE, TARGET1, VAR, ICounter.FULLY_COVERED);
		IDua dua2 = new Dua(INDEX2, DEF2, USE2, TARGET2, VAR, ICounter.FULLY_COVERED);

		sfl.updateRequirement(duaClassCoverage, duaMethodCoverage, dua1, false);
		sfl.updateRequirement(duaClassCoverage, duaMethodCoverage, dua2, false);
		
		Assert.assertEquals(2, sfl.getTestRequirements().size());

		Iterator<AbstractTestRequirement> iterator = sfl.getTestRequirements().values().iterator();
		
		DuaTestRequirement duaRequirement1 = (DuaTestRequirement) iterator.next();
		DuaTestRequirement duaRequirement2 = (DuaTestRequirement) iterator.next();
		
		Assert.assertEquals(duaRequirement1.hashCode(), duaRequirement2.hashCode());
	}

}
