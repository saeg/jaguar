package br.usp.each.saeg.jaguar.plugin.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.mihalis.opal.rangeSlider.RangeSlider;
import org.apache.commons.lang3.*;

import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.SuspiciousElement;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchyContentProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchyLabelProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.CodeHierarchySorter;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementContentProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementLabelProvider;
import br.usp.each.saeg.jaguar.plugin.views.content.RequirementSorter;

public class JaguarView extends ViewPart {

	public static final String ID = "br.usp.each.saeg.jaguar.plugin.views.JaguarView";
	
	private final double SLIDER_PRECISION_SCALE = 1000;
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	private Tree tree;
	private TreeColumnLayout columnLayout;

	private Table requirementTable;
	private TableViewer requirementTableViewer;
	private TableColumnLayout tableColumnLayout;
	
	private Text textSearch;
	
	private List<Package> originalEntities = new ArrayList<Package>();
	
	@Override
	public void createPartControl(Composite parent) {
		GridData parentData = new GridData(SWT.FILL,SWT.FILL,true,true);
		parent.setLayout(new GridLayout(1,true));
		parent.setLayoutData(parentData);
		
		Composite treeComposite = new Composite(parent,SWT.BORDER);
		tree = new Tree(treeComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		columnLayout = new TreeColumnLayout();
		
		treeComposite.setLayout(columnLayout);
		
		TreeColumn column = new TreeColumn(tree,SWT.NONE);
		column.setText("Entity level");
		column.setToolTipText("click here...");
		columnLayout.setColumnData(column, new ColumnWeightData(3,0));
		
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
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 200).applyTo(treeComposite);
		
		viewer = new TreeViewer(tree);
		
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new CodeHierarchyContentProvider());
		viewer.setLabelProvider(new CodeHierarchyLabelProvider());
		viewer.setSorter(new CodeHierarchySorter());
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(getViewSite());
		//makeActions();
		//hookContextMenu();
		//hookDoubleClickAction();
		//contributeToActionBars();
		
		copyInitialList();
		
		/**
		 * Display requirements that belongs to a method
		 * Only enable requirements are included in the table
		 */
		viewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedElement = thisSelection.getFirstElement();
				
