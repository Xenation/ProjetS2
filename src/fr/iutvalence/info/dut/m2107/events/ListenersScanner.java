package fr.iutvalence.info.dut.m2107.events;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility used to scan all classes and detect which ones are listeners and which ones are events.
 * @author Xenation
 *
 */
public class ListenersScanner {
	
	/**
	 * The package name separator
	 */
	private static final char PKG_SEPARATOR = '.';
	/**
	 * The directory separator
	 */
	private static final char DIR_SEPARATOR = '/';
	/**
	 * The suffix of a class file
	 */
	private static final String CLASS_FILE_SUFFIX = ".class";
	/**
	 * The string to display in case of an invalid package name
	 */
	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
	
	/**
	 * The list of classes that are event listeners
	 */
	public static final List<Class<?>> listenersClasses = new ArrayList<Class<?>>();
	/**
	 * The list of classes that are events
	 */
	public static final List<Class<?>> eventClasses = new ArrayList<Class<?>>();
	
	/**
	 * Scans all the packages to find listener and event classes
	 */
	public static void init() {
		List<Class<?>> classes = find("fr.iutvalence.info.dut.m2107.entities");
		classes.addAll(find("fr.iutvalence.info.dut.m2107.events"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.fontMeshCreator"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.gui"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.listeners"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.models"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.render"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.saving"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.shaders"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.storage"));
		classes.addAll(find("fr.iutvalence.info.dut.m2107.tiles"));
		for (Class<?> cla : classes) {
			try {
				cla.getMethod("init");
				if (cla.getName().contains("fr.iutvalence.info.dut.m2107.events") && cla.getSimpleName().contains("Event") && !Modifier.isAbstract(cla.getModifiers()) && !cla.getSimpleName().contains("Manager")) {
					eventClasses.add(cla);
					continue;
				}
			} catch (NoSuchMethodException e) {
				// Ignored if init method isn't here just don't add it to eventClasses
			} catch (SecurityException e) {
				// Ignored
			}
			for (Class<?> interf : cla.getInterfaces()) {
				if (interf.getName().contains("fr.iutvalence.info.dut.m2107.events.Listener")) {
					listenersClasses.add(cla);
				}
			}
		}
	}
	
	/**
	 * Gets the classes that can handle the specified type of event
	 * @param eventClass the class of the event to be handled
	 * @return A map that links each handling class to its method that handles the event
	 */
	public static Map<Class<?>, Method> getHandlers(Class<?> eventClass) {
//		System.out.println("REQUESTING HANDLER CLASSES FOR: "+eventClass.getSimpleName());
		Map<Class<?>, Method> handlers = new HashMap<Class<?>, Method>();
		for (Class<?> cla : listenersClasses) {
			try {
				Method meth = cla.getMethod("get"+eventClass.getSimpleName().substring(0, eventClass.getSimpleName().length()-5), eventClass);
				handlers.put(cla, meth);
//				System.out.println("\tADDED: "+cla.getName()+"\n\t\tMETHOD: "+meth.getName());
			} catch (NoSuchMethodException e) {
				// Ignored
			} catch (SecurityException e) {
				// Ignored
			}
		}
		return handlers;
	}
	
	/**
	 * Returns the method that handles the given event in the given listener. returns null if the listener can't handle the event
	 * @param listener the listener
	 * @param eventClass the class of the event that needs to be handled
	 * @return the method that handles the given event in the given listener. returns null if the listener can't handle the event
	 */
	public static Method getPreciseHandler(Listener listener, Class<?> eventClass) {
		try {
			return listener.getClass().getMethod("on"+eventClass.getSimpleName().substring(0, eventClass.getSimpleName().length()-5), eventClass);
		} catch (NoSuchMethodException e) {
			// Ignored
		} catch (SecurityException e) {
			// Ignored
		}
		return null;
	}
	
	/**
	 * Returns a list of classes that are contained by the given package
	 * @param scannedPackage the full name of the package
	 * @return a list of classes that are contained by the package
	 */
	public static List<Class<?>> find(String scannedPackage) {
		String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
		}
		File scannedDir = new File(scannedUrl.getFile());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : scannedDir.listFiles()) {
			classes.addAll(find(file, scannedPackage));
		}
		return classes;
	}
	
	private static List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + PKG_SEPARATOR + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource));
			}
		} else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
			int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}
	
}
