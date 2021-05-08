package io.revealbi.samples.mybi.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

public abstract class BaseResource {
	@Context
	protected ContainerRequestContext requestContext;

	protected String getUserId() {
		return UserIdProvider.getUserId(requestContext);
	}
}
