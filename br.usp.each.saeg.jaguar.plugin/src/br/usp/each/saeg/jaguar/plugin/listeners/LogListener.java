package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogListener implements ILogListener {
	
	private final static Logger logger = LoggerFactory.getLogger(LogListener.class.getName());

	@Override
	public void logging(IStatus status, String plugin) {
		if (status.getSeverity() == IStatus.WARNING) {
			if (status.getException() == null) {
				logger.warn(status.getMessage());
			} else {
				logger.warn(status.getMessage() + status.getException());
			}
		} else if (status.getSeverity() == IStatus.ERROR) {
			if (status.getException() == null) {
				logger.error(status.getMessage());
			} else {
				logger.error(status.getMessage() + status.getException());
			}
		} else {
			if (status.getException() == null) {
				logger.info(status.getMessage());
			} else {
				logger.info(status.getMessage() + status.getException());
			}
		}
	}
}
