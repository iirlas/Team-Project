package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.effect.Reflection;

public abstract class Parser {

	private static HashMap<Class<?>, Parser> instances = new HashMap<>();
	
	private File file;
	private Pattern pattern = Pattern.compile(" *@([a-zA-Z0-9_]+)");

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {
		if (!instances.containsKey(clazz)) {
			
			try {
				instances.put(clazz, (Parser) clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (T) instances.get(clazz);
	}

	protected Parser() {
		// TODO Auto-generated constructor stub
		assert false : "Can not be instantiated";
	}
	
//	public Parser(String filepath) {
//		this(new File(filepath));
//	}
//
//	public Parser(File file) {
//		this(file, );
//	}
//
//	public Parser(File file, String functionPattern) {
//		// TODO Auto-generated constructor stub
//		setFile(file);
//		pattern = functionPattern);
//	}

	public void parse() throws FileNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Scanner scanner = new Scanner(getFile());
		Method method = null;
		String arguments = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (Pattern.matches("^\s*#.*", line) || line.length() == 0) {
				continue;
			}

			Matcher matcher = getPattern().matcher(line);
			if (matcher.find()) {
				method = getClass().getMethod(matcher.group(1).toLowerCase(), String.class, String.class);
				if (matcher.end() < line.length()) {
					arguments = line.substring(matcher.end()).trim();
				} else {
					arguments = null;
				}
				method.invoke(this, arguments, null);
			} else if (method != null) {
				method.invoke(this, arguments, line);
			}
		}
		scanner.close();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setFile(String parent, String child) {
		this.file = new File(parent, child);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
}
