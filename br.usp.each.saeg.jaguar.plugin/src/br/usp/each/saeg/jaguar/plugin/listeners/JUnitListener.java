package br.usp.each.saeg.jaguar.plugin.listeners;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.launching.JavaRuntime;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

/**
 * @author Higor Amario (higoramario@gmail.com
 */
public class JUnitListener extends TestRunListener {
	
 	public void register(){
		JUnitCore.addTestRunListener(this);
	}

	public void sessionStarted(ITestRunSession session){
		System.out.println("test started!");
		IProject project = session.getLaunchedProject().getProject();
		JaguarPlugin.ui(project, this, "JUnit session started");
	}
	
	public void sessionFinished(ITestRunSession session){
		System.out.println("test finished!");
		IProject project = session.getLaunchedProject().getProject();
		JaguarPlugin.ui(project, this, "JUnit session finished");
	}
	
}
