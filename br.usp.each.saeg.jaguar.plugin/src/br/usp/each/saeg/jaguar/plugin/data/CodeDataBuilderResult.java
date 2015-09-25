package br.usp.each.saeg.jaguar.plugin.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import br.usp.each.saeg.jaguar.codeforest.model.Package;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class CodeDataBuilderResult {
	private final IResource resource;
    private final List<Map<String, Object>> markerProperties = new ArrayList<Map<String,Object>>();
    private final List<ClassData> classData = new ArrayList<ClassData>();

    public CodeDataBuilderResult(IResource arg) {
        this.resource = arg;
    }

    public IResource getResource() {
        return resource;
    }

    public List<Map<String, Object>> getMarkerProperties() {
        return markerProperties;
    }

    public List<ClassData> getClassData() {
        return classData;
    }
}
