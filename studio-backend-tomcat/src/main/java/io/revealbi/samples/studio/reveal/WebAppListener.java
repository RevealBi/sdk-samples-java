package io.revealbi.samples.studio.reveal;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;

import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.CredentialRepositoryFactory;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.api.oauth.OAuthManagerFactory;
import io.revealbi.sdk.ext.fs.FileSystemExtFactory;
import io.revealbi.sdk.ext.rest.RestExtFactory;

@WebListener
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		//nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
    	//by default all access is denied, we're setting here a provider that allows reading and writing all dashboards, just for this sample to be easy to use
		//as there's no authentication configured, the only user will be "guest"
		AuthorizationProviderFactory.setInstance(new AllowAllAuthorizationProvider());

		//registers all services (dashboards, data sources and credentials) to load/save data to {user.home}/reveal-studio
		FileSystemExtFactory.registerAllServices("${user.home}/reveal-studio", true);
				
		//if dashboards/guest folder is not found, then create it with the sample dashboards
		FileSystemExtFactory.installSampleDashboards("guest", getClass(), new String[] {"/Campaigns.rdash", "/Healthcare.rdash", "/Manufacturing.rdash", "/Marketing.rdash", "/Sales.rdash"}); 

		//initialize Reveal
		RevealEngineInitializer.initialize(new InitializeParameterBuilder().
				setAuthProvider(CredentialRepositoryFactory.getInstance()).
				setUserContextProvider(new UserContextProvider()).
				setDashboardProvider(DashboardRepositoryFactory.getInstance()).
			build()
		);

		//register the OAuth keys for Google Analytics, as this is an open source sample, we cannot hard-code the keys here
		//so we're loading them from a configuration file at {user.home}/reveal-studio/oauth.json
		OAuthManagerFactory.registerProviders("${user.home}/reveal-studio/oauth.json");

		//alternatively, you can register your providers with a code like this:
//		OAuthManagerFactory.getInstance().registerProvider(
//			OAuthProviderType.GOOGLE_ANALYTICS, 
//			"client_id_here", //clientId 
//			"client_secret_here", //clientSecret
//			"redirect url, like: http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback" //redirectUri
//		);

		//Registers endpoints for handling dashboards, data sources and credentials: /dashboards, /datasources and /credentials
		RestExtFactory.registerAllResources();
	}

}