package io.revealbi.sdk.samples.cookiesauth;


import java.util.HashMap;
import java.util.Map;

import com.infragistics.reveal.sdk.api.IRVAuthenticationProvider;
import com.infragistics.reveal.sdk.api.IRVDataSourceCredential;
import com.infragistics.reveal.sdk.api.RVHeadersDataSourceCredential;
import com.infragistics.reveal.sdk.api.model.RVDashboardDataSource;
import com.infragistics.reveal.sdk.api.model.RVRESTDataSource;
import com.infragistics.reveal.sdk.rest.RVUserContext;

public class SampleAuthenticationProvider implements IRVAuthenticationProvider {
	@Override
	public IRVDataSourceCredential resolveCredentials(String userId, RVDashboardDataSource dataSource) {
		if (dataSource instanceof RVRESTDataSource) {
			//retrieve the user context returned by SampleUserContextProvider, which is automatically encoded in userId
			RVUserContext userContext = RVUserContext.fromString(userId);
			
			//get the session id property included in the context
			String sessionId = userContext.getProperty("JSESSIONID");

			//pass two cookies, a fixed one just for testing purposes and the session id (if present)
			//"; " must be used as the separator when passing multiple cookies
			String cookies = "testCookie=testValue";
			if (sessionId != null) {
				cookies = cookies + "; JESSIONID=" + sessionId; 
			}

			//use the Cookie header as the authentication credentials for this data source
			//the data source used in this sample will echo back the list of headers/cookies received
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Cookie", cookies);
			return new RVHeadersDataSourceCredential(headers);
		} 
		return null;
	}
}
