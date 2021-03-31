package io.revealbi.samples.mybi.reveal;


import javax.ws.rs.container.ContainerRequestContext;

import com.infragistics.reveal.sdk.rest.RVContainerRequestAwareUserContextProvider;

import io.revealbi.samples.mybi.rest.UserIdProvider;

public class RevealUserContextProvider extends RVContainerRequestAwareUserContextProvider {

	@Override
	protected String getUserId(ContainerRequestContext requestContext) {
		return UserIdProvider.getUserId(requestContext);
	}

}
