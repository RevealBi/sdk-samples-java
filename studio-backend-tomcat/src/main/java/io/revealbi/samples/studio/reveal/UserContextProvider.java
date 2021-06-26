package io.revealbi.samples.studio.reveal;


import javax.ws.rs.container.ContainerRequestContext;

import com.infragistics.reveal.sdk.base.RVUserContext;
import com.infragistics.reveal.sdk.rest.RVBaseUserContextProvider;

import io.revealbi.sdk.ext.rest.UserIdProvider;

public class UserContextProvider extends RVBaseUserContextProvider {

	@Override
	protected RVUserContext getUserContext(ContainerRequestContext requestContext) {
		if (requestContext == null) {
			System.out.println("WARN: request context not available");
		}
		return new RVUserContext(UserIdProvider.getUserId(requestContext));
	}
}