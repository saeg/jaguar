package br.usp.each.saeg.jaguar.plugin.views.content;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.data.MethodData;
import br.usp.each.saeg.jaguar.plugin.data.PackageData;
import br.usp.each.saeg.jaguar.plugin.data.ClassData;
import br.usp.each.saeg.jaguar.plugin.data.RequirementData;

public class CodeHierarchyContentProvider implements ITreeContentProvider,
		IStructuredContentProvider {

	private final Object[] EMPTY_LIST = new Object[0];
	private ProjectState state;
	
	public CodeHierarchyContentProvider(ProjectState state){
		this.state = state;
		disableNonExecutedElements();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object[] getChildren(Object element) {
		return checkStatus(element);
	}
	
	@Override
	public Object[] getElements(Object arg0) {
		return createStructure();
		//return createFakeStructure();
		//return readXml();
	}

	@Override
	public Object getParent(Object arg0) {
		return null;
	}
	
	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof PackageData)
			return !((PackageData)element).getClassData().isEmpty();
		if(element instanceof ClassData)
			return !((ClassData)element).getMethodData().isEmpty();
		return false;
	}
		
	/*@Override
	public boolean hasChildren(Object element) {
		if(element instanceof Package)
			return !((Package)element).getClasses().isEmpty();
		if(element instanceof Class)
			return !((Class)element).getMethods().isEmpty();
		if(element instanceof Method)
			return !((Method)element).getRequirements().isEmpty();
		return false;
	}*/
	
	private Object[] createStructure(){
		List<PackageData> listPackageData = state.getPackageDataResult();
		return listPackageData.toArray();
	}
	
	/**
	 * Read an input xml file with the suspiciousness list
	 * @return
	 */
	public Object[] readXml(){
		XmlDataReader xml = new XmlDataReader();
		return xml.readXmlFile();
	}
	
	/**
	 * Check if the children must appear or not in the structure
	 * @param element
	 * @return
	 */
	private Object[] checkStatus(Object element) {
		if(element instanceof PackageData){
			List<ClassData> classDataList = new ArrayList<ClassData>();
			for(ClassData classData : ((PackageData) element).getClassData()){
				if(classData.isEnabled()){
					classDataList.add(classData);
				}
			}
			return classDataList.toArray();
		}
		if(element instanceof ClassData){
			List<MethodData> methodDataList = new ArrayList<MethodData>();
			for(MethodData methodData : ((ClassData) element).getMethodData()){
				if(methodData.isEnabled()){
					methodDataList.add(methodData);
				}
			}
			return methodDataList.toArray();
		}
		return EMPTY_LIST;
	}
	
	/**
	 * Check if the children must appear or not in the structure
	 * @param element
	 * @return
	 */
	private Object[] checkStatusOld(Object element) {
		if(element instanceof Package){
			List<Class> classList = new ArrayList<Class>();
			for(Class clazz : ((Package) element).getClasses()){
				if(clazz.isEnabled()){
					classList.add(clazz);
				}
			}
			return classList.toArray();
		}
		if(element instanceof Class){
			List<Method> methodList = new ArrayList<Method>();
			for(Method method : ((Class) element).getMethods()){
				if(method.isEnabled()){
					methodList.add(method);
				}
			}
			return methodList.toArray();
		}
		/*if(element instanceof Method){
			List<Requirement> requirementList = new ArrayList<Requirement>();
			for(Requirement requirement : ((Method) element).getRequirements()){
				if(requirement.isEnabled()){
					requirementList.add(requirement);
				}
			}
			return requirementList.toArray();
		}*/
		return EMPTY_LIST;
	}
	
	public void disableNonExecutedElements(){
		List<PackageData> listPackageData = state.getPackageDataResult();
		for(PackageData packData : listPackageData){
			for(ClassData classData : packData.getClassData()){
				for(MethodData methodData : classData.getMethodData()){
					for(RequirementData reqData : methodData.getRequirementData()){
						if(reqData.getScore() < 0)
							reqData.disable();
					}
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
	
	
	/**
	 * An example structure
	 * @return
	 */
	public Object[] createFakeStructure(){
		List<Package> packageList = new ArrayList<Package>();
		List<Class>  classList1 = new ArrayList<Class> ();
		List<Class>  classList2 = new ArrayList<Class> ();
		List<Method>  methodList1 = new ArrayList<Method> ();
		List<Method>  methodList2 = new ArrayList<Method> ();
		List<Method>  methodList3 = new ArrayList<Method> ();
		List<Requirement>  requirementList1 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList2 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList3 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList4 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList5 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList6 = new ArrayList<Requirement> ();
		List<Requirement>  requirementList7 = new ArrayList<Requirement> ();
		
		Package pack1 = new Package();
		Package pack2 = new Package();
		Class class1 = new Class();
		Class class2 = new Class();
		Class class3 = new Class();
		Method method1 = new Method();
		Method method2 = new Method();
		Method method3 = new Method();
		Method method4 = new Method();
		Method method5 = new Method();
		Method method6 = new Method();
		Method method7 = new Method();
		LineRequirement requirement1 = new LineRequirement();
		LineRequirement requirement2 = new LineRequirement();
		LineRequirement requirement3 = new LineRequirement();
		LineRequirement requirement4 = new LineRequirement();
		LineRequirement requirement5 = new LineRequirement();
		LineRequirement requirement6 = new LineRequirement();
		LineRequirement requirement7 = new LineRequirement();
		LineRequirement requirement8 = new LineRequirement();
		LineRequirement requirement9 = new LineRequirement();
		LineRequirement requirement10 = new LineRequirement();
		LineRequirement requirement11 = new LineRequirement();
		LineRequirement requirement12 = new LineRequirement();
		LineRequirement requirement13 = new LineRequirement();
		LineRequirement requirement14 = new LineRequirement();
		LineRequirement requirement15 = new LineRequirement();
		LineRequirement requirement16 = new LineRequirement();
		LineRequirement requirement17 = new LineRequirement();
		LineRequirement requirement18 = new LineRequirement();
		
		pack1.setName("Package 1");
		pack1.setSuspiciousValue(1.0);
		pack2.setName("Package 2");
		pack2.setSuspiciousValue(0.99);
		
		class1.setName("Class 1");
		class1.setSuspiciousValue(1.0);
		class2.setName("Class 2");
		class2.setSuspiciousValue(0.0);
		class3.setName("Class 3");
		class3.setSuspiciousValue(0.99);
		
		method1.setName("Method 1");
		method1.setSuspiciousValue(1.0);
		method2.setName("Method 2");
		method2.setSuspiciousValue(0.0);
		method3.setName("Method 3");
		method3.setSuspiciousValue(0.99);
		method4.setName("Method 4");
		method4.setSuspiciousValue(0.0);
		method5.setName("Method 5");
		method5.setSuspiciousValue(0.99);
		method6.setName("Method 6");
		method6.setSuspiciousValue(0.97);
		method7.setName("Method 7");
		method7.setSuspiciousValue(0.5);
		
		requirement1.setName("0");
		requirement1.setSuspiciousValue(1.0);
		requirement2.setName("10");
		requirement2.setSuspiciousValue(1.0);
		requirement3.setName("33");
		requirement3.setSuspiciousValue(0.95);
		requirement4.setName("102");
		requirement4.setSuspiciousValue(0.0);
		requirement5.setName("0");
		requirement5.setSuspiciousValue(0.0);
		requirement6.setName("7");
		requirement6.setSuspiciousValue(0.0);
		requirement7.setName("12");
		requirement7.setSuspiciousValue(0.5);
		requirement8.setName("10");
		requirement8.setSuspiciousValue(0.99);
		requirement9.setName("13");
		requirement9.setSuspiciousValue(0.9);
		requirement10.setName("0");
		requirement10.setSuspiciousValue(0.0);
		requirement11.setName("0");
		requirement11.setSuspiciousValue(0.99);
		requirement12.setName("15");
		requirement12.setSuspiciousValue(0.99);
		requirement13.setName("32");
		requirement13.setSuspiciousValue(0.93);
		requirement14.setName("0");
		requirement14.setSuspiciousValue(0.97);
		requirement15.setName("11");
		requirement15.setSuspiciousValue(0.71);
		requirement16.setName("21");
		requirement16.setSuspiciousValue(0.0);
		requirement17.setName("0");
		requirement17.setSuspiciousValue(0.5);
		requirement18.setName("60");
		requirement18.setSuspiciousValue(0.0);
		
		requirementList1.add(requirement1);
		requirementList1.add(requirement2);
		requirementList1.add(requirement3);
		requirementList1.add(requirement4);
		requirementList2.add(requirement5);
		requirementList2.add(requirement6);
		requirementList3.add(requirement7);
		requirementList3.add(requirement8);
		requirementList3.add(requirement9);
		requirementList4.add(requirement10);
		requirementList5.add(requirement11);
		requirementList5.add(requirement12);
		requirementList5.add(requirement13);
		requirementList6.add(requirement14);
		requirementList6.add(requirement15);
		requirementList6.add(requirement16);
		requirementList7.add(requirement17);
		requirementList7.add(requirement18);
		
		method1.setRequirements(requirementList1);
		method2.setRequirements(requirementList2);
		method3.setRequirements(requirementList3);
		method4.setRequirements(requirementList4);
		method5.setRequirements(requirementList5);
		method6.setRequirements(requirementList6);
		method7.setRequirements(requirementList7);
		
		methodList1.add(method1);
		methodList1.add(method2);
		methodList1.add(method3);
		methodList2.add(method4);
		methodList3.add(method5);
		methodList3.add(method6);
		methodList3.add(method7);
		
		class1.setMethods(methodList1);
		class2.setMethods(methodList2);
		class3.setMethods(methodList3);
		
		classList1.add(class1);
		classList1.add(class2);
		classList2.add(class3);
		pack1.setClasses(classList1);
		pack2.setClasses(classList2);
		packageList.add(pack1);
		packageList.add(pack2);
		
		return packageList.toArray();
	}
	
}