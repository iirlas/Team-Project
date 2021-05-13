package application.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import utility.Parser;

public class ConfigParser extends Parser {

	private ArrayList<File> files = new ArrayList<File>();

	public ArrayList<File> getFiles() {
		return files;
	}

	public void file(String arguments, String line) {
		if (line != null)
			return;
		Scanner argumentScanner = new Scanner(arguments);
		while (argumentScanner.hasNext()) {
			File file = new File(this.getFile().getParent(), argumentScanner.next());
			if (file.exists() && file.canRead()) {
				getFiles().add(file);
			}			
		}
		argumentScanner.close();
	}
}
