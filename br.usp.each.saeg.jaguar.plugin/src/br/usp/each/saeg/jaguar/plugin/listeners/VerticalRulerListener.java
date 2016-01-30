package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerInfoExtension;
import org.eclipse.jface.text.source.IVerticalRulerListener;
import org.eclipse.jface.text.source.VerticalRulerEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class VerticalRulerListener implements IVerticalRulerListener {
	
	public void register() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(window != null){
			IWorkbenchPage page = window.getActivePage();
			if(page != null){
				IEditorPart editor = page.getActiveEditor();
				if(editor instanceof ITextEditor){
					Object adapter = editor.getAdapter(IVerticalRuler.class);
					if(adapter instanceof IVerticalRulerInfoExtension){
						((IVerticalRulerInfoExtension)adapter).addVerticalRulerListener(this);
					}
				}
			}
		}
		
	}

	@Override
	public void annotationContextMenuAboutToShow(VerticalRulerEvent arg0,
			Menu arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void annotationDefaultSelected(VerticalRulerEvent rulerEvent) {
		try {
            Annotation annotation = rulerEvent.getSelectedAnnotation();
           if(annotation.getType().equals("org.eclipse.core.resources.markers")){
        	   if(annotation instanceof MarkerAnnotation){
        		   MarkerAnnotation markerAnnotation = (MarkerAnnotation)annotation;
        		   IMarker marker = markerAnnotation.getMarker();
        		   JaguarPlugin.ui(marker.getResource().getProject(), this, "" + marker.getAttribute("org.eclipse.core.resources.textmarker") + ":" + marker.getAttribute("lineNumber"));
        	   }
           }
            
        } catch (Exception e) {
        	JaguarPlugin.errorStatus("error inspecting vertical ruler", e);
        }

	}

	@Override
	public void annotationSelected(VerticalRulerEvent rulerEvent) {
		try {
            Annotation annotation = rulerEvent.getSelectedAnnotation();
           if(annotation.getType().equals("org.eclipse.core.resources.markers")){
        	   if(annotation instanceof MarkerAnnotation){
        		   MarkerAnnotation markerAnnotation = (MarkerAnnotation)annotation;
        		   IMarker marker = markerAnnotation.getMarker();
        		   JaguarPlugin.ui(marker.getResource().getProject(), this, "" + marker.getAttribute("org.eclipse.core.resources.textmarker") + ":" + marker.getAttribute("lineNumber"));
        	   }
           }
            
        } catch (Exception e) {
        	JaguarPlugin.errorStatus("error inspecting vertical ruler", e);
        }
		
	}

}
