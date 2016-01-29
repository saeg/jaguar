package br.usp.each.saeg.jaguar.plugin.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.mihalis.opal.rangeSlider.RangeSlider;

import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.actions.IdAction;
import br.usp.each.saeg.jaguar.plugin.actions.StartJaguarAction;
import br.usp.each.saeg.jaguar.plugin.actions.StopJaguarAction;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.DuaRequirementData;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;
import br.usp.each.saeg.jaguar.plugin.editor.OpenEditor;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchyContentProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchyLabelProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchySorter;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementContentProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementLabelProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementSorter;
import br.usp.each.saeg.jaguar.plugin.views.content.RoadmapLabelProvider;

public class JaguarView extends ViewPart {

	public static final String ID = "br.usp.each.saeg.jaguar.plugin.views.JaguarView";
	
	private final double SLIDER_PRECISION_SCALE = 1000;
	
	private TreeViewer viewer;
	private Tree tree;
	private TreeColumnLayout columnLayout;

	private Table requirementTable;
	private TableViewer requirementTableViewer;
	private TableColumnLayout tableColumnLayout;
	
	private Text textSearch;
	private RangeSlider slider;
	private IProject project;
    	private ProjectState state;
    
	private List<PackageData> originalEntities = new ArrayList<PackageData>();
	
