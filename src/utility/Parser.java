package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Parser {

	private File file;
	private Pattern pattern;

	public Parser(String filepath) {
		this(new File(filepath));
	}

	public Parser(File file) {
		this(file, " *@([a-zA-Z0-9_]+)");
	}

	public Parser(File file, String functionPattern) {
		// TODO Auto-generated constructor stub
		setFile(file);
		pattern = Pattern.compile(functionPattern);
	}

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

			Matcher matcher = pattern.matcher(line);
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
}
