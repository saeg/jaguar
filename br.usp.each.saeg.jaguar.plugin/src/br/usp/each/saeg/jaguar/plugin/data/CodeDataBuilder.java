package br.usp.each.saeg.jaguar.plugin.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jface.text.Position;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.source.parser.ParsingResult;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeUtils;
import br.usp.each.saeg.jaguar.codeforest.model.DuaRequirement;
import br.usp.each.saeg.jaguar.codeforest.model.Method;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement;
import br.usp.each.saeg.jaguar.codeforest.model.Requirement.Type;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class CodeDataBuilder {

    public static CodeDataBuilderResult from(ParsingResult result, char[] originalSourceCode) {
        IScanner originalScanner = SourceCodeUtils.createClassMethodScannerOf(originalSourceCode);
        boolean firstTime = true;

        List<Map<String, Object>> props = new ArrayList<Map<String,Object>>();

        Map<String, ClassData> nameData = new HashMap<String, ClassData>();
        for (Class clz : result.getClasses()) {
            ClassData classData = new ClassData();
            classData.setResource(result.getFile());

            classData.setScore(clz.getSuspiciousValue().floatValue());
            classData.setValue(clz.getContent());
            classData.setName(clz.getName());
            classData.setAnonymous(clz.getName().matches(".*(\\$[0-9]+)$"));

            classData.setOpenPosition(getPosition(originalScanner, clz.getStart(), clz.getEnd()));
            props.add(markerProps(classData.getScore(), classData.getOpenPosition(), clz.getStart(), clz.getEnd()));

            classData.setClosePosition(getPosition(originalScanner, clz.getClose(), clz.getClose()));
            props.add(markerProps(classData.getScore(), classData.getClosePosition(), clz.getClose()));

            classData.setStartLine(clz.getStart());
            classData.setEndLine(clz.getEnd());
            classData.setCloseLine(clz.getClose());
            classData.setLogLine(clz.getName());

            if (result.getPackage().getLocation() > 0 && firstTime) {
                firstTime = false;
                classData.setPackagePosition(getPosition(originalScanner, result.getPackage().getStart(), result.getPackage().getEnd()));
                classData.setPackageScore(result.getPackage().getSuspiciousValue().floatValue());
            }

            if (Configuration.CONSIDER_LINE_OCURRENCES) {
            	classData.setOcurrences(clz.getNumber());
            }

            for (Method method : clz.getMethods()) {
                MethodData methodData = new MethodData(classData);
                methodData.setResource(result.getFile());
                methodData.setScore(method.getSuspiciousValue().floatValue());
                methodData.setValue(method.getContent());
                methodData.setScriptPosition(method.getMcpPosition());
                methodData.setScriptScore(method.getMcpSuspiciousValue().floatValue());
                methodData.setName(method.getName());

                methodData.setPosition(getPosition(originalScanner, method.getStart(), method.getEnd()));
                props.add(markerProps(methodData.getScore(), methodData.getPosition(), method.getStart(), method.getEnd()));
                methodData.setStartLine(method.getStart());
                methodData.setEndLine(method.getEnd());
                methodData.setLogLine(classData.getName() + "." + methodData.getName());

                if (method.getClose() >= 0) {
                	methodData.setClosePosition(getPosition(originalScanner, method.getClose(), method.getClose()));
                    props.add(markerProps(methodData.getScore(), methodData.getClosePosition(), method.getClose()));
                    methodData.setCloseLine(method.getClose());
                }

                if (Configuration.CONSIDER_LINE_OCURRENCES) {
                	methodData.setOcurrences(method.getNumber());
                }
                classData.getMethodData().add(methodData);
                for (Requirement req : method.getRequirements()) {
                	if (req.getType() == Type.DUA) {
                		DuaRequirement dua = (DuaRequirement) req;
                		DuaRequirementData reqData = new DuaRequirementData();
                		reqData.setResource(result.getFile());
                    	reqData.setLine(req.getLocation());
                    	reqData.setScore(req.getSuspiciousValue().floatValue());
                    	reqData.setPosition(getPosition(originalScanner, req.getStart(), req.getEnd()));
                    	reqData.setUsePosition(getPosition(originalScanner, dua.getUse(), dua.getUse()));
                        props.add(markerProps(reqData.getScore(), reqData.getPosition(), req.getStart()));
                        reqData.setValue(req.getContent());
                        reqData.setLogLine(classData.getName() + "." + methodData.getName() + "." + req.getLocation());
                        reqData.setDef(dua.getDef());
                		reqData.setTarget(dua.getTarget());
                		reqData.setUse(dua.getUse());
                		reqData.setVar(dua.getVar());
                		methodData.getRequirementData().add(reqData);
                	}
                	else {
                		LineRequirementData reqData = new LineRequirementData();
                		reqData.setResource(result.getFile());
                    	reqData.setLine(req.getLocation());
                    	reqData.setScore(req.getSuspiciousValue().floatValue());
                    	reqData.setPosition(getPosition(originalScanner, req.getStart(), req.getEnd()));
                        props.add(markerProps(reqData.getScore(), reqData.getPosition(), req.getStart()));
                        reqData.setValue(req.getContent());
                        reqData.setLogLine(classData.getName() + "." + methodData.getName() + "." + req.getLocation());
                        methodData.getRequirementData().add(reqData);
                        if(reqData.getLine() > 0)
                        System.out.println("reqData.getLine() : "+reqData.getLine());
                       // if(req.getLocation() > 0)
                            System.out.println("req.getLocation() : "+req.getLocation() + " req.getType() : "+req.getType());
                	}
                }
                Collections.sort(methodData.getRequirementData());
            }
            Collections.sort(classData.getMethodData());
            nameData.put(clz.getName(), classData);
        }

        CodeDataBuilderResult buildResult = new CodeDataBuilderResult(result.getFile());
        buildResult.getClassData().addAll(nameData.values());
        buildResult.getMarkerProperties().addAll(props);

        return buildResult;
    }

    private static Map<String, Object> markerProps(float score, Position position, int... locs) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("score", score);
        result.put("loc", locs);
        if (position != null) {
            result.put(IMarker.CHAR_START, position.getOffset());
            result.put(IMarker.CHAR_END, position.getOffset() + position.getLength());
        } else {
            result.put(IMarker.CHAR_START, 0);
            result.put(IMarker.CHAR_END, 0);
        }
        return result;
    }

    private static Position getPosition(IScanner originalScanner, int start, int end) {
        if (start <= 0 || end <= 0) return null;
  
        try {
            if (originalScanner.getLineEnd(end) == 0) {
                return new Position(originalScanner.getLineStart(start), 1);
            } 
            else {
                return new Position(originalScanner.getLineStart(start), originalScanner.getLineEnd(end) - originalScanner.getLineStart(start));
            }
        } 
        catch (Exception e) {
           JaguarPlugin.log(e);
           return null;
        }
    }
}
