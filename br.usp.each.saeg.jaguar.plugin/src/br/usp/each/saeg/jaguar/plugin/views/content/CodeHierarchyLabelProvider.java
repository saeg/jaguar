package br.usp.each.saeg.jaguar.plugin.views.content;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;

public class CodeHierarchyLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof Package)
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
		if(element instanceof Package){
			Package packageObj = (Package)element;
			switch(columnIndex){
			case 0:
				displayText = packageObj.getName();
				break;
			case 1:
				displayText = String.valueOf(packageObj.getSuspiciousValue());
			}
		}
		else if(element instanceof Class){
			Class classObj = (Class)element;
			switch(columnIndex){
			case 0:
				displayText = classObj.getSingleName();
				break;
			case 1:
				displayText = String.valueOf(classObj.getSuspiciousValue());
			}
		}
		else if(element instanceof Method){
			Method methodObj = (Method)element;
			switch(columnIndex){
			case 0:
				displayText = methodObj.getName();
				break;
			case 1:
				displayText = String.valueOf(methodObj.getSuspiciousValue());
			}
		}
		else if(element instanceof Requirement){
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