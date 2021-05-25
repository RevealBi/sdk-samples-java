package com.pany.analytics.upmedia.reveal;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;

import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.auth.simple.AllowAllReadAuthorizationProvider;

@WebListener
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		//nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		UpmediaDashboardProvider dashboardProvider = new UpmediaDashboardProvider();
		DashboardRepositoryFactory.setInstance(dashboardProvider);
		AuthorizationProviderFactory.setInstance(new AllowAllReadAuthorizationProvider());
		
		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(new UpmediaAuthenticationProvider()).
				setUserContextProvider(new UpmediaUserContextProvider()).
				setDashboardProvider(dashboardProvider).
			build()
		);

		RevealEngineInitializer.registerResource(CorsFilter.class);
	}

}
