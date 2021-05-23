package io.revealbi.samples.browser.reveal;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;

import io.revealbi.samples.browser.rest.CorsFilter;
import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.CredentialRepositoryFactory;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.fs.FileSystemExtFactory;

@WebListener
public class WebAppListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(WebAppListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		String rootDir = getRootDir(evt.getServletContext());
		
		log.info("Initializing samples-browser with path: " + rootDir);
		
		FileSystemExtFactory.registerAllServices(rootDir, false);
		DashboardRepositoryFactory.setInstance(new SamplesDashboardRepository(new File(rootDir, "dashboards").getAbsolutePath(), false));
		TagsService.setInstance(new TagsService(new File(rootDir, "tags.json").getAbsolutePath()));		
		AuthorizationProviderFactory.setInstance(new ShiroAuthorizationProvider(true));

		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(CredentialRepositoryFactory.getInstance()).
				setUserContextProvider(new RevealUserContextProvider()).
				setDashboardProvider(DashboardRepositoryFactory.getInstance()).
				setDashboardAuthorizationProvider(AuthorizationProviderFactory.getInstance()).
				setLicense(getLicenseKey(rootDir)).build()
		);
		evt.getServletContext().setAttribute("revealSdkVersion", RevealEngineInitializer.getRevealSdkVersion());
		
		RevealEngineInitializer.registerResource(CorsFilter.class);
	}

	private static String getRootDir(ServletContext ctx) {
		String defaultPath = ctx.getRealPath("/WEB-INF/samples-browser");
		if (defaultPath == null || !(new File(defaultPath).exists())) {
			defaultPath = System.getProperty("user.home") + "/samples-browser";
		}
		return System.getProperty("samples.browser.root", defaultPath);
	}
		
	private static String getLicenseKey(String rootDir) {
		File file = new File(rootDir, "license.dat");
		if (file.exists() && file.canRead()) {
			return getFirstLineTrimmed(file);
		} else {
			return null;
		}
	}
	
	public static String getPathForConfigurationFile(ServletContext context, String relativePath) {
		String rootDir = getRootDir(context);
		return rootDir + "/" + relativePath;
	}
	
	private static String getFirstLineTrimmed(File file) {
		try (FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader)) {
			
			String line = reader.readLine();
			if (line != null) {
				return line.trim();
			}
		} catch (Exception exc) {
			log.log(Level.SEVERE, "Failed to read license: " + exc.getMessage(), exc);
		}
		return null;
	}
}
