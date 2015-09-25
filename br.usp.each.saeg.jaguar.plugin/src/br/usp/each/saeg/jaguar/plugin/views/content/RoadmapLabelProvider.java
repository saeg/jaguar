package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import br.usp.each.saeg.jaguar.plugin.data.MethodData;

public class RoadmapLabelProvider extends LabelProvider implements
		ITableColorProvider, ITableLabelProvider {

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String displayText = "";
		MethodData methodData = (MethodData)element;
		switch(columnIndex){
		case 0:
			displayText = methodData.getName();
			break;
		case 1:
			displayText = String.valueOf(methodData.getScore());
		}
		return displayText;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		MethodData methodData = (MethodData)element;
		if(methodData.getScore() < 0.25)
			return new Color(Display.getCurrent(),192, 255, 192);
		else if(methodData.getScore() < 0.5)
			return new Color(Display.getCurrent(),255,255,128);
		else if(methodData.getScore() < 0.75)
			return new Color(Display.getCurrent(),255, 204, 153);
		else
			return new Color(Display.getCurrent(),255,160,160);
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

}
