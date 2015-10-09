package br.usp.each.saeg.jaguar.plugin;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Henrique Lemos (@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
@SuppressWarnings("restriction")
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
	
	public static void setPropertyOf(IProject project, String name, Object value) {
        try {
            project.setSessionProperty(new QualifiedName("eclipse://br.usp.each.saeg.jaguar.plugin.namespace", name), value);
        } catch (Exception e) {
            JaguarPlugin.log(e);
        }
    }

    public static Object getPropertyOf(IProject project, String name) {
        try {
            return project.getSessionProperty(new QualifiedName("eclipse://br.usp.each.saeg.jaguar.plugin.namespace", name));
        } catch (Exception e) {
            JaguarPlugin.log(e);
            return null;
        }
    }

    public static boolean containsProperty(IProject project, String name) {
        try {
            return project.getSessionProperties().containsKey(new QualifiedName("eclipse://br.usp.each.saeg.jaguar.plugin.namespace", name));
        } catch (Exception e) {
            JaguarPlugin.log(e);
            return false;
        }
    }

	public static Map<String, List<IResource>> javaFilesOf(IProject project) {
		 final Map<String, List<IResource>> result = new HashMap<String, List<IResource>>();
		try {
			result.putAll(visit(project, "java"));
			removeCopiedFiles(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
	
	/*
	 * Remove java files which are out of the right path structure
	 * */
    private static void removeCopiedFiles(Map<String, List<IResource>> result) {
    	Map<String, List<IResource>> resultWithoutCopiedFiles = new HashMap<String, List<IResource>>();
    	Set<String> fileNames = result.keySet();
		for(String name : fileNames){
			for(IResource resourceFile : result.get(name)){
				IFile file = (IFile)resourceFile;
				if(checkPackagePath(file)){
					if(!resultWithoutCopiedFiles.containsKey(name)){
						resultWithoutCopiedFiles.put(name, new ArrayList<IResource>());
					}
					resultWithoutCopiedFiles.get(name).add(resourceFile);
				}
			}
		}
		result.clear();
		result.putAll(resultWithoutCopiedFiles);
	}
    
    /*
     * Compares if the file path matches with package path
     * */
	private static boolean checkPackagePath(IFile file) {
		try {
			String line = "";
			String path = file.getLocation().toString();
			path = path.replace("/", ".");
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents()));
			try {
				while((line = reader.readLine()) != null){
					if(line.contains("package") && line.contains(";")){
						String strLine = line.substring(line.indexOf(" ")+1);
						strLine = strLine.substring(0,strLine.indexOf(";"));
						if(path.contains(strLine)){
							return true;
						}
					}
				}
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
			return false;
		}
		return false;
	}

	public static Map<String, List<IResource>> xmlFilesOf(IProject project) {
        try {
            return visit(project, "xml");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
	
	private static Map<String, List<IResource>> visit(IProject project, final String extension) throws CoreException {
        final Map<String, List<IResource>> result = new HashMap<String, List<IResource>>();
        project.accept(new IResourceVisitor() {
            @Override
            public boolean visit(IResource arg) throws CoreException {
                if (extension.equalsIgnoreCase(arg.getFileExtension())) {
                    if (!result.containsKey(arg.getName())) {
                        result.put(arg.getName(), new ArrayList<IResource>());
                    }
                    result.get(arg.getName()).add(arg);
                }
                return true;
            }
        });
        return result;
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