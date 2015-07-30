package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;

public class RequirementLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String displayText = "";
		
		if(element instanceof Requirement){
			Requirement requirementObj = (Requirement)element;
			switch(columnIndex){
			case 0:
				displayText = requirementObj.getName();
				break;
			case 1:
				displayText = String.valueOf(requirementObj.getSuspiciousValue());
			}
		}
		return displayText;
	}

	
	
}
