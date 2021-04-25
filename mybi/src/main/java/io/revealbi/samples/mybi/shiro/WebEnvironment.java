package io.revealbi.samples.mybi.shiro;

import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.shiro.web.env.IniWebEnvironment;

import io.revealbi.samples.mybi.reveal.WebAppListener;

public class WebEnvironment extends IniWebEnvironment {
	private static Logger log = Logger.getLogger(WebEnvironment.class.getName());
	
	@Override
	protected String[] getDefaultConfigLocations() {
		String iniUrl = getIniUrl(getServletContext());
		log.info("Loading Shiro INI file from: " + iniUrl);
		return new String[] {
			iniUrl
		};
	}
	
	@Override
	public void init() {
		IniRealm.setIniUrl(getIniUrl(getServletContext()));
		super.init();
	}
	
	public static String getIniUrl(ServletContext ctx) {
		return "file:" + WebAppListener.getPathForConfigurationFile(ctx, "shiro.ini");
	}
}
