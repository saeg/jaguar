package br.usp.each.saeg.jaguar.plugin.source.parser;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.listeners.LogListener;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeUtils;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeParser;
import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.HierarchicalFaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.LineRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Package;

public class SourceCodeParser extends ASTVisitor{
	
	private final static Logger logger = LoggerFactory.getLogger(SourceCodeParser.class.getName());
	
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final HierarchicalFaultClassification input;
    private final CompilationUnit cu;
    private final IScanner classMethodScanner;
    private final IScanner requirementScanner;


    private String packageName = "";
    private Deque<String> classes = new ArrayDeque<String>();
    private String baseClassName = "";
    private Map<String, Integer> classNameCounter = new HashMap<String, Integer>();
    private Map<String, Integer> classNameBlockCounter = new HashMap<String, Integer>();
    private Map<String, Integer> classNameStaticBlockCounter = new HashMap<String, Integer>();

    private final ParsingResult parsingResult;
    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");
    private IProject project;
    private ProjectState state;

    public SourceCodeParser(CompilationUnit cu, char[] src, HierarchicalFaultClassification input, ParsingResult result, IProject project) {
        this.input = input;
        this.cu = cu;
        this.classMethodScanner = SourceCodeUtils.createClassMethodScannerOf(src);
        this.requirementScanner = SourceCodeUtils.createRequirementScannerOf(src);
        this.parsingResult = result;
        this.project = project;
        state = ProjectPersistence.getStateOf(project);
    }
    
    @Override
    public boolean visit(PackageDeclaration node) {
        parsingResult.packageDeclared();
        packageName = node.getName().getFullyQualifiedName();
        findPackageIfNeeded();

        baseClassName = SourceCodeUtils.asString(parsingResult.getFile());
        baseClassName = baseClassName.replace(".java", "");
        baseClassName = baseClassName.replaceAll("/", ".");
        baseClassName = baseClassName.substring(baseClassName.lastIndexOf(packageName));

        addClass(baseClassName);
        int loc = getLineNumberOf(node.getStartPosition());
        parsingResult.getPackage().setLocation(loc);
        parsingResult.getPackage().setStart(loc);
        parsingResult.getPackage().setEnd(loc);
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        findPackageIfNeeded();

        String currentClassName = node.getName().getFullyQualifiedName();
        if (parsingResult.isDeclaredPackage()) {
            currentClassName = packageName + "." + node.getName().getFullyQualifiedName();
        }
        if (!currentClassName.equals(baseClassName)) {
            int modifiers = node.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                addClass(getCurrentClass() + "$" + node.getName().getFullyQualifiedName());
            } else {
                addClass((parsingResult.isDeclaredPackage() ? (packageName + ".") : "") + node.getName().getFullyQualifiedName());
            }
        }

