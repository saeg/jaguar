package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

public class ExecutionListener implements IExecutionListener {

    public void register() {
      ICommandService commandService = (ICommandService)PlatformUI.getWorkbench().getService(ICommandService.class);
      commandService.addExecutionListener(this);
    }

    @Override
    public void notHandled(String commandId, NotHandledException exception) {
        System.out.println("notHandled " + commandId);
    }

    @Override
    public void postExecuteFailure(String commandId, ExecutionException exception) {
        System.out.println("postExecuteFailure " + commandId);
    }

    @Override
    public void postExecuteSuccess(String commandId, Object returnValue) {
        System.out.println("postExecuteSuccess " + commandId);
    }

    @Override
    public void preExecute(String commandId, ExecutionEvent event) {
        System.out.println("preExecute " + commandId);
    }

}