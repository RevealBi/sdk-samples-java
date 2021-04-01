package io.revealbi.samples.mybi.reveal;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.RevealEngineInitializer;
import com.infragistics.reveal.engine.init.InitializeParameterBuilder;

import io.revealbi.samples.mybi.dashboards.CredentialRepositoryFactory;
import io.revealbi.samples.mybi.dashboards.DashboardRepositoryFactory;
import io.revealbi.samples.mybi.dashboards.FileSystemCredentialRepository;
import io.revealbi.samples.mybi.dashboards.FileSystemDashboardRepository;
import io.revealbi.samples.mybi.jsp.DataSourcesHelper;

@WebListener
public class WebAppListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(WebAppListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		String rootDir = getRootDir(evt.getServletContext());
		
		log.info("Initializing mybi with path: " + rootDir);
		
		DataSourcesHelper.setFileName(new File(rootDir, "datasources.js").getAbsolutePath());
		DashboardRepositoryFactory.setInstance(new FileSystemDashboardRepository(getDashboardsRootDir(rootDir)));
		CredentialRepositoryFactory.setInstance(new FileSystemCredentialRepository(getCredentialsFilePath(rootDir)));

		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(CredentialRepositoryFactory.getInstance()).
				setUserContextProvider(new RevealUserContextProvider()).
				setDashboardProvider(DashboardRepositoryFactory.getInstance()).
				setLicense(getLicenseKey(rootDir)).build()
		);
		evt.getServletContext().setAttribute("revealSdkVersion", RevealEngineInitializer.getRevealSdkVersion());
	}

	private static String getRootDir(ServletContext ctx) {
		String defaultPath = ctx.getRealPath("/WEB-INF/mybi");
		if (defaultPath == null || !(new File(defaultPath).exists())) {
			defaultPath = System.getProperty("user.home") + "/mybi";
		}
		return System.getProperty("mybi.root", defaultPath);
	}
	
	private static String getDashboardsRootDir(String rootDir) {
		return new File(rootDir, "dashboards").getAbsolutePath();
	}
	private static String getCredentialsFilePath(String rootDir) {
		return new File(rootDir, "credentials.json").getAbsolutePath();
	}
	
	private static String getLicenseKey(String rootDir) {
		File file = new File(rootDir, "license.dat");
		if (file.exists() && file.canRead()) {
			return getFirstLineTrimmed(file);
		} else {
			return null;
		}
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
