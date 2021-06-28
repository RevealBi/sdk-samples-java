# Tomcat sample backend application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) 

## Usage
Download the code and run the following to get the WAR file to deploy in Tomcat:

```sh
mvn package
```

Now you can deploy the file `target/studio-backend.war` to your Tomcat server, this application has been tested using Tomcat 9.
Assuming Tomcat is using the standard port, you can verify the server is working properly by accessing http://localhost:8080/studio-backend/reveal-api/dashboards that will return a JSON document with the list of sample dashboards, the dashboards are installed automatically the first time the application is executed.

This project provides only the backend piece, you can use it along [studio-react](https://github.com/RevealBi/sdk-samples-react/tree/main/studio) for the frontend.

## How it works
This sample is called "studio" because it allows the creation and storage of data sources (and credentials to login to them) and dashboards.

### Where data is stored
This data (data sources, credentials and dashboards) is stored in the file system, under the user's home in a directory called `reveal-studio`, as configured in [WebAppListener.java](src/main/java/io/revealbi/samples/studio/reveal/WebAppListener.java):
```java
FileSystemExtFactory.registerAllServices("${user.home}/reveal-studio", true);
```
The `true` flag we're passing as the second parameter indicates we want these services to store data per user, on some other scenarios like read-only anonymous applications we might want to pass false so all users share the same data.

ℹ️ | The way data is stored by these classes is NOT production ready, first because data is stored in the file system that is not the best option but mainly because sensitive data (like tokens or passwords) is not encrypted. We'll be releasing soon improved implementations of these services storing data in databases and encrypting sensitive data.
:---: | :---

The previous call to `registerAllServices` is a shortcut to register providers in the following factories: `DashboardRepositoryFactory`, `CredentialRepositoryFactory`, `DataSourcesRepositoryFactory` and `OAuthTokenRepositoryFactory`.

So, you can replace that line by registering your own implementations for some or all of these services, for example you might want to keep dashboards and data sources stored in the file system but move credentials and OAuth tokens to a database, for security reasons.

### Allowing read/write permissions
Usually, in a real-world application, you would have authentication and you would have code to check for permissions as most likely there will be different kind of users in your application.
But this is a sample application and we need to keep it simple and focused on how to enable the creation of data sources and dashboards, that means it doesn't include authentication, and the user will be always `guest`.
As we want to have the creation of dashboards and data sources enabled, even for this guest user, we're installing an authentication provider that enables all operations on dashboards:
```java
AuthorizationProviderFactory.setInstance(new AllowAllAuthorizationProvider());
```

### Setting up initial dashboards
To improve the experience when running this sample, we're installing the sample dashboards for the `guest` user the first time the application is run, we detect that by looking for the `dashboards/guest` folder under `reveal-studio`, if such folder doesn't exist then the sample dashboards will be installed:
```java
FileSystemExtFactory.installSampleDashboards("guest", getClass(), new String[] {"/Campaigns.rdash", "/Healthcare.rdash", "/Manufacturing.rdash", "/Marketing.rdash", "/Sales.rdash"});
```

### Initializing Reveal
In other samples we're using custom "providers" for dashboards and data sources for the sample, like a custom dashboard provider that returns a hard-coded list of dashboards.
In this sample, we want to use the providers that we just configured to read/write data in the file system, so instead of setting custom instances we're setting the instances installed when we called `FileSystemExtFactory.registerAllServices`:
```java
RevealEngineInitializer.initialize(new InitializeParameterBuilder().
    setAuthProvider(CredentialRepositoryFactory.getInstance()).
    setUserContextProvider(new UserContextProvider()).
    setDashboardProvider(DashboardRepositoryFactory.getInstance()).
  build()
);
```
The custom user provider in this sample, as mentioned before will return always `guest` as there's no authentication configured.

### Configuring OAuth providers
Some of the data providers, for example databases like MS SQL Server, are pretty easy to connect to, you basically need the connection information (host and port) and usually just simple credentials (domain, user and password), for these providers there's no additional configuration required server side, you enable them client side and then in the server you only need to have the required providers installed to have data sources and its credentials stored properly.

But unfortunately, that's not the case for other providers, specifically those requiring an OAuth authentication, for example Google Analytics. Even when you might consider the access to the provider is performed by Reveal, as the authentication to the provider is performed as part of your application, you'll need to configure your application as an OAuth client for such provider. This is not a trivial process but it's usually a well documented process, at least for most of the OAuth providers supported by Reveal.

Using Google Analytics as an example, in order to authenticate end users with Google Analytics you need to configure an OAuth application in the Google Developers Console, as end users will enter credentials in your application (in a URL you control) you need to show Google that such application is secure and can be trusted. 
The requirements from Google vary if you're deploying a public application or just an application that is internal to your company, for more information check [this page](https://support.google.com/cloud/answer/10311615?authuser=2#user-type&zippy=).

For more information on how to create an OAuth application for Google Analytics you can read [this section](#creating-an-oauth-application-for-google-analytics) below.
Once you have your application configured in the OAuth provider (Google in this case) you'll have this information: `clientId` and `clientSecret` and you'll have a way to configure the redirect URI(s) for the application.

Continuing with Google Analytics as the example, the redirect URI to be configured is `reveal-api/oauth/GOOGLE_ANALYTICS/callback`, for example for a React application and for the development environment, it would be: `http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback`, please note that only `localhost` is allowed to be accessed using `http` as the protocol, for any other name you need to use `https`, that's an important consideration when deploying to an internal test server or a production environment.

Once you registered the OAuth application and the redirect URI, you need to configure Reveal to use those values in the login process for such provider, for Google Analytics you can do this:
```java
OAuthManagerFactory.getInstance().registerProvider(
  OAuthProviderType.GOOGLE_ANALYTICS, 
  "client_id_here", //clientId 
  "client_secret_here", //clientSecret
  "redirect url, like: http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback" //redirectUri
);
```
In this sample, and to avoid hard-coding test OAuth settings in the public source code, we're loading the OAuth providers from a file, located at `{user.home}/reveal-studio/oauth.json`:
```java
OAuthManagerFactory.registerProviders("${user.home}/reveal-studio/oauth.json");
```
Where the `oauth.json` file has a format like this:
```json
{
  "providers": {
    "GOOGLE_ANALYTICS": {
      "clientId": "client_id_here",
      "clientSecret": "client_secret_here",
      "redirectUri": "http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback"
    }
}
```

### Register extension endpoints
Finally, in order to have the endpoints for managing dashboards, data sources and credentials installed (they are not included as part of the Reveal SDK, they are included as part of the "extensions" project at [sdk-java-ext](https://github.com/RevealBi/sdk-java-ext)) we're including this line:
```java
RestExtFactory.registerAllResources();
```
This will register all resources required to read/write dashboards, data sources, credentials and OAuth tokens and these resources will automatically use the providers registered before to read/write them.

## Creating an OAuth application for Google Analytics
In this section we describe how to use the Google Developers Console to create an OAuth application in order to get data from Google Analytics in Reveal embedded in your application.

1. Go to [console.developers.google.com](https://console.developers.google.com).
2. Create a new project or use an existing one, for this document we created a new project called “RevealStudioTest”.
3. Enable Google Analytics APIs: in the “Dashboard” screen click “Enable APIs and Services”, search for “analytics” and enable both “Google Analytics API” and “Google Analytics Reporting API”:

<img width="468" alt="image" src="https://user-images.githubusercontent.com/14890904/123649394-1773d980-d800-11eb-93d2-b458365e6623.png">

5. Configure the “OAuth consent screen”:
<img width="468" alt="image" src="https://user-images.githubusercontent.com/14890904/123648404-4d648e00-d7ff-11eb-8bfd-d7098ca0f9f8.png">

You need to configure it based on the type of your application, for internal applications to your organizations you can select internal, for this document we’ll be using an “external” application, then click create and configure the requested information in the next screen:

<img width="468" alt="image" src="https://user-images.githubusercontent.com/14890904/123648456-57868c80-d7ff-11eb-9e03-5a62e164061c.png">

Please note that not all of the information in this screen is mandatory to have an application for testing, filling the fields in the screenshot above (App name, support email and App logo) should be enough for now, then click “Save and Continue” to configure the scopes.

In the scopes section you need to configure the following:

<img width="468" alt="image" src="https://user-images.githubusercontent.com/14890904/123648525-653c1200-d7ff-11eb-858a-a8d8d4cee77f.png">

If you don’t find the option for the “analytics.readonly” scope, then you need to enable the Google Analytics APIs, as described in step 3. Then click “Save and Continue” to configure test users.

Add at least one user to the list of test users, the user you’re planning to use when creating the data source in Reveal, click “Save and Continue” to complete the configuration of the OAuth Consent Screen.

5. Create OAuth credentials
In the “Credentials” screen, click “Create Credentials” and select “OAuth client ID”, then select “Web application” as the “Application type”:

<img width="468" alt="image" src="https://user-images.githubusercontent.com/14890904/123648629-7a18a580-d7ff-11eb-94bf-701f4cc286dc.png">

You can change the name for the web client or just leave “Web client 1”, then you need to setup the redirect URI, add the following one:
http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback

Then click “Create” to create the web client, you will see a screen like this one with the “Client ID” and “Client Secret” values for your application, you need to copy them as you’ll need to configure them in Reveal, along the redirect URI:

<img width="326" alt="image" src="https://user-images.githubusercontent.com/14890904/123648669-83a20d80-d7ff-11eb-9172-2d05906aa45c.png">

6. Configure the OAuth provider in Reveal, in the class initializing Reveal (usually WebAppListener), add a line like this (replacing clientId and clientSecret with the corresponding values):

```java
OAuthManagerFactory.getInstance().registerProvider(
	OAuthProviderType.GOOGLE_ANALYTICS, 
	"client_id_here", //clientId 
	"client_secret_here", //clientSecret
	"http://localhost:3000/studio-backend/reveal-api/oauth/GOOGLE_ANALYTICS/callback"
);
```
