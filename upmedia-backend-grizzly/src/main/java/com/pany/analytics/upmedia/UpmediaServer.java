package com.pany.analytics.upmedia;

import java.net.URI;
import java.util.HashSet;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.infragistics.reveal.engine.init.InitializeParameterBuilder;
import com.infragistics.reveal.engine.init.RevealEngineInitializer;
import com.pany.analytics.upmedia.reveal.CorsFilter;
import com.pany.analytics.upmedia.reveal.UpmediaAuthenticationProvider;
import com.pany.analytics.upmedia.reveal.UpmediaDashboardProvider;
import com.pany.analytics.upmedia.reveal.UpmediaUserContextProvider;

import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.auth.simple.AllowAllReadAuthorizationProvider;
import io.revealbi.sdk.ext.rest.DashboardsResource;

public class UpmediaServer {
	public static void main(String[] args) {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).path("/upmedia-backend/reveal-api").build();

		UpmediaDashboardProvider dashboardProvider = new UpmediaDashboardProvider();

		// set it as the dashboard repository, used by DashboardsResource (that returns the list of dashboards at /reveal-api/dashboards)
		DashboardRepositoryFactory.setInstance(dashboardProvider);

		// by default all access is denied, we're setting here a provider that allows
		// reading all dashboards and listing them
		AuthorizationProviderFactory.setInstance(new AllowAllReadAuthorizationProvider());
		
		RevealEngineInitializer.initialize(new InitializeParameterBuilder()
				.setAuthProvider(new UpmediaAuthenticationProvider())
				.setUserContextProvider(new UpmediaUserContextProvider())
				.setDashboardProvider(dashboardProvider)
				.setLicense("LICENSE_KEY_HERE")
			.build());

		HashSet<Class<?>> resourceClasses = new HashSet<Class<?>>(RevealEngineInitializer.getClassesToRegister());
		resourceClasses.add(DashboardsResource.class);
		resourceClasses.add(CorsFilter.class);
		ResourceConfig config = new ResourceConfig(resourceClasses);

		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
		try {
			server.start();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
