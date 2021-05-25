package com.pany.analytics.upmedia.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.pany.analytics.upmedia.reveal.CorsFilter;

import io.revealbi.sdk.ext.rest.DashboardsResource;

@ApplicationPath("api")
public class JAXActivator extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(DashboardsResource.class);
		classes.add(CorsFilter.class);
		classes.add(MultiPartFeature.class);
		return Collections.unmodifiableSet(classes);
	}
}