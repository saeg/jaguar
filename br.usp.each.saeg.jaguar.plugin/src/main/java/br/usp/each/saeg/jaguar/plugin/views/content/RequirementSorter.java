package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;

public class RequirementSorter extends ViewerSorter {
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
		if(element1 instanceof Requirement && element2 instanceof Requirement){
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
