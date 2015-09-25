package br.usp.each.saeg.jaguar.plugin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.PluginCleanup;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;


/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class RemoveColorHandler extends OnlyAfterColoringHandler {

	@Override
    public Object execute(ExecutionEvent arg) throws ExecutionException {
        IProject project = ProjectUtils.getCurrentSelectedProject();
        PluginCleanup.clean(project);
        JaguarPlugin.ui(project, this, "removing coloring analysis");
        return null;
    }
}
