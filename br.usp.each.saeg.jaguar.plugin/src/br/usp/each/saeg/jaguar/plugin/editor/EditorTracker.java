package br.usp.each.saeg.jaguar.plugin.editor;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeUtils;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.listeners.LogListener;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.project.ProjectToolkit;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com
 */
public class EditorTracker {
	
	private final static Logger logger = LoggerFactory.getLogger(LogListener.class.getName());
    private final IWorkbench workbench;
    private IProject project;

    private IWindowListener windowListener = new IWindowListener() {
        public void windowOpened(IWorkbenchWindow window) {
            window.getPartService().addPartListener(partListener);
        }

        public void windowClosed(IWorkbenchWindow window) {
            window.getPartService().removePartListener(partListener);
        }

        public void windowActivated(IWorkbenchWindow window) {
        }

        public void windowDeactivated(IWorkbenchWindow window) {
        }
    };

    private IPartListener2 partListener = new IPartListener2() {
        public void partOpened(IWorkbenchPartReference partref) {
            annotateEditor(partref);
            if(partref instanceof IEditorReference){
            	AbstractTextEditor editor = (AbstractTextEditor)((IEditorReference)partref).getEditor(false);
            	((StyledText)editor.getAdapter(Control.class)).addCaretListener(caretListener);
            	//get project - refactor
            	ProjectToolkit toolkit = new ProjectToolkit(partref);
                if (toolkit.isValid()) {
                	project = toolkit.getProject();
                	if (project != null) {
                		JaguarPlugin.ui(project, EditorTracker.this, "caret listener added on "+editor.getTitle());
                	}
                	System.out.println("adding CaretListener here");
                }
            }
        }

        public void partActivated(IWorkbenchPartReference partref) {
        }

        public void partBroughtToTop(IWorkbenchPartReference partref) {
        }

        public void partVisible(IWorkbenchPartReference partref) {
            annotateEditor(partref);
        }

        public void partInputChanged(IWorkbenchPartReference partref) {
        }

        public void partClosed(IWorkbenchPartReference partref) {
            if(partref instanceof IEditorReference){
            	AbstractTextEditor editor = (AbstractTextEditor)((IEditorReference)partref).getEditor(false);
            	((StyledText)editor.getAdapter(Control.class)).removeCaretListener(caretListener);
            	IProject project = ProjectUtils.getCurrentSelectedProject();
            	if (project != null) {
            		JaguarPlugin.ui(project, EditorTracker.this, "caret listener removed on "+editor.getTitle());
            	}
            	System.out.println("removing CaretListener here");
            }
        }

        public void partDeactivated(IWorkbenchPartReference partref) {
        }

        public void partHidden(IWorkbenchPartReference partref) {
        }
    };

    public EditorTracker(IWorkbench workbench) {
        this.workbench = workbench;
        for (final IWorkbenchWindow w : workbench.getWorkbenchWindows()) {
            w.getPartService().addPartListener(partListener);
        }
        workbench.addWindowListener(windowListener);
        annotateAllEditors();
    }

    public synchronized void dispose() {
        workbench.removeWindowListener(windowListener);
        for (final IWorkbenchWindow w : workbench.getWorkbenchWindows()) {
            w.getPartService().removePartListener(partListener);
        }
    }

    private void annotateAllEditors() {
        for (final IWorkbenchWindow w : workbench.getWorkbenchWindows()) {
            for (final IWorkbenchPage p : w.getPages()) {
                for (final IEditorReference e : p.getEditorReferences()) {
                    annotateEditor(e);
                }
            }
        }
    }
	
	 /**
     * Arrumar o TreeDataBuilder para fazer a inclusao em batch
     * Rodar o addAnnotations em batch
     *
     */
    public synchronized void annotateEditor(IWorkbenchPartReference partref) {
        ProjectToolkit toolkit = new ProjectToolkit(partref);
        if (!toolkit.isValid()) {
            return;
        }

        ProjectState state = ProjectPersistence.getStateOf(toolkit.getProject());
        if (state == null) {
            return;
        }
        String fileName = SourceCodeUtils.asString(toolkit.getFile());
        if (state.getMarked().contains(fileName)) {
            return;
        }

        Collection<ClassData> analysis = state.getAnalysisResult().get(fileName);
        if (analysis == null) {
            logger.info("[" + SourceCodeUtils.asString(toolkit.getFile()) + "] no data found jaguar...");
            return;
        }
        state.getMarked().add(fileName);
    }
	
    //editor listener    
    private CaretListener caretListener = new CaretListener(){
		@Override
		public void caretMoved(CaretEvent caretEvent) {
			String line = "line: " + ((StyledText)caretEvent.getSource()).getLineAtOffset(caretEvent.caretOffset);
			String code = ", code: " + ((StyledText)caretEvent.getSource()).getLine(((StyledText)caretEvent.getSource()).getLineAtOffset(caretEvent.caretOffset)).trim();
			if (project != null) {
        		JaguarPlugin.ui(project, EditorTracker.this, "click on "+line+code);
        	}
			//System.out.println("offset:"+((StyledText)caretEvent.getSource()).getSelectionText());//get text selected by the caret
			System.out.println(line + code);
			
		}
	};
    
}
