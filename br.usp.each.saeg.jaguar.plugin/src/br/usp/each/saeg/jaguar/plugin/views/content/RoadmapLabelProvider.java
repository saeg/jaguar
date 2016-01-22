package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import br.usp.each.saeg.jaguar.plugin.data.MethodData;

public class RoadmapLabelProvider extends StyledCellLabelProvider  implements
ITableColorProvider, ITableLabelProvider {
	
	String column = "";
	
	public RoadmapLabelProvider(String column){
		this.column = column;
	}
	
	@Override
	public String getToolTipText(Object element){
		if(column.equals("method")){
			MethodData methodData = (MethodData) element;
			return methodData.getParent().getName();
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
			cell.setText(String.valueOf(methodData.getRoundedScore(3)));
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
		MethodData methodData = (MethodData)element;
		if(methodData.getScore() <= 0.25)
			return new Color(Display.getCurrent(),192, 255, 192);
		else if(methodData.getScore() <= 0.5)
			return new Color(Display.getCurrent(),255,255,128);
		else if(methodData.getScore() <= 0.75)
			return new Color(Display.getCurrent(),255, 204, 153);
		else
			return new Color(Display.getCurrent(),255,160,160);
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

}
