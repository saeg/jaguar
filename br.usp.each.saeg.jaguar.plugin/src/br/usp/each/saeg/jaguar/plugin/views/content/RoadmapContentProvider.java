package br.usp.each.saeg.jaguar.plugin.views.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

public class RoadmapContentProvider implements IStructuredContentProvider {
	
	private final Object[] EMPTY_LIST = new Object[0];
	private ProjectState state;
	
	public RoadmapContentProvider(ProjectState state){
		this.state = state;
		disableNonExecutedElements();
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object arg0) {
		return createStructure();
	}
	
	private Object[] createStructure(){
		return getMethodList().toArray();
	}
	
	private List<MethodData> getMethodList(){
		List<MethodData> methodList = new ArrayList<MethodData> ();
		List<PackageData> listPackageData = state.getPackageDataResult();
		for(PackageData pack : listPackageData){
			for(ClassData clazz : pack.getClassData()){
				for(MethodData method : clazz.getMethodData()){
					if(method.getScore() > 0){
						methodList.add(method);
					}
				}
			}
		}
		Collections.sort(methodList);
		return methodList;
	}
	
	public void disableNonExecutedElements(){
		List<PackageData> listPackageData = state.getPackageDataResult();
		for(PackageData packData : listPackageData){
			for(ClassData classData : packData.getClassData()){
				for(MethodData methodData : classData.getMethodData()){
					for(RequirementData reqData : methodData.getRequirementData()){
						if(reqData.getScore() < 0)
							reqData.disable();
					}
					if(methodData.getScore() < 0)
						methodData.disable();
				}
				if(classData.getScore() < 0)
					classData.disable();
			}
			if(packData.getScore() < 0)
				packData.disable();
		}
	}
	
}