	@Override
	public void createPartControl(Composite parent) {
		
		project = ProjectUtils.getCurrentSelectedProject();
        if (project == null) {
            return;
        }
        state = ProjectPersistence.getStateOf(project);
        if (state == null || !state.containsAnalysis()) {
            return;
        }
		
		GridData parentData = new GridData(SWT.FILL,SWT.FILL,true,true);
		parent.setLayout(new GridLayout(1,true));
		parent.setLayoutData(parentData);
		
		//sorting the widgets
		Composite textComposite = new Composite(parent,SWT.BORDER);
		Composite treeComposite = new Composite(parent,SWT.BORDER);
		Composite sliderComposite = new Composite(parent,SWT.NONE);
		Composite tableComposite = new Composite(parent,SWT.BORDER);
		
		//creating the CodeHierarchy Tree
		tree = new Tree(treeComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		columnLayout = new TreeColumnLayout();
		
		treeComposite.setLayout(columnLayout);
		
		TreeColumn column = new TreeColumn(tree,SWT.NONE);
		column.setText("Entity level");
		column.setToolTipText("click here...");
		columnLayout.setColumnData(column, new ColumnWeightData(7,0));
		
		column.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				((CodeHierarchySorter)viewer.getSorter()).doSort(1);
			}
		});
		
		
		TreeColumn column2 = new TreeColumn(tree,SWT.NONE);
		column2.setText("Score");
		columnLayout.setColumnData(column2, new ColumnWeightData(1,0));
		
		column2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				((CodeHierarchySorter)viewer.getSorter()).doSort(1);
			}
		});
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 250).applyTo(treeComposite);
		
		viewer = new TreeViewer(tree);
		viewer.setContentProvider(new CodeHierarchyContentProvider(state));
		viewer.setLabelProvider(new CodeHierarchyLabelProvider());
		viewer.setSorter(new CodeHierarchySorter());
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(getViewSite());
		
		createStructure();//to use in the experiment. the data is loaded only when the start button is clicked
		//copyInitialList();
		
		/**
		 * Display requirements that belongs to a method
		 * Only enable requirements are included in the table
		 */
		viewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedElement = thisSelection.getFirstElement();
				
				if(selectedElement instanceof PackageData){
					System.out.println("click on "+((PackageData)selectedElement).toString());
					JaguarPlugin.ui(project, viewer, "[CodeHIerarchy] click on "+((PackageData)selectedElement).toString());
					if(requirementTableViewer.getTable().getItemCount() > 0){
						if(!containsTableRequirements((PackageData)selectedElement,(RequirementData)requirementTableViewer.getElementAt(0))){
							requirementTableViewer.getTable().removeAll();
						}
					}
				}
				if(selectedElement instanceof ClassData){
					ClassData classData = (ClassData)selectedElement;
					OpenEditor.at(classData.getOpenMarker());
					System.out.println("click on "+((ClassData)selectedElement).toString());
					JaguarPlugin.ui(project, viewer, "[CodeHierarchy] click on "+((ClassData)selectedElement).toString());
					if(requirementTableViewer.getTable().getItemCount() > 0){
						if(!containsTableRequirements((ClassData)selectedElement,(RequirementData)requirementTableViewer.getElementAt(0))){
							requirementTableViewer.getTable().removeAll();
						}
					}
				}
				if(selectedElement instanceof MethodData){
					MethodData methodData = (MethodData) selectedElement;
					OpenEditor.at(methodData.getOpenMarker());
					System.out.println("click on "+((MethodData)selectedElement).toString());
					JaguarPlugin.ui(project, viewer, "[CodeHierarchy] click on "+((MethodData)selectedElement).toString());
					requirementTableViewer.getTable().removeAll();
					for(RequirementData req : methodData.getChildren()){
						if(req.isEnabled()){
							requirementTableViewer.add(req);
						}
					}
				}
			}
		});
		
		//keep the color of the selected requirement's item and change the font's color
		/*viewer.getTree().addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;
				if ((event.detail & SWT.SELECTED) == 0) return;  item not selected 
				//GC gc = event.gc;
				//Color oldBackground = gc.getBackground();
				//gc.setBackground(new Color(Display.getCurrent(),0,0,0));
				//gc.setBackground(oldBackground);
				event.detail &= ~SWT.SELECTED;
			}
		});*/
		
		//Generating the tableviewer
		
		requirementTableViewer = new TableViewer(tableComposite,SWT.SINGLE | SWT.FULL_SELECTION);
		requirementTable = requirementTableViewer.getTable();
		requirementTable.setHeaderVisible(true);
		requirementTable.setLinesVisible(false);
		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		
		ColumnViewerToolTipSupport.enableFor(requirementTableViewer, ToolTip.RECREATE);
		
		if(state.getRequirementType() == Type.LINE){
			TableViewerColumn lineViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.LEFT);
			lineViewerColumn.setLabelProvider(new RequirementLabelProvider("line"));
			lineViewerColumn.getColumn().setText("Statement");
			TableViewerColumn scoreLineViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.RIGHT);
			scoreLineViewerColumn.setLabelProvider(new RequirementLabelProvider("score"));
			scoreLineViewerColumn.getColumn().setText("Score");
			tableColumnLayout.setColumnData(lineViewerColumn.getColumn(), new ColumnWeightData(7,0));
			tableColumnLayout.setColumnData(scoreLineViewerColumn.getColumn(), new ColumnWeightData(1,0));
		}else{
			TableViewerColumn varViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.LEFT);
			varViewerColumn.setLabelProvider(new RequirementLabelProvider("var"));
			varViewerColumn.getColumn().setText("Var");
			TableViewerColumn defViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.RIGHT);
			defViewerColumn.setLabelProvider(new RequirementLabelProvider("def"));
			defViewerColumn.getColumn().setText("Def");
			TableViewerColumn useViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.RIGHT);
			useViewerColumn.setLabelProvider(new RequirementLabelProvider("use"));
			useViewerColumn.getColumn().setText("Use");
			TableViewerColumn scoreDuaViewerColumn = new TableViewerColumn(requirementTableViewer,SWT.RIGHT);
			scoreDuaViewerColumn.setLabelProvider(new RequirementLabelProvider("score"));
			scoreDuaViewerColumn.getColumn().setText("Score");
			tableColumnLayout.setColumnData(varViewerColumn.getColumn(), new ColumnWeightData(4,0));
			tableColumnLayout.setColumnData(defViewerColumn.getColumn(), new ColumnWeightData(1,0));
			tableColumnLayout.setColumnData(useViewerColumn.getColumn(), new ColumnWeightData(1,0));
			tableColumnLayout.setColumnData(scoreDuaViewerColumn.getColumn(), new ColumnWeightData(1,0));
		}
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 250).applyTo(tableComposite);
		
		requirementTableViewer.setContentProvider(new RequirementContentProvider());
		//requirementTableViewer.setLabelProvider(new RequirementLabelProvider());
		requirementTableViewer.setSorter(new RequirementSorter());
		requirementTableViewer.setInput(getViewSite());
		
		requirementTableViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent se){
				IStructuredSelection selection = (IStructuredSelection)requirementTableViewer.getSelection();
				RequirementData reqData = (RequirementData)selection.getFirstElement();
				OpenEditor.at(reqData.getMarker());
				System.out.println("click on "+reqData.toString());
				if(state.getRequirementType() == Type.LINE){
					JaguarPlugin.ui(project, requirementTableViewer, "[Line] click on "+reqData.toString());
				}else{
					JaguarPlugin.ui(project, requirementTableViewer, "[Dua] click on "+reqData.toString());
				}
			}
		});
		
		//keep the color of the selected requirement's item and change the font's color
		/*requirementTable.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;
				if ((event.detail & SWT.SELECTED) == 0) return;  item not selected 
				//GC gc = event.gc;
				//Color oldBackground = gc.getBackground();
				//gc.setBackground(new Color(Display.getCurrent(),0,0,0));
				//gc.setBackground(oldBackground);
				event.detail &= ~SWT.SELECTED;
			}
		});*/
		
		//Generating the slider
		
		sliderComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		sliderComposite.setLayout(new GridLayout(3,false));
		
		final Label labelLower = new Label(sliderComposite,SWT.LEFT);
		labelLower.setLayoutData(new GridData(GridData.FILL,GridData.BEGINNING,true,false,1,1));
		
		Label labelScore = new Label(sliderComposite,SWT.CENTER);
		labelScore.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
		labelScore.setText("Score");
		
		final Label labelUpper = new Label(sliderComposite,SWT.RIGHT);
		labelUpper.setLayoutData(new GridData(GridData.FILL,GridData.END,true,false,1,1));
		
		slider = new RangeSlider(sliderComposite,SWT.HORIZONTAL);
		slider.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,true,3,1));
		slider.setMinimum(0);
		slider.setMaximum((int)SLIDER_PRECISION_SCALE);
		slider.setLowerValue(0);
		slider.setUpperValue(1000);
		slider.setIncrement(1);
		slider.setPageIncrement(5);
		//slider.setBackground(new Color(Display.getCurrent(),255, 160, 160));
		
		labelLower.setText("Min: " + slider.getLowerValue()/SLIDER_PRECISION_SCALE);
		labelUpper.setText("Max: " + slider.getUpperValue()/SLIDER_PRECISION_SCALE);
		
		slider.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(final SelectionEvent se){
					//there's a bug in the RangeSlider when min and max values are the same
					if(((double)slider.getLowerValue() == (double)slider.getUpperValue()) && (double)slider.getLowerValue() > 0d)
						slider.setLowerValue(slider.getUpperValue()-1);
					
					labelLower.setText("Min: " + slider.getLowerValue()/SLIDER_PRECISION_SCALE);
					labelUpper.setText("Max: " + slider.getUpperValue()/SLIDER_PRECISION_SCALE);
					
					System.out.println("changed to min:"+slider.getLowerValue()/SLIDER_PRECISION_SCALE + ", max:"+slider.getUpperValue()/SLIDER_PRECISION_SCALE);
					JaguarPlugin.ui(project, slider, "changed to min:"+slider.getLowerValue()/SLIDER_PRECISION_SCALE + ", max:"+slider.getUpperValue()/SLIDER_PRECISION_SCALE);
					
					viewer.getTree().removeAll();
					checkScoreBounds(slider.getLowerValue()/SLIDER_PRECISION_SCALE,slider.getUpperValue()/SLIDER_PRECISION_SCALE);
					for(PackageData pack : originalEntities){
						if(pack.isEnabled())
							viewer.add(viewer.getInput(), pack);	
					}
					requirementTableViewer.getTable().removeAll();
				}
		});
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 65).applyTo(sliderComposite);
		
		//Generating the text field
		
		textComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		textComposite.setLayout(new GridLayout(3,false));
		
		Label labelSearch = new Label(textComposite,SWT.LEFT);
		labelSearch.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,false,false,1,1));
		labelSearch.setText("Search");
		
		textSearch = new Text(textComposite,SWT.BORDER | SWT.LEFT);
		textSearch.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		textSearch.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent me){ //refactor to avoid repeated code
				System.out.println("change to \""+textSearch.getText()+"\"");
				JaguarPlugin.ui(project, textSearch, "change to \""+textSearch.getText()+"\"");
				viewer.getTree().removeAll();
				checkScoreBounds(slider.getLowerValue()/SLIDER_PRECISION_SCALE,slider.getUpperValue()/SLIDER_PRECISION_SCALE);
				for(PackageData pack : originalEntities){
					if(pack.isEnabled())
						viewer.add(viewer.getInput(), pack);	
				}
				requirementTableViewer.getTable().removeAll();
			}
			
		});
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 60).applyTo(textComposite);
		
		if(Configuration.EXPERIMENT_VERSION){
			//adding the toolbar buttons
			StopJaguarAction stopAction = new StopJaguarAction(project,this);
			stopAction.setText("Stop debugging session");
			ImageDescriptor stopImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/stop.png");
			stopAction.setImageDescriptor(stopImage);
			
			StartJaguarAction startAction = new StartJaguarAction(project,stopAction,this);
			startAction.setText("Start debugging session");
			ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/bug.png");
			startAction.setImageDescriptor(startImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
			
			if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				IdAction idAction = new IdAction(project,startAction);
				idAction.setText("Create ID number");
				ImageDescriptor idImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/key.png");
				idAction.setImageDescriptor(idImage);
				
				getViewSite().getActionBars().getToolBarManager().add(idAction);
			}
			getViewSite().getActionBars().getToolBarManager().add(startAction);
			getViewSite().getActionBars().getToolBarManager().add(stopAction);
		}
		
		if(!Configuration.EXPERIMENT_VERSION){
			loadView();
		}
		
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	
	/**
	 * This method generates a list from the TreeViewer structure
	 */
	private void copyInitialList() {
		for(TreeItem treeItem : viewer.getTree().getItems()){
			PackageData packageItem = (PackageData)treeItem.getData();
			originalEntities.add(packageItem);
		}
	}
	
	/**
	 * Update the TreeViewer with the RangeSlider entry. The bounds are applied to the requirement level.
	 * Packages, classes, and methods which contains requirements between the bound limits will be kept in the tree
	 * @param lower
	 * @param upper
	 */
	private void checkScoreBounds(double lower, double upper) {
		boolean keepMethod = false;
		boolean keepClass = false;
		boolean keepPackage = false;
		
		for(PackageData pack : originalEntities){
			keepPackage = false;
			for(ClassData clazz : pack.getClassData()){
				keepClass = false;
				for(MethodData method : clazz.getMethodData()){
					keepMethod = false;
					for(RequirementData req : method.getRequirementData()){
						if(req.getScore() >= lower && req.getScore() <= upper && containsTerm(req)){
							req.enable();
							keepMethod = true;
						}
						else{
							req.disable();
						}
					}
					if(keepMethod){
						method.enable();
						keepClass = true;
					}
					else{
						method.disable();
						if(method.getScore() >= lower && method.getScore() <= upper && containsTerm(method)){
							method.enable();
							keepClass = true;
						}
					}
				}
				if(keepClass){
					clazz.enable();
					keepPackage = true;
				}
				else{
					clazz.disable();
					if(clazz.getScore() >= lower && clazz.getScore() <= upper && containsTerm(clazz)){
						clazz.enable();
						keepPackage = true;
					}
				}
			}
			if(keepPackage){
				pack.enable();
			}
			else{
				pack.disable();
				if(pack.getScore() >= lower && pack.getScore() <= upper && containsTerm(pack)){
					pack.enable();
				}
			}
		}
	}
	
	private boolean containsTerm(Object element){ //pass this to SuspiciousElement, and checkStatus and updateStatus too
		if(StringUtils.isBlank(textSearch.getText())){
			return true;
		}
		if(element instanceof PackageData){
			if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((PackageData)element).getName(), StringUtils.trim(textSearch.getText()))){
				return true;
			}
		}
		if(element instanceof ClassData){
			if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((ClassData)element).getName(), StringUtils.trim(textSearch.getText()))){
				return true;
			}
		}
		if(element instanceof MethodData){
			if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((MethodData)element).getName(), StringUtils.trim(textSearch.getText()))){
				return true;
			}
		}
		if(element instanceof RequirementData){
			if(state.getRequirementType() == Type.LINE){
				if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((RequirementData)element).getValue().trim(), StringUtils.trim(textSearch.getText()))){
					return true;
				}
			}else{
				if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((DuaRequirementData)element).getVar(), StringUtils.trim(textSearch.getText()))){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Keep the requirements in TableViewer if is an ancestor of such requirements
	 * @return
	 */
	private boolean containsTableRequirements(Object ancestor, RequirementData requirement){
		if(ancestor instanceof PackageData){
			for(ClassData clazz : ((PackageData)ancestor).getClassData()){
				for(MethodData method : clazz.getMethodData()){
					for(RequirementData req : method.getRequirementData()){
						if(req.equals(requirement)){
							return true;
						}
					}
				}
			}
		}
		if(ancestor instanceof ClassData){
			for(MethodData method : ((ClassData)ancestor).getMethodData()){
				for(RequirementData req : method.getRequirementData()){
					if(req.equals(requirement)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * Load the data when the start button is clicked - to be used in the experiments
	 * */
	public void loadView() {
		checkScoreBounds(slider.getLowerValue()/SLIDER_PRECISION_SCALE,slider.getUpperValue()/SLIDER_PRECISION_SCALE);
		for(PackageData pack : originalEntities){
			if(pack.isEnabled())
				viewer.add(viewer.getInput(), pack);	
		}
		requirementTableViewer.getTable().removeAll();
	}
	
	private void createStructure(){
		originalEntities = state.getPackageDataResult();
	}
	
}