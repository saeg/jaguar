package br.usp.each.saeg.jaguar.plugin.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.actions.IdAction;
import br.usp.each.saeg.jaguar.plugin.actions.StartJaguarAction;
import br.usp.each.saeg.jaguar.plugin.actions.StopJaguarAction;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.editor.OpenEditor;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.views.content.RoadmapContentProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RoadmapLabelProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RoadmapSorter;

public class MethodView extends ViewPart {
	
	public static final String ID = "br.usp.each.saeg.jaguar.plugin.views.MethodView";
	private final double SLIDER_PRECISION_SCALE = 1000;
	private final int LEVEL_SCORE = 2;
	private TableViewer viewer;
	private Table roadmapTable;
	private TableColumnLayout roadmapTableColumnLayout;
	private List<MethodData> originalRoadmap = new ArrayList<MethodData>();
	private Text textSearch;
	private Slider slider;
	private IProject project;
    private ProjectState state;
	
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
		Composite roadmapComposite = new Composite(parent,SWT.BORDER);
		Composite textComposite = new Composite(parent,SWT.BORDER);
		Composite sliderComposite = new Composite(parent,SWT.NONE);
		
		//Generating the roadmap 
		
		viewer = new TableViewer(roadmapComposite,SWT.SINGLE | SWT.FULL_SELECTION);
		
		roadmapTable = viewer.getTable();
		roadmapTable.setHeaderVisible(true);
		roadmapTable.setLinesVisible(false);
		roadmapTableColumnLayout = new TableColumnLayout();
		roadmapComposite.setLayout(roadmapTableColumnLayout);
		
