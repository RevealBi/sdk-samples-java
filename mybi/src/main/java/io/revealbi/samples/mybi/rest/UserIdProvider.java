package io.revealbi.samples.mybi.rest;

import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;

public class UserIdProvider {
	public static String getUserId(ContainerRequestContext requestContext) {
		if (requestContext == null) {
			System.out.println("WARN: request context not available");
		}
		Principal principal = requestContext.getSecurityContext().getUserPrincipal();
		return principal == null ? "guest" : principal.getName();
	}
}
