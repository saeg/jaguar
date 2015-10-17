package br.usp.each.saeg.jaguar.plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;

import br.usp.each.saeg.jaguar.plugin.Configuration;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public abstract class OnlyAfterColoringHandler extends AbstractHandler {

	@Override
    public boolean isEnabled() {
        /*IProject project = ProjectUtils.getCurrentSelectedProject();
        if (project == null || !project.isOpen()) {
            return false;
        }

        ProjectState state = ProjectPersistence.getStateOf(project);
        if (state == null) {
            return false;
        }
        if (state.isAnalyzed()) {
            return true;
        }*/
		if(Configuration.EXPERIMENT_VERSION){
			return false;
		}
		
        return true;
    }

}
