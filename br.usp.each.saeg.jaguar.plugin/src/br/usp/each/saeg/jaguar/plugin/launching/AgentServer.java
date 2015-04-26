/*******************************************************************************
 * Copyright (c) 2006, 2014 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 ******************************************************************************/
package br.usp.each.saeg.jaguar.plugin.launching;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.jacoco.core.data.AbstractExecutionDataStore;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.data.dua.DataflowExecutionDataStore;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

/**
 * Internal TCP/IP server for the JaCoCo agent to connect to.
 * 
 */
public class AgentServer implements Runnable {

  private ServerSocket serverSocket;
  private RemoteControlWriter writer;
  private RemoteControlReader reader;
  private volatile boolean shouldRun;
  
  private final boolean isDataFlow;

  private SessionInfoStore sessionInfo;
  private AbstractExecutionDataStore executionData;
  
  private static AgentServer instance;

  public AgentServer(boolean isDataFlow) {
    this.shouldRun = true;
    this.isDataFlow = isDataFlow;

    this.sessionInfo = new SessionInfoStore();
    this.executionData = this.isDataFlow ? new DataflowExecutionDataStore() : new ExecutionDataStore();
    
    AgentServer.instance = this;
  }

  public void restartCoverageData() {
	  sessionInfo = new SessionInfoStore();
	  executionData = this.isDataFlow ? new DataflowExecutionDataStore() : new ExecutionDataStore();
	  reader.setSessionInfoVisitor(sessionInfo);
	  reader.setExecutionDataVisitor(executionData);
  }
  
  public SessionInfoStore getSessionInfo() {
	  return sessionInfo;
  }

  public AbstractExecutionDataStore getExecutionData() {
	  return executionData;
  }

  public static AgentServer getInstance(){
	  return instance;
  }

  public void start() throws CoreException {
    try {
      // Bind an available port on the loopback interface:
      serverSocket = new ServerSocket(0, 0, InetAddress.getByName(null));
    } catch (IOException e) {
    	// TODO throw new CoreException(EclEmmaStatus.AGENTSERVER_START_ERROR.getStatus(e));
    	e.printStackTrace();
    }
  }

  public void requestDump(boolean reset) throws CoreException {
    if (writer != null) {
      try {
        writer.visitDumpCommand(true, reset);
      } catch (IOException e) {
       		// TODO throw new CoreException(EclEmmaStatus.DUMP_REQUEST_ERROR.getStatus(e));
    	  e.printStackTrace();
      }
    }
  }

  public void stop() {
    writer = null;
    try {
      serverSocket.close();
    } catch (IOException e) {
    	// TODO EclEmmaCorePlugin.getInstance().getLog().log(EclEmmaStatus.AGENTSERVER_STOP_ERROR.getStatus(e));
    	e.printStackTrace();
    }
    shouldRun = false;
  }

  public int getPort() {
    return serverSocket.getLocalPort();
  }

  @Override
  public void run() {
	  try {
	      final Socket socket = serverSocket.accept();
		  writer = new RemoteControlWriter(socket.getOutputStream());
		  reader = new RemoteControlReader(socket.getInputStream());
		  
		  reader.setSessionInfoVisitor(sessionInfo);
		  reader.setExecutionDataVisitor(executionData);

		  while (shouldRun) {
			  reader.read();
		  }
	  } catch (IOException e) {
		  // return EclEmmaStatus.EXECDATA_DUMP_ERROR.getStatus(e); TODO
		  e.printStackTrace();
	  }
  }
  
  
}
