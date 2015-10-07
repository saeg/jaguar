package br.usp.each.saeg.jaguar.plugin.source.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;


/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class SourceCodeUtils {

    public static char[] readAndTrim(IResource file) {
        try {
            List<String> lines = FileUtils.readLines(new File(file.getLocationURI()));
            List<String> trimmedLines = new ArrayList<String>(lines.size());
            for (String line : lines) {
                trimmedLines.add(line.replaceAll("\\s+$", ""));
            }
            return StringUtils.join(trimmedLines, System.getProperty("line.separator")).toCharArray();
        } catch (Exception e) {
            JaguarPlugin.log(e);
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
    }

    public static char[] read(IResource file) {
        try {
            return FileUtils.readFileToString(new File(file.getLocationURI())).toCharArray();

        } catch (Exception e) {
            JaguarPlugin.log(e);
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
    }

    public static IScanner createClassMethodScannerOf(char[] src) {
        IScanner result = ToolFactory.createScanner(false, true, false, true);
        result.setSource(src);
        return consumeScanner(result);
    }

    public static IScanner createRequirementScannerOf(char[] src) {
        IScanner result = ToolFactory.createScanner(true, true, false, true);
        result.setSource(src);
        return consumeScanner(result);
    }

    public static String asString(IFile file) {
        return file.getLocationURI().toString();
    }

    public static String asString(IResource file) {
        return file.getLocationURI().toString();
    }

    private static IScanner consumeScanner(IScanner arg) {
        try {
            while (arg.getNextToken() != ITerminalSymbols.TokenNameEOF) {
                //tem que correr o arquivo pro scanner funcionar
            }
        } catch (Exception e) {
            JaguarPlugin.log(e);
        }
        return arg;
    }
}