        Class clazz = findParsedClass();
        if (node.isInterface()) {
            clazz.setJavaInterface(true);
        }
        addClassToResult(node, clazz);
        return true;
    }

    @Override
    public void endVisit(TypeDeclaration node) {
        removeCurrentClass();
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        findPackageIfNeeded();

        if (!(packageName + "." + node.getName().getFullyQualifiedName()).equals(baseClassName)) {
            String currentClassName = getCurrentClass() + "$" + node.getName();
            addClass(currentClassName);
        }

        Class clazz = findParsedClass();
        addClassToResult(node, clazz);
        return true;
    }

    @Override
    public void endVisit(EnumDeclaration node) {
        removeCurrentClass();
    }

    public boolean visit(AnonymousClassDeclaration node) {
        findPackageIfNeeded();
        String currentClassName = getCurrentClass();
        if (!classNameCounter.containsKey(currentClassName)) {
            classNameCounter.put(currentClassName, 1);
        } else {
            classNameCounter.put(currentClassName, classNameCounter.get(currentClassName) + 1);
        }
        String anonClassName = currentClassName + "$" + classNameCounter.get(currentClassName);
        addClass(anonClassName);

        Class clazz = findParsedClass();
        addClassToResult(node, clazz);
        return true;
    }

    @Override
    public void endVisit(AnonymousClassDeclaration node) {
        removeCurrentClass();
    }
    
    private void addClassToResult(ASTNode node, Class clazz) {
        clazz.setContent(node.toString());
        int end = findFirstPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameLBRACE);
        int start = findLastPositionOf(node.getStartPosition(), end - node.getStartPosition(), ITerminalSymbols.TokenNameIdentifier);
        int close = findLastPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameRBRACE);
        parsingResult.addClass(getCurrentClass(), getLineNumberOf(start), getLineNumberOf(end), getLineNumberOf(close), clazz);
    }

    @Override
    public boolean visit(Initializer node) {
        findPackageIfNeeded();

        String methodName = "initializer";
        String currentClassName = getCurrentClass();

        int modifiers = node.getModifiers();
        if (Modifier.isStatic(modifiers)) {
            methodName = "static " + methodName;
            if (!classNameStaticBlockCounter.containsKey(currentClassName)) {
                classNameStaticBlockCounter.put(currentClassName, 1);
            } else {
                classNameStaticBlockCounter.put(currentClassName, classNameStaticBlockCounter.get(currentClassName) + 1);
            }
            methodName = methodName + "$" + classNameStaticBlockCounter.get(currentClassName);

        } else {

            if (!classNameBlockCounter.containsKey(currentClassName)) {
                classNameBlockCounter.put(currentClassName, 1);
            } else {
                classNameBlockCounter.put(currentClassName, classNameBlockCounter.get(currentClassName) + 1);
            }
            methodName = methodName + "$" + classNameBlockCounter.get(currentClassName);
        }

        Class foundClass = parsingResult.getClass(getCurrentClass());
        Method method = new Method();
        foundClass.getMethods().add(method);

        int startEnd = findFirstPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameLBRACE);
        int close = findLastPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameRBRACE);

        method.setStart(getLineNumberOf(startEnd));
        method.setEnd(getLineNumberOf(startEnd));
        method.setClose(getLineNumberOf(close));
        method.setName(methodName);
        method.setSuspiciousValue(MINUS_ONE.doubleValue());
        method.setContent(node.toString());
        method.setMcpPosition(0);
        method.setMcpSuspiciousValue(0D);

        if (node.getBody() == null || node.getBody().statements() == null || node.getBody().statements().isEmpty()) {
            return true;
        }

        for (Object o : node.getBody().statements()) {
            Statement stmt = (Statement) o;
            Requirement requirement;
            if(state.getRequirementType() == Type.DUA){
            	requirement = new DuaRequirement();
            }else{
            	requirement = new LineRequirement();
            }
            requirement.setSuspiciousValue(MINUS_ONE.doubleValue());
            requirement.setStart(getLineNumberOf(stmt.getStartPosition()));
            requirement.setEnd(getLineNumberOf(stmt.getStartPosition() + stmt.getLength()));
            requirement.setContent(stmt.toString());
            method.getRequirements().add(requirement);
        }
        return true;
    }
    
    @Override
    public boolean visit(MethodDeclaration node) {
        String methodName = node.getName().getFullyQualifiedName();
        if (node.parameters() != null && node.parameters() != null) {
            List<String> parameters = new ArrayList<String>();
            for (Object o : node.parameters()) {
                SingleVariableDeclaration decl = (SingleVariableDeclaration) o;
                parameters.add(decl.getType().toString());
            }
            methodName = methodName + "(" + StringUtils.join(parameters, ",") + ")";
        }

        Class clazz = findParsedClass();
        Method method = clazz.byName(methodName);
        if (method == null) {
            logger.trace("method [" + methodName + "], class [" + getCurrentClass() + "] not found in jaguar.xml");
           
            method = new Method();
            method.setName(methodName);
            method.setSuspiciousValue(MINUS_ONE.doubleValue());
            clazz.addMethod(method);
        }

        Class foundClass = parsingResult.getClass(getCurrentClass());

        int modifiers = node.getModifiers();
        int end = node.getStartPosition();
        int close = -1;
        if (foundClass.isJavaInterface() || Modifier.isAbstract(modifiers)) {
            end = node.getStartPosition() + node.getLength();
        } else {
            end = findFirstPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameLBRACE);
            close = findLastPositionOf(node.getStartPosition(), node.getLength(), ITerminalSymbols.TokenNameRBRACE);
        }
        int start = findLastPositionOf(node.getStartPosition(), end - node.getStartPosition(), ITerminalSymbols.TokenNameLPAREN);

        Method foundMethod = new Method();
        foundClass.getMethods().add(foundMethod);
        foundMethod.setStart(getLineNumberOf(start));
        foundMethod.setEnd(getLineNumberOf(end));
        if (close < 0) {
            foundMethod.setClose(close);
        } else {
            foundMethod.setClose(getLineNumberOf(close));
        }
        foundMethod.setName(methodName);
        foundMethod.setNumber(method.getNumber());
        foundMethod.setSuspiciousValue(method.getSuspiciousValue());
        foundMethod.setMcpPosition(method.getMcpPosition());
        foundMethod.setMcpSuspiciousValue(method.getMcpSuspiciousValue());
        foundMethod.setContent(node.toString());

        if (node.getBody() == null || node.getBody().statements() == null || node.getBody().statements().isEmpty()) {
            return true;
        }

        int delta = (int)method.getLocation() - (classMethodScanner.getLineNumber(node.getStartPosition()));
        for (Object raw : node.getBody().statements()) {
            Statement superStmt = (Statement) raw;
            int stmtStart = classMethodScanner.getLineStart(cu.getLineNumber(superStmt.getStartPosition()));
            int stmtEnd = classMethodScanner.getLineEnd(cu.getLineNumber(superStmt.getStartPosition() + superStmt.getLength()));
            List<String> splitted = readIt(stmtStart, stmtEnd - stmtStart);
            
            int relativeLoc = classMethodScanner.getLineNumber(superStmt.getStartPosition()) - classMethodScanner.getLineNumber(node.getStartPosition());
            int absoluteLoc = classMethodScanner.getLineNumber(superStmt.getStartPosition());

            for (String line : splitted) {
                int relativeLineNumber = relativeLoc++;
                int absoluteLineNumber = absoluteLoc++;
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                
                Requirement requirement = method.byAbsoluteLoc(absoluteLineNumber);
                
                if (requirement == null) {
                    JaguarPlugin.warn("line of code [" + relativeLoc + "], method [" + methodName + "], class [" + getCurrentClass() + "] not found in jaguar.xml");
                    requirement = method.byRelativeLoc(relativeLineNumber + delta);

                    if (requirement == null) {
                    	if(state.getRequirementType() == Type.LINE){
                    		requirement = new LineRequirement();
                    	}
                    	else{
	                       	requirement= new DuaRequirement();
	                    }
	                    requirement.setSuspiciousValue(MINUS_ONE.doubleValue());
	                    method.addRequirements(requirement);
                    }
                }
                else  System.out.println("1-"+methodName + ", req " + requirement.getName() + ", score" + requirement.getSuspiciousValue() + " location " + requirement.getLocation() + " type : "+requirement.getType());
                System.out.println("2-"+methodName + ", req " + requirement.getName() + ", score" + requirement.getSuspiciousValue() + " location " + requirement.getLocation() + " type : "+requirement.getType());
                
                //FIXME obter a de maior score
                Requirement foundRequirement;
                if(state.getRequirementType() == Type.DUA){
                	foundRequirement= new DuaRequirement();
                	((DuaRequirement)foundRequirement).setVar(((DuaRequirement)requirement).getVar());
                	((DuaRequirement)foundRequirement).setDef(((DuaRequirement)requirement).getDef());
                	((DuaRequirement)foundRequirement).setUse(((DuaRequirement)requirement).getUse());
                	((DuaRequirement)foundRequirement).setTarget(((DuaRequirement)requirement).getTarget());
                }else{
                	foundRequirement= new LineRequirement();
                }
                foundRequirement.setSuspiciousValue(requirement.getSuspiciousValue());
                foundRequirement.setStart(relativeLineNumber + classMethodScanner.getLineNumber(node.getStartPosition()));
                foundRequirement.setEnd(relativeLineNumber + classMethodScanner.getLineNumber(node.getStartPosition()));
                foundRequirement.setContent(line);
                foundRequirement.setLocation(requirement.getLocation());
                foundMethod.getRequirements().add(foundRequirement);
            }
        }
        return true;
    }

    private List<String> readIt(int start, int length) {
        StringBuilder result = new StringBuilder();
        try {
            requirementScanner.resetTo(start, start + length);
            int token;
            while ((token = requirementScanner.getNextToken()) != ITerminalSymbols.TokenNameEOF) {
                String str = new String(requirementScanner.getCurrentTokenSource());//FIXME bug aqui
                if (token == ITerminalSymbols.TokenNameCOMMENT_BLOCK || token == ITerminalSymbols.TokenNameCOMMENT_JAVADOC || token == ITerminalSymbols.TokenNameCOMMENT_LINE) {
                    for (int i = 0; i < StringUtils.countMatches(str, LINE_SEPARATOR); i++) {
                        result.append(LINE_SEPARATOR);
                    }
                } else {
                    result.append(requirementScanner.getCurrentTokenSource());
                }
            }
        } catch (Exception ignored) {
            JaguarPlugin.log("erro ao ler o arquivo [" + parsingResult.getURI() + "]", ignored);

        } finally {
        	requirementScanner.resetTo(cu.getStartPosition(), cu.getStartPosition() + cu.getLength());
        }
        return Arrays.asList(result.toString().split(LINE_SEPARATOR));
    }


    private int findFirstPositionOf(int start, int length, int symbol) {
        try {
            classMethodScanner.resetTo(start, start + length);
            int token;
            while ((token = classMethodScanner.getNextToken()) != ITerminalSymbols.TokenNameEOF) {
                if (token == symbol) {
                    break;
                }
            }
            return classMethodScanner.getCurrentTokenEndPosition();
        } catch (InvalidInputException ignored) {

        } finally {
            classMethodScanner.resetTo(cu.getStartPosition(), cu.getStartPosition() + cu.getLength());
        }
        return -1;
    }

    private int findLastPositionOf(int start, int length, int symbol) {
        int identifierPos = start;
        try {
            classMethodScanner.resetTo(start, start + length);
            int token;
            while ((token = classMethodScanner.getNextToken()) != ITerminalSymbols.TokenNameEOF) {
                if (token == symbol) {
                    identifierPos = classMethodScanner.getCurrentTokenStartPosition();
                }
            }
        } catch (InvalidInputException ignored) {

        } finally {
            classMethodScanner.resetTo(cu.getStartPosition(), cu.getStartPosition() + cu.getLength());
        }
        return identifierPos;
    }

    private void removeCurrentClass() {
        classes.pollLast();
    }

    private void addClass(String name) {
        classes.offer(name);
    }

    private String getCurrentClass() {
        return classes.peekLast();
    }

    private Class findParsedClass() {
    	Class clazz = parsingResult.getPackage().byName(getCurrentClass());
        if (clazz == null) {
            logger.trace("class [" + getCurrentClass() + "] not found in jaguar.xml");
            Class fake = new Class();
            fake.setName(getCurrentClass());
            fake.setSuspiciousValue(MINUS_ONE.doubleValue());
            parsingResult.getPackage().addClass(fake);
            clazz = fake;
        }
        return clazz;
    }

    private void findPackageIfNeeded() {
        if (parsingResult.isPackageNull()) {
            parsingResult.setPackage(input.byName(packageName));
        }
        if (parsingResult.isPackageNull()) {
            logger.trace("package [" + packageName + "] not found in jaguar.xml");
            Package fake = new Package();
            fake.setName(packageName);
            fake.setSuspiciousValue(MINUS_ONE.doubleValue());
            parsingResult.setPackage(fake);
        }
        if (!parsingResult.isDeclaredPackage()) {
            parsingResult.getPackage().setLocation(-1);
        }
    }

    private int getLineNumberOf(int position) {
        return classMethodScanner.getLineNumber(position);
    }
   
    
}
