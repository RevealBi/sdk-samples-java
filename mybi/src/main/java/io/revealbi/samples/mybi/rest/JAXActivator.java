package io.revealbi.samples.mybi.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("api")
public class JAXActivator extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(DashboardsResource.class);
		classes.add(DataSourcesResource.class);
		classes.add(LoginResource.class);
		classes.add(CorsFilter.class);
		classes.add(MultiPartFeature.class);
		return Collections.unmodifiableSet(classes);
	}
}