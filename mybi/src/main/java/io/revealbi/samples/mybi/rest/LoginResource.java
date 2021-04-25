package io.revealbi.samples.mybi.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import io.revealbi.samples.mybi.shiro.BearerProviderToken;
import io.revealbi.samples.mybi.shiro.DisplayNamePrincipal;

@Path("/login")
public class LoginResource {	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public LoginResponse login(LoginRequest request) throws IOException {
		if (request == null || (request.accessToken == null && (request.username == null || request.password == null))) {
			return new LoginResponse(false, null, "Invalid request");
		}
		Subject subject = SecurityUtils.getSubject();				
		AuthenticationToken token;
		if (request.accessToken != null) {
			token = new BearerProviderToken(request.provider, request.accessToken);
		} else {
			token = new UsernamePasswordToken(request.username, request.password);
		}
		
		try {
			subject.login(token);
			DisplayNamePrincipal displayNamePrincipal = subject.getPrincipals().oneByType(DisplayNamePrincipal.class);
			LoginResponse response = new LoginResponse(true, subject.getSession().getId().toString(), "Success");
			if (displayNamePrincipal != null) {
				response.displayName = displayNamePrincipal.getDisplayName();
			}
			response.emailAddress = subject.getPrincipal().toString();
			return response;
        } catch (UnknownAccountException uae) {
			uae.printStackTrace();
			return new LoginResponse(false, null, "User not found or invalid credentials");
        } catch (IncorrectCredentialsException ice) {
			ice.printStackTrace();
			return new LoginResponse(false, null, "User not found or invalid credentials");
        } catch (LockedAccountException lae) {
			lae.printStackTrace();			
			return new LoginResponse(false, null, "The account for username " + token.getPrincipal() + " is locked. Please contact your administrator to unlock it.");			
        } catch (Exception exc) {
			exc.printStackTrace();
			return new LoginResponse(false, null, exc.getClass().getSimpleName() +  ": " + exc.getMessage());			
		}
	}
	
	@DELETE
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
	}
	
	public static class LoginRequest {
		public String username;
		public String password;
		public String provider;
		public String accessToken;
	}
	
	public static class LoginResponse {
		public LoginResponse(boolean succeeded, String sessionId, String message) {
			this.succeeded = succeeded;
			this.sessionId = sessionId;
			this.message = message;
		}
		public boolean succeeded;
		public String message;
		public String sessionId;
		public String displayName;
		public String emailAddress;
	}
}
