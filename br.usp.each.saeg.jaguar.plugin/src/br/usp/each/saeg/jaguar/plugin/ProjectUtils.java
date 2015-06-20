package br.usp.each.saeg.jaguar.plugin;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;

public class ProjectUtils {

    public static IProject getCurrentSelectedProject() {
        return getCurrentSelectedProject(JaguarPlugin.getActiveWorkbenchWindow());
    }
    
    public static IJavaProject getCurrentSelectedJavaProject() {
    	return getCurrentSelectedJavaProject(JaguarPlugin.getActiveWorkbenchWindow());
    }

    public static IProject getCurrentSelectedProject(IWorkbenchWindow window) {
        IProject project = null;
        ISelectionService selectionService =  window.getSelectionService();

        ISelection selection = selectionService.getSelection();

        if (selection instanceof IStructuredSelection) {
            Object element = ((IStructuredSelection)selection).getFirstElement();

            if (element instanceof IResource) {
                project= ((IResource) element).getProject();
            } else if (element instanceof PackageFragmentRoot) {
                IJavaProject jProject = ((PackageFragmentRoot) element).getJavaProject();
                project = jProject.getProject();
            } else if (element instanceof IJavaElement) {
                IJavaProject jProject = ((IJavaElement) element).getJavaProject();
                project = jProject.getProject();
            }
        }
        return project;
    }
    
    public static IJavaProject getCurrentSelectedJavaProject(IWorkbenchWindow window) {
    	IProject project = getCurrentSelectedProject(window);

		try {
			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			      return JavaCore.create(project);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
        return null;
    }
}