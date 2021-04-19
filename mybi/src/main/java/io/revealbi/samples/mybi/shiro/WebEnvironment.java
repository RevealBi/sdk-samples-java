package io.revealbi.samples.mybi.shiro;

import java.util.logging.Logger;

import org.apache.shiro.web.env.IniWebEnvironment;

import io.revealbi.samples.mybi.reveal.WebAppListener;

public class WebEnvironment extends IniWebEnvironment {
	private static Logger log = Logger.getLogger(WebEnvironment.class.getName());
	
	@Override
	protected String[] getDefaultConfigLocations() {
		String iniUrl = "file:" + WebAppListener.getPathForConfigurationFile(getServletContext(), "shiro.ini");
		log.info("Loading Shiro INI file from: " + iniUrl);
		return new String[] {
			iniUrl
		};
	}
}
