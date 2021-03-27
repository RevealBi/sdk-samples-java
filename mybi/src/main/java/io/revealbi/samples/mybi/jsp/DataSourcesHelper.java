package io.revealbi.samples.mybi.jsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DataSourcesHelper {
	private static String fileName;
	private static String cachedJs;
	private static long cachedJsTimestamp;
	
	public static void setFileName(String fileName) {
		DataSourcesHelper.fileName = fileName;
	}
	public static String getDataSourcesScript() {
		if (fileName == null) {
			return "";
		}
		
		File jsFile = new File(fileName);
		if (!jsFile.exists() || !jsFile.canRead()) {
			return "";
		}
		
		if (cachedJsTimestamp == jsFile.lastModified()) {
			return cachedJs;
		}
		StringBuilder jsBuilder = new StringBuilder();
		try (FileReader reader = new FileReader(jsFile);
				BufferedReader inputReader = new BufferedReader(reader)) {
			String line;
			while ((line = inputReader.readLine()) != null) {
				jsBuilder.append(line);
				jsBuilder.append('\n');
			}
			cachedJs = jsBuilder.toString();
			cachedJsTimestamp = jsFile.lastModified();
			return cachedJs;
		} catch (Exception exc) {
			exc.printStackTrace();
			return "";
		}
	}
}
