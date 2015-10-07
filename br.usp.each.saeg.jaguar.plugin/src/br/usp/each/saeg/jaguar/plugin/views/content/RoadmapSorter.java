package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;

public class RoadmapSorter extends ViewerSorter {
	
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
		if(element1 instanceof Method && element2 instanceof Method){
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
		}
		return rc;
	}
	
}
