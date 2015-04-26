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

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IPackageFragmentRoot;

/**
 * Implementation of {@link ICoverageLaunch}.
 */
public class CoverageLaunch {

  private final AgentServer agentServer;

  public CoverageLaunch(ILaunchConfiguration launchConfiguration,
      Set<IPackageFragmentRoot> scope) {
    this.agentServer = new AgentServer(false);
  }

  public AgentServer getAgentServer() {
    return agentServer;
  }

  public void requestDump(boolean reset) throws CoreException {
    agentServer.requestDump(reset);
  }

}
