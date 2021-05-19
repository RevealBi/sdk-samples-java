package io.revealbi.samples.browser.rest;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import io.revealbi.samples.browser.shiro.DisplayNamePrincipal;

@Path("/myinfo")
public class MyInfoResource {	
	@GET
	@Produces("application/json")
	public UserInfo getMyInfo() throws IOException {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || !subject.isAuthenticated()) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		Object principal = subject.getPrincipal();
		String userId = principal.toString();
		
		Collection<DisplayNamePrincipal> displayNames = subject.getPrincipals().byType(DisplayNamePrincipal.class);
		String displayName = displayNames.isEmpty() ? null : displayNames.iterator().next().getDisplayName();
		
		return new UserInfo(userId, displayName == null ? userId : displayName);
	}
	
	public static class UserInfo {
		public UserInfo(String userId, String fullName) {
			this.userId = userId;
			this.fullName = fullName;
		}
		public String userId;
		public String fullName;
	}
}
