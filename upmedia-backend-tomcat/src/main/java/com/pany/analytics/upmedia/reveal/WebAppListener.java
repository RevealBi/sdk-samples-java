package com.pany.analytics.upmedia.reveal;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;

import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.auth.simple.AllowAllReadAuthorizationProvider;
import io.revealbi.sdk.ext.rest.DashboardsResource;

@WebListener
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		//nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		UpmediaDashboardProvider dashboardProvider = new UpmediaDashboardProvider();
		
		//set it as the dashboard repository, used by DashboardsResource (that returns the list of dashboards at /reveal-api/dashboards)
		DashboardRepositoryFactory.setInstance(dashboardProvider);
		
    	//by default all access is denied, we're setting here a provider that allows reading all dashboards and listing them
		AuthorizationProviderFactory.setInstance(new AllowAllReadAuthorizationProvider());
		
		//initialize Reveal
		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(new UpmediaAuthenticationProvider()).
				setUserContextProvider(new UpmediaUserContextProvider()).
				setDashboardProvider(dashboardProvider).
			build()
		);

		RevealEngineInitializer.registerResource(ExceptionMapper.class);

		//register the CORS filter class
		RevealEngineInitializer.registerResource(CorsFilter.class);
		
        //register the dashboards resource, returns the list of dashboards that can be used with the thumbnail control
        //it also supports exporting dashboards (to download the rdash file) and deleting them
        //if you want to support also uploading dashboards you should register DashboardsUploadResource
		RevealEngineInitializer.registerResource(DashboardsResource.class);
	}

}
