package com.pany.analytics.upmedia.reveal;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.RevealEngineInitializer;
import com.infragistics.reveal.engine.init.RevealEngineInitializer.InitializeParameter;

@WebListener
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		//nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		RevealEngineInitializer.initialize(new InitializeParameter().
				withAuthProvider(new UpmediaAuthenticationProvider()).
				withUserContextProvider(new UpmediaUserContextProvider()).
				withDashboardProvider(new UpmediaDashboardProvider())
		);

		RevealEngineInitializer.registerResource(CorsFilter.class);
	}

}
