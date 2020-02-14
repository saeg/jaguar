package br.usp.each.saeg.jaguar.core.analysis;

import br.usp.each.saeg.badua.core.analysis.ClassCoverage;
import br.usp.each.saeg.badua.core.analysis.ICoverageVisitor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DuaCoverageBuilder implements ICoverageVisitor {

    private final Map<String, ClassCoverage> classes = new HashMap<>();

    public Collection<ClassCoverage> getClasses() {
        return Collections.unmodifiableCollection(classes.values());
    }

    @Override
    public void visitCoverage(ClassCoverage classCoverage) {
        if (!classCoverage.getMethods().isEmpty()) {
            final String name = classCoverage.getName();
            final ClassCoverage dup = classes.put(name, classCoverage);

            if (dup != null && dup.hashCode() != classCoverage.hashCode()) {
                throw new IllegalStateException("Cannot add different class with same name: " + name);
            }
        }
    }
}