		TableViewerColumn methodViewerColumn = new TableViewerColumn(viewer,SWT.LEFT);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.RECREATE);
		methodViewerColumn.setLabelProvider(new RoadmapLabelProvider("method"));
		methodViewerColumn.getColumn().setText((Configuration.LANGUAGE_EN)?"Class.Method":"Classe.Metodo");
		
		TableViewerColumn scoreViewerColumn = new TableViewerColumn(viewer,SWT.RIGHT);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.RECREATE);
		scoreViewerColumn.setLabelProvider(new RoadmapLabelProvider("score"));
		scoreViewerColumn.getColumn().setText((Configuration.LANGUAGE_EN)?"Score":"Valor");
		
		roadmapTableColumnLayout.setColumnData(methodViewerColumn.getColumn(), new ColumnWeightData(7,0));
		roadmapTableColumnLayout.setColumnData(scoreViewerColumn.getColumn(), new ColumnWeightData(1,0));
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 250).applyTo(roadmapComposite);
		
		viewer.setContentProvider(new RoadmapContentProvider(state));
		viewer.setSorter(new RoadmapSorter());
		viewer.setInput(getViewSite());
		
		createStructure();//to use in the experiment. the data is loaded only when the start button is clicked
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent event){
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				MethodData methodData = (MethodData)selection.getFirstElement();
				OpenEditor.at(methodData.getOpenMarker());
				System.out.println("[Roadmap] click @ "+methodData.toString());
				JaguarPlugin.ui(project,viewer, "[Roadmap] click @ "+methodData.toString());
			}
		});
		
		//Generating the slider
		sliderComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		sliderComposite.setLayout(new GridLayout(3,false));
		
		final Label labelLower = new Label(sliderComposite,SWT.LEFT);
		labelLower.setLayoutData(new GridData(GridData.FILL,GridData.BEGINNING,true,false,1,1));
		
		final Label labelScore = new Label(sliderComposite,SWT.CENTER);
		labelScore.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
				
		final Label labelUpper = new Label(sliderComposite,SWT.RIGHT);
		labelUpper.setLayoutData(new GridData(GridData.FILL,GridData.END,true,false,1,1));
		
		slider = new Slider(sliderComposite,SWT.HORIZONTAL);
		slider.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,true,3,1));
		
		slider.setMinimum(0);
		slider.setMaximum((int)SLIDER_PRECISION_SCALE);
		slider.setSelection(0);
		slider.setIncrement(1);
		slider.setPageIncrement(5);
		
		labelLower.setText("Min: " + slider.getMinimum()/SLIDER_PRECISION_SCALE);
		labelUpper.setText("Max: " + slider.getMaximum()/SLIDER_PRECISION_SCALE);
		labelScore.setText(((Configuration.LANGUAGE_EN)?"Current min score: ":"Valor minimo atual: ")+slider.getSelection()/SLIDER_PRECISION_SCALE);
				
		slider.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(final SelectionEvent se){
					
				labelScore.setText(((Configuration.LANGUAGE_EN)?"Current min score: ":"Valor minimo atual: ")+slider.getSelection()/SLIDER_PRECISION_SCALE);
				
				System.out.println("changed min to:"+slider.getSelection()/SLIDER_PRECISION_SCALE);
				JaguarPlugin.ui(project, slider, "changed min to:"+slider.getSelection()/SLIDER_PRECISION_SCALE);
				
				viewer.getTable().removeAll();
				checkScoreBounds(slider.getSelection()/SLIDER_PRECISION_SCALE,slider.getMaximum()/SLIDER_PRECISION_SCALE);
				for(MethodData method : originalRoadmap){
					if(method.isEnabled())
						viewer.add(method);	
				}
			}
		});
						
		GridDataFactory.fillDefaults().grab(true, false).hint(400, 50).applyTo(sliderComposite);
		
		//Generating the text field
		
		textComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		textComposite.setLayout(new GridLayout(3,false));
		
		Label labelSearch = new Label(textComposite,SWT.LEFT);
		labelSearch.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,false,false,1,1));
		labelSearch.setText((Configuration.LANGUAGE_EN)?"Search:":"Busca:");
		
		textSearch = new Text(textComposite,SWT.BORDER | SWT.LEFT);
		textSearch.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		textSearch.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent me){ //refactor to avoid repeated code
				System.out.println("change to \""+textSearch.getText()+"\"");
				JaguarPlugin.ui(project, textSearch, "change to \""+textSearch.getText()+"\"");
				viewer.getTable().removeAll();
				checkScoreBounds(slider.getSelection()/SLIDER_PRECISION_SCALE,slider.getMaximum()/SLIDER_PRECISION_SCALE);//for RangeSlider
				for(MethodData method : originalRoadmap){
					if(method.isEnabled())
						viewer.add(method);	
				}
			}
		});
						
		GridDataFactory.fillDefaults().grab(true, false).hint(400, 35).applyTo(textComposite);	
		
		if(Configuration.EXPERIMENT_VERSION){
		//adding the toolbar buttons
			StopJaguarAction stopAction = new StopJaguarAction(project,this);
			stopAction.setText((Configuration.LANGUAGE_EN)?"Stop debugging session":"Finalizar depuracao");
			ImageDescriptor stopImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/stop.png");
			stopAction.setImageDescriptor(stopImage);
			
			StartJaguarAction startAction = new StartJaguarAction(project,stopAction,this);
			startAction.setText((Configuration.LANGUAGE_EN)?"Start debugging session":"Iniciar depuracao");
			ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/bug.png");
			startAction.setImageDescriptor(startImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
			
			if(!Configuration.EXTERNAL_ID_GENERATION){
				if(Configuration.EXPERIMENT_JAGUAR_FIRST){
					IdAction idAction = new IdAction(project,startAction);
					idAction.setText((Configuration.LANGUAGE_EN)?"Create ID number":"Criar numero ID");
					ImageDescriptor idImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/key.png");
					idAction.setImageDescriptor(idImage);
									
					getViewSite().getActionBars().getToolBarManager().add(idAction);
				}
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
		for(TableItem tableItem : viewer.getTable().getItems()){
			MethodData methodItem = (MethodData)tableItem.getData();
			originalRoadmap.add(methodItem);
		}
	}
	
	/**
	 * Update the TableViewer with the RangeSlider entry. The bounds are applied to the requirement level.
	 * Methods which contains requirements between the bound limits will be kept in the table
	 * @param lower
	 * @param upper
	 */
	private void checkScoreBounds(double lower, double upper) {
		boolean keepMethod = false;
		
		for(MethodData method : originalRoadmap){
			method.disable();
			if(method.getScore() >= lower && method.getScore() <= upper && containsTerm(method)){
				method.enable();
			}
		}
	}
	
	private boolean containsTerm(Object element){ //pass this to SuspiciousElement, and checkStatus and updateStatus too
		if(StringUtils.isBlank(textSearch.getText())){
			return true;
		}
		if(element instanceof MethodData){
			if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(((MethodData)element).getName(), StringUtils.trim(textSearch.getText()))){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Load the data when the start button is clicked - to be used in the experiments
	 * */
	public void loadView() {
		checkScoreBounds(slider.getSelection()/SLIDER_PRECISION_SCALE,slider.getMaximum()/SLIDER_PRECISION_SCALE);
		for(MethodData method : originalRoadmap){
			if(method.isEnabled())
				viewer.add(method);	
		}
	}
	
	private void createStructure(){
		disableNonExecutedElements();
		originalRoadmap = getMethodList();
	}
	
	private List<MethodData> getMethodList(){
		List<MethodData> methodList = new ArrayList<MethodData> ();
		List<PackageData> listPackageData = state.getPackageDataResult();
		for(PackageData pack : listPackageData){
			for(ClassData clazz : pack.getClassData()){
				for(MethodData method : clazz.getMethodData()){
					if(method.getScore() > 0){
						methodList.add(method);
					}
				}
			}
		}
		Collections.sort(methodList);
		return methodList;
	}
	
	public void disableNonExecutedElements(){
		List<PackageData> listPackageData = state.getPackageDataResult();
		for(PackageData packData : listPackageData){
			for(ClassData classData : packData.getClassData()){
				for(MethodData methodData : classData.getMethodData()){
					if(methodData.getScore() < 0)
						methodData.disable();
				}
				if(classData.getScore() < 0)
					classData.disable();
			}
			if(packData.getScore() < 0)
				packData.disable();
		}
	}
	
}
