package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

public class CodeHierarchyLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {
	
	public String getText(Object obj) {
		return obj.toString();
	}
	
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof PackageData)
		   imageKey = ISharedImages.IMG_OBJ_FOLDER;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		String displayText = "";
		if(element instanceof PackageData){
			PackageData packageData = (PackageData)element;
			switch(columnIndex){
			case 0:
				displayText = packageData.getName();
				break;
			case 1:
				displayText = String.format("%.2f",packageData.getRoundedScore(2));
			}
		}
		else if(element instanceof ClassData){
			ClassData classData = (ClassData)element;
			switch(columnIndex){
			case 0:
				displayText = classData.getSingleName();
				break;
			case 1:
				displayText = String.format("%.2f",classData.getRoundedScore(2));
			}
		}
		else if(element instanceof MethodData){
			MethodData methodData = (MethodData)element;
			switch(columnIndex){
			case 0:
				displayText = methodData.getName();
				break;
			case 1:
				displayText = String.format("%.2f",methodData.getRoundedScore(2));
			}
		}
		/*else if(element instanceof RequirementData){
			RequirementData requirementData = (RequirementData)element;
			switch(columnIndex){
			case 0:
				displayText = requirementData.getName();
				break;
			case 1:
				displayText = String.format("%.2f",requirementData.getRoundedScore(2));
			}
		}*/
		return displayText;
	}
	
	@Override
	public Color getBackground(Object element, int columnIndex){
		if(element instanceof PackageData){
			PackageData packageData = (PackageData)element;
			if(packageData.getScore() < 0.25)
				return new Color(Display.getCurrent(),192, 255, 192);
			else if(packageData.getScore() < 0.5)
				return new Color(Display.getCurrent(),255,255,128);
			else if(packageData.getScore() < 0.75)
				return new Color(Display.getCurrent(),255, 204, 153);
			else
				return new Color(Display.getCurrent(),255,160,160);
		}
		if(element instanceof ClassData){
			ClassData classData = (ClassData)element;
			if(classData.getScore() < 0.25)
				return new Color(Display.getCurrent(),192, 255, 192);
			else if(classData.getScore() < 0.5)
				return new Color(Display.getCurrent(),255,255,128);
			else if(classData.getScore() < 0.75)
				return new Color(Display.getCurrent(),255, 204, 153);
			else
				return new Color(Display.getCurrent(),255,160,160);
		}
		if(element instanceof MethodData){
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
		return null;
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}
	
}