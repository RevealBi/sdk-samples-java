# Tomcat sample application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) 

## Usage
Download the code and run the following to get the WAR file to deploy in Tomcat:

```sh
mvn package
```

Now you can deploy the file `target/cookies-auth.war` to your Tomcat server, this application has been tested using Tomcat 9.

Assuming Tomcat is using the standard port, you can access the application at http://localhost:8080/cookies-auth.

## What to expect
This application includes a single page that renders a dashboard with two visualizations, these visualizations are using a REST service that echoes the list of headers and cookies received, just for testing purposes.

When you open the page at http://localhost:8080/cookies-auth you should see something like this:

<img width="1037" alt="image" src="https://user-images.githubusercontent.com/14890904/120902576-856d1c80-c617-11eb-8b1b-16fa84bb4d4b.png">

In the first visualization you can see the standard headers sent in the request to the REST data source, like "user-agent" or "accept-encoding", also a "cookie" header including a hard-coded cookie added in the sample code and a JSESSIONID cookie.
The second visualization is pretty similar but displays only parsed coookies, it can be used to confirm the "cookie" header is processed and parsed properly.

## How it works

In this application we want to get data from a REST service, and this service requires a cookie for authentication, the cookie we need to send is the same cookie used for authentication in the application. So, basically we need to "forward" a cookie from the application to the data source.

To keep things simple for this sample, we'll use the standard "JSESSIONID" as the cookie to forward, instead of a "real" authentication cookie.

### Storing the value for a cookie in the user context
In [SampleUserContextProvider](src/main/java/io/revealbi/sdk/samples/cookiesauth/SampleUserContextProvider.java) you can see we're getting the current session id value.
In a Java Web application running in Tomcat the JSESSIONID cookie is used to track sessions and by default it's enabled, so we're taking advantange of that for this sample, in your application you might need to use this same cookie, some other cookie or some other authentication header, the way to use it is the same.

_SampleUserContextProvider_ is extending _RVBaseUserContextProvider_ and implementing the _getUserContext_ method that returns a _RVUserContext_ object, which contains both the userId and a set of properties.
We're using this list of properties to store the sessionId, this way we can retrieve it later when credentials for a data source are requested.

```java
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
```

### Retrieving the cookie value from the user context
In [SampleAuthenticationProvider](src/main/java/io/revealbi/sdk/samples/cookiesauth/SampleAuthenticationProvider.java) we're extending _RVBaseAuthenticationProvider_, that takes care of passing a _RVUserContext_ to _resolveCredentials_.
We're using that _RVUserContext_ object to get the "JSESSIONID" property we installed in _SampleUserContextProvider_.

In this case our data source is a REST API data source, and we want to pass a couple of cookies as the credentials for it, including the session id cookie, so we need to return a _RVHeadersDataSourceCredentials_ object, this object can be used as the authentication credentials object only for REST or Web Resource data sources.

As _RVHeadersDataSourceCredentials_ is constructed with one or more headers, we need to create the "Cookie" header to be sent, according to the [HTTP documentation](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie) the "Cookie" header can include multiple cookies separated by "; ", and that's what we're doing in this code:

```java
public class SampleAuthenticationProvider extends RVBaseAuthenticationProvider {
	@Override
	public IRVDataSourceCredential resolveCredentials(RVUserContext userContext, RVDashboardDataSource dataSource) {
		if (dataSource instanceof RVRESTDataSource) {
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
			return new RVHeadersDataSourceCredentials("Cookie", cookies);
		} 
		return null;
	}
}
```
