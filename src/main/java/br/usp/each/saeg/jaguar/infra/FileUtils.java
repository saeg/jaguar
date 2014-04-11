package br.usp.each.saeg.jaguar.infra;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static Class<?>[] findTestClasses(Class<?> clazz) throws ClassNotFoundException {
		File classDir = findClassDir(clazz);
		List<File> classFiles = findClasses(classDir);
		List<Class<?>> classes = convertToClasses(classFiles, classDir, "Test.class");
		return classes.toArray(new Class[classes.size()]);
	}

	public static List<Class<?>> convertToClasses(final List<File> classFiles, final File classesDir, String... endsWith)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : classFiles) {
			if (isGoodFile(file, endsWith)) {
				Class<?> c = Class.forName(getClassNameFromFile(classesDir, file));
				if (!Modifier.isAbstract(c.getModifiers())) {
					classes.add(c);
				}
			}
		}

		return classes;
	}

	private static String getClassNameFromFile(final File classesDir, File file) {
		String name = file.getPath().substring(classesDir.getPath().length() + 1).replace('/', '.').replace('\\', '.');
		name = name.substring(0, name.length() - 6);
		return name;
	}

	private static Boolean isGoodFile(File file, String... endsWith) {
		Boolean goodFile = false;

		if (endsWith.length == 0) {
			endsWith = new String[] { ".class" };
		}

		for (String end : endsWith) {
			if (file.getName().endsWith(end)) {
				goodFile = true;
				break;
			}
		}
		return goodFile;
	}

	public static List<File> findClasses(final File dir) {
		List<File> classFiles = new ArrayList<File>();
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				classFiles.addAll(findClasses(file));
			} else if (file.getName().toLowerCase().endsWith(".class")) {
				classFiles.add(file);
			}
		}
		return classFiles;
	}

	public static File findClassDir(Class<?> clazz) {
		try {
			String path = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
			return new File(URLDecoder.decode(path, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
}
