package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.data.DuaRequirementData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

public class RequirementLabelProvider extends StyledCellLabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	String column = "";
	IProject project;
	
	public RequirementLabelProvider(String column){
		this.column = column;
		this.project = ProjectUtils.getCurrentSelectedProject();
	}
	
	@Override
	public String getToolTipText(Object element){
		if(column.equals("line") || column.equals("var") || column.equals("def")){
			RequirementData requirementData = (RequirementData) element;
			System.out.println("[Mouse hover] @ "+requirementData);
			JaguarPlugin.ui(project, this, "[Mouse hover] @ "+ requirementData);
			return "line: "+String.valueOf(requirementData.getLine());
		}
		if(column.equals("score")){
			RequirementData requirementData = (RequirementData) element;
			return String.valueOf(requirementData.getScore());
		}
		return null;
	}
	
	@Override
	public Point getToolTipShift(Object element){
		return new Point(5,5);
	}
	
	@Override
	public int getToolTipDisplayDelayTime(Object element){
		return 100;
	}
	
	@Override
	public int getToolTipTimeDisplayed(Object element){
		return 5000;
	}
	
	@Override
	public void update(ViewerCell cell) {
		RequirementData requirementData = (RequirementData)cell.getElement();
		switch (column){
			case "line":
				cell.setText(requirementData.getValue().trim());
				break;
			case "var":
				cell.setText(String.valueOf(((DuaRequirementData)requirementData).getVar()));
				break;
			case "def":
				cell.setText(String.valueOf(((DuaRequirementData)requirementData).getDef()));
				break;
			case "use":
				cell.setText(String.valueOf(((DuaRequirementData)requirementData).getUse()));
				break;
			case "score":
				cell.setText(String.format("%.2f",requirementData.getRoundedScore(2)));
		}
		
		cell.setBackground(getBackground(requirementData,0));
		cell.setForeground(getForeground(requirementData,0));
	}
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex){
		RequirementData reqData = (RequirementData)element;
		if(reqData.getScore() <= 0.25)
			return new Color(Display.getCurrent(),192, 255, 192);
		else if(reqData.getScore() <= 0.5)
			return new Color(Display.getCurrent(),255,255,128);
		else if(reqData.getScore() <= 0.75)
			return new Color(Display.getCurrent(),255, 204, 153);
		else
			return new Color(Display.getCurrent(),255,160,160);
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}
	
}
