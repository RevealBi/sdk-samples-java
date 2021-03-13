package com.pany.analytics.upmedia.reveal;


import javax.ws.rs.container.ContainerRequestContext;

import com.infragistics.reveal.sdk.rest.RVContainerRequestAwareUserContextProvider;

public class UpmediaUserContextProvider extends RVContainerRequestAwareUserContextProvider {

	@Override
	protected String getUserId(ContainerRequestContext requestContext) {
		if (requestContext == null) {
			System.out.println("WARN: request context not available");
		}
		return null;
	}

}
