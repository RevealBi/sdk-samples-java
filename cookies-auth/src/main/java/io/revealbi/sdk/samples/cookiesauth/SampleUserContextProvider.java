package io.revealbi.sdk.samples.cookiesauth;


import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;

import com.infragistics.reveal.sdk.base.RVUserContext;
import com.infragistics.reveal.sdk.rest.RVBaseUserContextProvider;

public class SampleUserContextProvider extends RVBaseUserContextProvider {

	@Override
	protected RVUserContext getUserContext(ContainerRequestContext context) {
		//look for the session id cookie, this cookie should be automatically added by Tomcat, so it should be always present
		Cookie sessionCookie = context.getCookies().get("JSESSIONID");
		
		//we don't have a real authentication in this sample, so we're just hard-coding "guest" as the user here
		//when using standard auth mechanisms, the userId can be obtained using: context.getSecurityContext().getUserPrincipal().getName()
		String userId = "guest";
		
		//RVUserContext allows to store properties in addition to the userId, these properties can be used later
		//for example in the authentication provider, in this case we're including the session id cookie so we 
		//can forward it to the REST data source, see SampleAuthenticationProvider.
		Map<String, String> properties = new HashMap<String, String>();
		if (sessionCookie != null) {
			properties.put(sessionCookie.getName(), sessionCookie.getValue());
		}
		return new RVUserContext(userId, properties);
	}

}
