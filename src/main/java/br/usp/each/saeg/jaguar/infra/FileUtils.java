package br.usp.each.saeg.jaguar.infra;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static Class<?>[] findClasses(Class<?> clazz) {
		File classDir = findClassDir(clazz);
		List<File> classFiles = findClasses(classDir);
		List<Class<?>> classes = convertToClasses(classFiles, classDir);
		return classes.toArray(new Class[classes.size()]);
	}

	public static List<Class<?>> convertToClasses(final List<File> classFiles,
			final File classesDir) {

		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : classFiles) {
			if (!file.getName().endsWith("Test.class")) {
				continue;
			}
			String name = file.getPath()
					.substring(classesDir.getPath().length() + 1)
					.replace('/', '.').replace('\\', '.');
			name = name.substring(0, name.length() - 6);
			Class<?> c;
			try {
				c = Class.forName(name);
			} catch (ClassNotFoundException e) {
				throw new AssertionError(e);
			}
			if (!Modifier.isAbstract(c.getModifiers())) {
				classes.add(c);
			}
		}

		return classes;
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
			String path = clazz.getProtectionDomain().getCodeSource()
					.getLocation().getFile();
			return new File(URLDecoder.decode(path, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
}
