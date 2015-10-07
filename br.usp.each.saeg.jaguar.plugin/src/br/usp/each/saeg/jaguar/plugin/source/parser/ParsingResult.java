package br.usp.each.saeg.jaguar.plugin.source.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeUtils;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Package;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class ParsingResult {
	private final IResource file;
    private Package pack;
    private boolean declaredPackage = false;
    private Map<String, Class> nameClass = new HashMap<String, Class>();

    public ParsingResult(IResource file) {
        this.file = file;
    }

    public IResource getFile() {
        return file;
    }

    public Package getPackage() {
        return pack;
    }
    public void setPackage(Package pack) {
        if (isPackageNull()) {
            this.pack = pack;
        }
    }
    public boolean isPackageNull() {
        return pack == null;
    }

    public void addClass(String currentClass, int start, int end, int close, Class clazz) {
        Class foundClass = new Class();
        foundClass.setStart(start);
        foundClass.setEnd(end);
        foundClass.setClose(close);
        foundClass.setName(currentClass);
        foundClass.setNumber(clazz.getNumber());
        foundClass.setSuspiciousValue(clazz.getSuspiciousValue());
        foundClass.setContent(clazz.getContent());
        foundClass.setJavaInterface(clazz.isJavaInterface());
        nameClass.put(currentClass, foundClass);
    }

    public Class getClass(String name) {
        return nameClass.get(name);
    }

    public Collection<Class> getClasses() {
        return nameClass.values();
    }

    public boolean isDeclaredPackage() {
        return declaredPackage;
    }
    public void packageDeclared() {
        this.declaredPackage = true;
    }

    public String getURI() {
        return SourceCodeUtils.asString(file);
    }
}
