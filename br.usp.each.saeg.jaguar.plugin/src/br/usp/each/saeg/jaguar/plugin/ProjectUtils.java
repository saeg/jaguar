package br.usp.each.saeg.jaguar.plugin;


import java.io.File;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

public class ProjectUtils {
	
	public static String getLibJarLocation(String jarName){
		Bundle plugin = Platform.getBundle(JaguarPlugin.PLUGIN_ID);
		URL url = plugin.getEntry ("/lib/" + jarName);
		File file = null;
		try {
			URL resolvedURL = FileLocator.resolve(url);
			file = new File (resolvedURL.getFile ());
		} catch (Exception e) {
			System.out.println(e);
		}
		return file.toString();
	}

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