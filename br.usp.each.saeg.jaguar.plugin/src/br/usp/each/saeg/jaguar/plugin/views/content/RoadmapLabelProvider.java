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
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

public class RoadmapLabelProvider extends StyledCellLabelProvider  implements
ITableColorProvider, ITableLabelProvider {
	
	String column = "";
	IProject project;
	
	public RoadmapLabelProvider(String column){
		this.column = column;
		this.project = ProjectUtils.getCurrentSelectedProject();
	}
	
	@Override
	public String getToolTipText(Object element){
		if(column.equals("method")){
			MethodData methodData = (MethodData) element;
			System.out.println("[Mouse hover] @ "+methodData);
			JaguarPlugin.ui(project, this, "[Mouse hover] @ "+ methodData);
			return methodData.getParent().getName();
		}
		if(column.equals("score")){
			MethodData methodData = (MethodData) element;
			return String.valueOf(methodData.getScore());
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
		MethodData methodData = (MethodData)cell.getElement();
		if(column.equals("method")){
			cell.setText(methodData.getParent().getSingleName()+"."+methodData.getName());
		}else if(column.equals("score")){
			cell.setText(String.format("%.2f",methodData.getRoundedScore(2)));
		}
		cell.setBackground(getBackground(methodData,0));
		cell.setForeground(getForeground(methodData,0));
	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}

	@Override
	public String getColumnText(Object arg0, int arg1) {
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		MethodData data = (MethodData) element;
		return data.getBackgroundColor();
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

}