				if(selectedElement instanceof Package){
					System.out.println(((Package) selectedElement).getName());
					if(requirementTableViewer.getTable().getItemCount() > 0){
						if(!containsTableRequirements((Package)selectedElement,(Requirement)requirementTableViewer.getElementAt(0))){
							requirementTableViewer.getTable().removeAll();
						}
					}
				}
				if(selectedElement instanceof Class){
					System.out.println(((Class) selectedElement).getName());
					if(requirementTableViewer.getTable().getItemCount() > 0){
						if(!containsTableRequirements((Class)selectedElement,(Requirement)requirementTableViewer.getElementAt(0))){
							requirementTableViewer.getTable().removeAll();
						}
					}
				}
				if(selectedElement instanceof Method){
					Method method = (Method) selectedElement;
					requirementTableViewer.getTable().removeAll();
					for(Requirement req : method.getChildren()){
						if(req.isEnabled()){
							requirementTableViewer.add(req);
						}
					}
				}
			}
		});
		
		//Generating the tableviewer
		
		Composite tableComposite = new Composite(parent,SWT.BORDER);
		
		requirementTableViewer = new TableViewer(tableComposite,SWT.SINGLE | SWT.FULL_SELECTION);
		requirementTable = requirementTableViewer.getTable();
		requirementTable.setHeaderVisible(true);
		requirementTable.setLinesVisible(false);
		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		
		TableColumn tableColumn1 = new TableColumn(requirementTable,SWT.LEFT);
		tableColumn1.setText("Nodes");
		tableColumnLayout.setColumnData(tableColumn1, new ColumnWeightData(3,0));
		TableColumn tableColumn2 = new TableColumn(requirementTable,SWT.RIGHT);
		tableColumn2.setText("Score");
		tableColumnLayout.setColumnData(tableColumn2, new ColumnWeightData(1,0));
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 200).applyTo(tableComposite);
		
		requirementTableViewer.setContentProvider(new RequirementContentProvider());
		requirementTableViewer.setLabelProvider(new RequirementLabelProvider());
		requirementTableViewer.setSorter(new RequirementSorter());
		requirementTableViewer.setInput(getViewSite());
		
		requirementTableViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent se){
				IStructuredSelection selection = (IStructuredSelection)requirementTableViewer.getSelection();
				System.out.println("Requirement selected: "+((Requirement)selection.getFirstElement()).getName() + " - open source code here");
			}
		});
		
		//Generating the slider
		
		Composite sliderComposite = new Composite(parent,SWT.NONE);
		sliderComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		sliderComposite.setLayout(new GridLayout(3,false));
		
		final Label labelLower = new Label(sliderComposite,SWT.LEFT);
		labelLower.setLayoutData(new GridData(GridData.FILL,GridData.BEGINNING,true,false,1,1));
		
		Label labelScore = new Label(sliderComposite,SWT.CENTER);
		labelScore.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
		labelScore.setText("Score");
		
		final Label labelUpper = new Label(sliderComposite,SWT.RIGHT);
		labelUpper.setLayoutData(new GridData(GridData.FILL,GridData.END,true,false,1,1));
		
		final RangeSlider slider = new RangeSlider(sliderComposite,SWT.HORIZONTAL);
		slider.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,true,3,1));
		slider.setMinimum(0);
		slider.setMaximum((int)SLIDER_PRECISION_SCALE);
		slider.setLowerValue(0);
		slider.setUpperValue(999);
		slider.setIncrement(1);
		slider.setPageIncrement(5);
		
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
					
					viewer.getTree().removeAll();
					checkScoreBounds(slider.getLowerValue()/SLIDER_PRECISION_SCALE,slider.getUpperValue()/SLIDER_PRECISION_SCALE);
					for(Package pack : originalEntities){
						if(pack.isEnabled())
							viewer.add(viewer.getInput(), pack);	
					}
					requirementTableViewer.getTable().removeAll();
				}
		});
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 150).applyTo(sliderComposite);
		
		//Generating the text field
		
		Composite textComposite = new Composite(parent,SWT.BORDER);
		textComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		textComposite.setLayout(new GridLayout(3,false));
		
		Label labelSearch = new Label(textComposite,SWT.LEFT);
		labelSearch.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,false,false,1,1));
		labelSearch.setText("Search");
		
		textSearch = new Text(textComposite,SWT.BORDER | SWT.LEFT);
		textSearch.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		textSearch.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent me){ //refactor to avoid repeated code
				viewer.getTree().removeAll();
				checkScoreBounds(slider.getLowerValue()/SLIDER_PRECISION_SCALE,slider.getUpperValue()/SLIDER_PRECISION_SCALE);
				for(Package pack : originalEntities){
					if(pack.isEnabled())
						viewer.add(viewer.getInput(), pack);	
				}
				requirementTableViewer.getTable().removeAll();
			}
			
		});
		
		GridDataFactory.fillDefaults().grab(true, true).hint(400, 130).applyTo(textComposite);
		
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
			Package packageItem = (Package)treeItem.getData();
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
		
		for(Package pack : originalEntities){
			keepPackage = false;
			for(Class clazz : pack.getClasses()){
				keepClass = false;
				for(Method method : clazz.getMethods()){
					keepMethod = false;
					for(Requirement req : method.getRequirements()){
						if(req.getSuspiciousValue() >= lower && req.getSuspiciousValue() <= upper && containsTerm(req)){
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
						if(method.getSuspiciousValue() >= lower && method.getSuspiciousValue() <= upper && containsTerm(method)){
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
					if(clazz.getSuspiciousValue() >= lower && clazz.getSuspiciousValue() <= upper && containsTerm(clazz)){
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
				if(pack.getSuspiciousValue() >= lower && pack.getSuspiciousValue() <= upper && containsTerm(pack)){
					pack.enable();
				}
			}
		}
	}
	
	private boolean containsTerm(SuspiciousElement element){ //pass this to SuspiciousElement, and checkStatus and updateStatus too
		if(StringUtils.isBlank(textSearch.getText())){
			return true;
		}
		if(StringUtils.isNotBlank(textSearch.getText()) && StringUtils.containsIgnoreCase(element.getName(), StringUtils.trim(textSearch.getText()))){
			return true;
		}
		return false;
	}
	
	/**
	 * Keep the requirements in TableViewer if is an ancestor of such requirements
	 * @return
	 */
	private boolean containsTableRequirements(SuspiciousElement ancestor, Requirement requirement){
		if(ancestor instanceof Package){
			for(Class clazz : ((Package)ancestor).getClasses()){
				for(Method method : clazz.getMethods()){
					for(Requirement req : method.getRequirements()){
						if(req.equals(requirement)){
							return true;
						}
					}
				}
			}
		}
		if(ancestor instanceof Class){
			for(Method method : ((Class)ancestor).getMethods()){
				for(Requirement req : method.getRequirements()){
					if(req.equals(requirement)){
						return true;
					}
				}
			}
		}
		return false;
	}
}