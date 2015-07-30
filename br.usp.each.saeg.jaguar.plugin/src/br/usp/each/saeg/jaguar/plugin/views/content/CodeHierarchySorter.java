package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;

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
		if(element1 instanceof Package && element2 instanceof Package){
			Package package1 = (Package) element1;
			Package package2 = (Package) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(package1.getSuspiciousValue(), package2.getSuspiciousValue());
				break;
			case 1: 
				rc = package1.getName().compareToIgnoreCase(package2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		}else if(element1 instanceof Class && element2 instanceof Class){
			Class class1 = (Class) element1;
			Class class2 = (Class) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(class1.getSuspiciousValue(), class2.getSuspiciousValue());
				break;
			case 1: 
				//rc = collator.compare(class1.getName(), class2.getName());
				rc = class1.getName().compareToIgnoreCase(class2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		} else if(element1 instanceof Method && element2 instanceof Method){
			Method method1 = (Method) element1;
			Method method2 = (Method) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(method1.getSuspiciousValue(), method2.getSuspiciousValue());
				break;
			case 1: 
				rc = method1.getName().compareToIgnoreCase(method2.getName());
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		} else if(element1 instanceof Requirement && element2 instanceof Requirement){
			Requirement requirement1 = (Requirement) element1;
			Requirement requirement2 = (Requirement) element2;
			
			switch(column){
			case 0: 
				rc = Double.compare(requirement1.getSuspiciousValue(), requirement2.getSuspiciousValue());
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