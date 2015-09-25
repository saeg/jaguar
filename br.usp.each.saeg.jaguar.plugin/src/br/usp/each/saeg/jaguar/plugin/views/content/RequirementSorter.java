package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.plugin.data.DuaRequirementData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

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
		if(element1 instanceof RequirementData && element2 instanceof RequirementData){
			RequirementData requirement1 = (RequirementData) element1;
			RequirementData requirement2 = (RequirementData) element2;
			
			if(requirement1.getType() == Type.LINE){
				switch(column){
				case 0: 
					rc = Double.compare(requirement1.getScore(), requirement2.getScore());
					break;
				case 1: 
					rc = requirement1.getValue().trim().compareToIgnoreCase(requirement2.getValue().trim());
				}
			}else{
				switch(column){
				case 0: 
					rc = Double.compare(requirement1.getScore(), requirement2.getScore());
					break;
				case 1: 
					rc = ((DuaRequirementData)requirement1).getVar().compareToIgnoreCase(((DuaRequirementData)requirement2).getVar());
					break;
				case 2: 
					rc = Integer.compare(((DuaRequirementData)requirement1).getDef(),((DuaRequirementData)requirement2).getDef());
					break;
				case 3: 
					rc = Integer.compare(((DuaRequirementData)requirement1).getUse(),((DuaRequirementData)requirement2).getUse());
					break;
				}
			}
			if(direction == ASCENDING)
				rc = -rc;
			return rc;
		}
		return 0;
	}
}
