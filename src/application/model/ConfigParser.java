package application.model;

import java.io.File;
import java.util.ArrayList;

import utility.Parser;

public class ConfigParser extends Parser {

	private ArrayList<File> files = new ArrayList<File>();

	public ConfigParser(String filepath) {
		super(filepath);
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void file(String[] arguments, String line) {
		for (String arg : arguments) {
			File file = new File(this.getFile().getParent(), arg);
			if (file.exists() && file.canRead()) {
				getFiles().add(file);
			}
		}
	}
}
