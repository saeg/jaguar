package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.plugin.data.DuaRequirementData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

public class RequirementLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String displayText = "";
		
		if(element instanceof RequirementData){
			RequirementData requirementData = (RequirementData)element;
			if(requirementData.getType() == Type.LINE){
				switch(columnIndex){
				case 0:
					displayText = requirementData.getValue().trim();
					break;
				case 1:
					displayText = String.valueOf(requirementData.getScore());
				}
			}else{
				switch(columnIndex){
				case 0:
					displayText = ((DuaRequirementData)requirementData).getVar();
					break;
				case 1:
					displayText = String.valueOf(((DuaRequirementData)requirementData).getDef());
					break;
				case 2:
					displayText = String.valueOf(((DuaRequirementData)requirementData).getUse());
					break;
				case 3:
					displayText = String.valueOf(requirementData.getScore());
				}
			}
		}
		return displayText;
	}

	@Override
	public Color getBackground(Object element, int columnIndex){
		RequirementData reqData = (RequirementData)element;
		if(reqData.getScore() < 0.25)
			return new Color(Display.getCurrent(),192, 255, 192);
		else if(reqData.getScore() < 0.5)
			return new Color(Display.getCurrent(),255,255,128);
		else if(reqData.getScore() < 0.75)
			return new Color(Display.getCurrent(),255, 204, 153);
		else
			return new Color(Display.getCurrent(),255,160,160);
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}
	
}
