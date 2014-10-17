package br.usp.each.saeg.jaguar.core.infra;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utilies methods that can be used to search and list files within a
 * directory.
 * 
 * @author Henrique Ribeiro
 * 
 */
public class FileUtils {

	/**
	 * Search recursively for classes ending with
	 * Test.class within the current directory.
	 * 
	 * @param clazz
	 *            the current class
	 * @return list of files ending with Test.class
	 * @throws ClassNotFoundException
	 */
	public static Class<?>[] findTestClasses(Class<?> clazz) throws ClassNotFoundException {
		File testDir = findClassDir(clazz);
		return findTestClasses(testDir);
	}
	
	//TODO javadoc
	public static Class<?>[] findTestClasses(File testDir) throws ClassNotFoundException {
		List<File> testClassFiles = findFilesEndingWith(testDir, new String[] { "Test.class" });
		List<Class<?>> classes = convertToClasses(testClassFiles, testDir);
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Convert files into classes.
	 * 
	 * @param classFiles
	 *            files to be searched
	 * @param classesDir
	 *            where the files are saved
	 * @return list of classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> convertToClasses(final List<File> classFiles, final File classesDir) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : classFiles) {
			Class<?> c = Class.forName(getClassNameFromFile(classesDir, file));
			if (!Modifier.isAbstract(c.getModifiers())) {
				classes.add(c);
			}
		}

		return classes;
	}

	/**
	 * Get the file name, including package.
	 * 
	 * @param classesDir
	 *            directory where the class is saved
	 * @param file
	 *            class file
	 * @return full class name
	 */
	public static String getClassNameFromFile(final File classesDir, File file) {
		String name = file.getPath().substring(classesDir.getPath().length() + 1).replace('/', '.').replace('\\', '.');
		name = name.substring(0, name.length() - 6);
		return name;
	}

	/**
	 * Search recursively within the given directory for files ending with one
	 * of the Strings.
	 * 
	 * @param dir
	 *            the directory to be searched
	 * @param endsWith
	 *            string array with the end's name.
	 * @return list of files ending with one of the Strings.
	 */
	public static List<File> findFilesEndingWith(final File dir, String[] endsWith) {
		List<File> classFiles = new ArrayList<File>();
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				classFiles.addAll(findFilesEndingWith(file, endsWith));
			} else if (endsWith(file, endsWith)) {
				classFiles.add(file);
			}
		}
		return classFiles;
	}

	/**
	 * Check if the file name name ends with one of the Strings. It is NOT case
	 * sensitive.
	 * 
	 * @param file
	 *            file to be checked
	 * @param endsWith
	 *            string array with the end's name.
	 */
	public static Boolean endsWith(File file, String[] endsWith) {
		Boolean goodFile = false;
		for (String end : endsWith) {
			if (file.getName().toLowerCase().endsWith(end.toLowerCase())) {
				goodFile = true;
				break;
			}
		}
		return goodFile;
	}

	/**
	 * Find the class's parent dir
	 * 
	 * @param clazz
	 *            the class
	 * @return new File object witch is the parent folder
	 */
	public static File findClassDir(Class<?> clazz) {
		try {
			String path = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
			return new File(URLDecoder.decode(path, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
	
	/**
	 * Search for a file within a directory.
	 * 
	 * @param directory the directory
	 * @param fileName the file name
	 * @return the file, or null
	 */
	public static File getFile(File directory, final String fileName) {
		File classesDir = null;
		File[] filesReturned = directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().equals(fileName);
			}
		});
		if (filesReturned.length == 1){
			classesDir = filesReturned[0];
		}
		return classesDir;
	}
}
