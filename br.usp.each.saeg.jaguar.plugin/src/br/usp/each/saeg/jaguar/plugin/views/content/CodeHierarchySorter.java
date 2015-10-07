package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

public class CodeHierarchySorter extends ViewerSorter {

	private final int ASCENDING = 0;
	private final int DESCENDING = 1;
	private int column, direction;
	
	public void doSort(int column){
		if(column == this.column){
			direction = 1 - direction;
		} else {
			this.column = column;
			direction = ASCENDING;
		}
	}
	
	public int compare(Viewer viewer, Object element1, Object element2){
		int rc = 0;
		if(element1 instanceof PackageData && element2 instanceof PackageData){
			PackageData package1 = (PackageData) element1;
			PackageData package2 = (PackageData) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(package1.getScore(), package2.getScore());
				break;
			case 1: 
				rc = package1.getName().compareToIgnoreCase(package2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		}else if(element1 instanceof ClassData && element2 instanceof ClassData){
			ClassData class1 = (ClassData) element1;
			ClassData class2 = (ClassData) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(class1.getScore(), class2.getScore());
				break;
			case 1: 
				//rc = collator.compare(class1.getName(), class2.getName());
				rc = class1.getName().compareToIgnoreCase(class2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		} else if(element1 instanceof Method && element2 instanceof Method){
			MethodData method1 = (MethodData) element1;
			MethodData method2 = (MethodData) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(method1.getScore(), method2.getScore());
				break;
			case 1: 
				rc = method1.getName().compareToIgnoreCase(method2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		} else if(element1 instanceof RequirementData && element2 instanceof RequirementData){//TODO remove?
			RequirementData requirement1 = (RequirementData) element1;
			RequirementData requirement2 = (RequirementData) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(requirement1.getScore(), requirement2.getScore());
				break;
			case 1: 
				rc = requirement1.getName().compareToIgnoreCase(requirement2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		}
		return 0;
	}
	
}