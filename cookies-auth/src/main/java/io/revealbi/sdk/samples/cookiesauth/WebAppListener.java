package io.revealbi.sdk.samples.cookiesauth;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;

@WebListener
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		//nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(new SampleAuthenticationProvider()).
				setUserContextProvider(new SampleUserContextProvider()).
				setDashboardProvider(new SampleDashboardProvider()).
				setLicense("LICENSE_KEY_HERE")
			.build());
		
		ctx.getServletContext().setAttribute("revealSdkVersion", RevealEngineInitializer.getRevealSdkVersion());
	}

}
