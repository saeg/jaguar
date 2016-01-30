package br.usp.each.saeg.jaguar.plugin.editor;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
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
    private String file = "";

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
            	if(((IEditorReference)partref).getEditor(false) instanceof AbstractTextEditor){
	            	AbstractTextEditor editor = (AbstractTextEditor)((IEditorReference)partref).getEditor(false);
	            	((StyledText)editor.getAdapter(Control.class)).addCaretListener(caretListener);
	            	((StyledText)editor.getAdapter(Control.class)).addMouseTrackListener(mouseListener);
	            	//get project - refactor
	            	ProjectToolkit toolkit = new ProjectToolkit(partref);
	                if (toolkit.isValid()) {
	                	project = toolkit.getProject();
	                	if (project != null) {
	                		JaguarPlugin.ui(project, EditorTracker.this, "caret and mouse listeners added @ "+editor.getTitle());
	                	}
	                	System.out.println("adding CaretListener and MouseListener here");
	                }
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
            	if(((IEditorReference)partref).getEditor(false) instanceof AbstractTextEditor){
	            	AbstractTextEditor editor = (AbstractTextEditor)((IEditorReference)partref).getEditor(false);
	            	((StyledText)editor.getAdapter(Control.class)).removeCaretListener(caretListener);
	            	((StyledText)editor.getAdapter(Control.class)).removeMouseTrackListener(mouseListener);
	            	IProject project = ProjectUtils.getCurrentSelectedProject();
	            	if (project != null) {
	            		JaguarPlugin.ui(project, EditorTracker.this, "caret and mouse listeners removed @ "+editor.getTitle());
	            	}
	            	System.out.println("removing CaretListener and MouseListener here");
	            }
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
        if(fileName != null){
        	file = fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator").toString())+1,fileName.lastIndexOf("."));
        }
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
	
    //editor listener - to get the right line number Window > Preferences > Java > Editor > Folding and unmark Enable Folding
    private CaretListener caretListener = new CaretListener(){
		@Override
		public void caretMoved(CaretEvent caretEvent) {
			StyledText text = (StyledText)caretEvent.getSource();
			String line = "line: " + (text.getLineAtOffset(caretEvent.caretOffset)+1);
			String code = ", code: " + text.getLine(text.getLineAtOffset(caretEvent.caretOffset)).trim();
			if (project != null) {
        		JaguarPlugin.ui(project, EditorTracker.this, "[" + file + "]" + " click @ "+line+code);
        	}
			System.out.println( "[" + file + "]" + " click @ "+ line + code);
		}
	};
	
	private MouseTrackListener mouseListener = new MouseTrackListener(){

		@Override
		public void mouseEnter(MouseEvent mouseEvent) {
		}

		@Override
		public void mouseExit(MouseEvent mouseEvent) {
		}

		@Override
		public void mouseHover(MouseEvent mouseEvent) {
			try{
				Point point = new Point(mouseEvent.x,mouseEvent.y);
				StyledText text = (StyledText)mouseEvent.getSource();
				String line = "line: " + (text.getLineAtOffset(text.getOffsetAtLocation(point))+1);
				String code = ", code: " + text.getLine(text.getLineAtOffset(text.getOffsetAtLocation(point))).trim();
				if (project != null) {
	        		JaguarPlugin.ui(project, EditorTracker.this, "[" + file +"] [mouse over] @ "+line+code);
	        	}
				System.out.println("[" + file +"] [mouse over] @ "+line+code);
			}catch(IllegalArgumentException iaex){
				
			}
		}
		
	};
}
