# Sample applications using [RevealBI](https://revealbi.io) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch~public/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) | [Docs](https://help.revealbi.io/en/developer/java-sdk/overview.html) | [API Reference](https://help.revealbi.io/en/developer/java-sdk/api-reference.html)

  
ℹ️ | Please note the Maven repo has been changed to https://maven.revealbi.io/repository/public
:---: | :---

## Latest Artifacts:
- Latest Version: 1.0.6
- Latest JS Files: [here](https://maven.revealbi.io/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.6/reveal-sdk-distribution-1.0.6-js.zip)

## Basic Usage

### Requirements
RevealBI Java SDK requires **Java 11** or newer.

### Add Maven dependency
RevealBI Java SDK is distributed as a set of [Maven](https://maven.apache.org/what-is-maven.html) modules. The easiest way to use it is to add a reference to our Maven Repository and a dependency to the reveal-sdk artifact into your Maven `pom.xml` file as described below. 
If you're not familiar with Maven please refer to its [documentation](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

To include RevealBI Java SDK into your application, simply add the following repository:
```xml
<repositories>
  <repository>
    <id>reveal.public</id>
    <url>https://maven.revealbi.io/repository/public</url>
  </repository>	
</repositories>
```

and the following dependency, both to your `pom.xml` file:
```xml
<dependency>
  <groupId>com.infragistics.reveal.sdk</groupId>
  <artifactId>reveal-sdk</artifactId>
  <version>1.0.6</version>
</dependency>
```

### Download JS Files
For a given version, for example "1.0.6", you can download the JS files using this URL: https://maven.revealbi.io/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.6/reveal-sdk-distribution-1.0.6-js.zip

Please note there are two places in the URL where the version (1.0.6 in this case) is referenced, you need to change both to get JS files for another version.

## Configuration
### Export feature required configurations
RevealBI for Java uses some native components for exporting Dashboards to different formats: Image, PDF, PPT and Excel.

We use [Playwright for Java](https://github.com/microsoft/playwright-java) for exporting images and our own native application (called ExportTool) for exporting PDF, PPT and Excel documents.

The required downloads (some tools required by Playwright and the ExportTool binary) are automatically triggered the first time a Dashboard is opened, trying to get these tools ready for the first Export request, but for some platforms there are some dependencies that need to be installed in advance, and also your server environment might restrict external downloads and you might need to setup these tools manually.

#### Linux dependencies
There are dependencies to multiple native libraries in Linux and the exact list of dependencies you need to install will depend on the distribution used, version and list of packages previously installed. 

Here we provide the list of libraries needed to have it working on a basic Ubuntu 18.0.4 distribution, if it keeps failing the errors in the log file should provide more information about the missing libraries:
  
```sh
sudo apt-get update

sudo apt-get install -y libgdiplus\
        libatk1.0-0\
        libatk-bridge2.0-0\
        libxkbcommon0\
        libxcomposite1\
        libxdamage1\
        libxfixes3\
        libxrandr2\
        libgbm1\
        libgtk-3-0\
        libpango-1.0-0\
        libcairo2\
        libgdk-pixbuf2.0-0\
        libatspi2.0-0    

sudo apt-get install -y --no-install-recommends xvfb          
```
In a few environments we also had to install:
```sh
sudo apt-get install -y --allow-unauthenticated libc6-dev

sudo apt-get install -y --allow-unauthenticated libx11-dev
```

#### macOS dependencies
For macOS, the required library is libgdiplus and the installation procedure is described [here](https://docs.microsoft.com/th-th/dotnet/core/install/macos#libgdiplus).

#### Manual configuration of required tools
##### ExportTool manual setup
Please note these instructions are required only if you're having issues with the automatic download mechanism, or if you want to have everything pre-installed in advance.
- First, download the required binaries for your platform: [Windows](https://download.infragistics.com/reveal/builds/sdk/java/ExportTool/1.0.0/win-x64.zip), [Linux](https://download.infragistics.com/reveal/builds/sdk/java/ExportTool/1.0.0/linux-x64.zip) or [macOS](https://download.infragistics.com/reveal/builds/sdk/java/ExportTool/1.0.0/osx-x64.zip).
- Unzip this file to some directory in the server where your Web Application is running, make sure the user you're using to execute the server process (Tomcat for example) has access to the directory where you extracted the zip file.
- After extracting the zip file you should get the ExportTool at this location: `<dir>/<version>/<arch>/ExportTool`, for example `<dir>/1.0.0/linux-x64/ExportTool`.
- Initialize Reveal setting the directory where you extracted the zip file, like in the following code snippet:
```java
String exportToolDir = "<dir>";
RevealEngineInitializer.initialize(new InitializeParameterBuilder().
		setAuthProvider(new UpmediaAuthenticationProvider()).
		setUserContextProvider(new UpmediaUserContextProvider()).
		setDashboardProvider(new UpmediaDashboardProvider()).
		setExportToolContainerPath(exportToolDir).
    build()
);
```
- Alternatively, you can specify the directory through the system property `reveal.exportToolContainerPath`:
```sh
java -Dreveal.exportToolContainerPath=<dir> -jar target/upmedia-backend-spring.war
```

##### Playwright configuration
Playwright automatically downloads the required binaries, but if a manual configuration is required or you want to understand better how it works or how to tweak it, you can check Playwright documentation [here](https://playwright.dev/java/docs/installation).

## Integrating Reveal into existing applications
Here we include some additional information about integrating Reveal into existing Spring Boot and Tomcat applications.

### Adding Reveal to an existing Tomcat application
If you have an existing Tomcat (or any other JEE container) application and you want to add Reveal, you need to follow these steps:
- Add a dependency to a JAX-RS implementation, there are multiple options (Jersey, Resteasy, etc.), you’ll need to follow the steps described by the provider. Here the dependencies to add for Jersey:
```xml
     <dependency>
           <groupId>org.glassfish.jersey.containers</groupId>
           <artifactId>jersey-container-servlet</artifactId>
           <version>2.32</version>
     </dependency>
     <dependency>
           <groupId>org.glassfish.jersey.inject</groupId>
           <artifactId>jersey-cdi2-se</artifactId>
           <version>2.32</version>
     </dependency>
```
- Add a dependency to “reveal-sdk” (“version_here” must be replaced with the version you’re using, like 1.0.6):
```xml
   <dependency>
         <groupId>com.infragistics.reveal.sdk</groupId>
         <artifactId>reveal-sdk</artifactId>
         <version>version_here</version>
   </dependency>
```
- Add a ServletContextListener class that will initialize Reveal. You can copy the class WebAppListener from [upmedia-backend-tomcat](upmedia-backend-tomcat) source code (in package com.pany.analytics.upmedia.reveal).
  - The parameters passed to RevealEngineInitializer.initialize (UpmediaAuthenticationProvider, UpmediaUserContextProvider, UpmediaDashboardProvider) are the providers used to customize Reveal, you’ll need to create your own providers when integrating Reveal into your application. 


### Adding Reveal to an existing Spring Boot application
If you have an existing Spring Boot application and you want to add Reveal, you need to follow these steps:
- Add a dependency to “spring-starter-jersey” (if not added yet):
```xml
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jersey</artifactId>
   </dependency>
```
- Add a dependency to “reveal-sdk” (“version_here” must be replaced with the version
you’re using, like 1.0.6):
```xml
   <dependency>
         <groupId>com.infragistics.reveal.sdk</groupId>
         <artifactId>reveal-sdk</artifactId>
         <version>version_here</version>
   </dependency>
```
- Add a JerseyConfig component that will initialize a JAX-RS application with Reveal resources. You can copy the class RevealJerseyConfig from [upmedia-backend-spring](upmedia-backend-spring) source code (in package com.pany.analytics.upmedia.reveal).
  - The @ApplicationPath annotation configures the path where Reveal services will be available, if you modify it then you’ll need to modify the path client-side too, in the case of the React application this is configured in index.html:
```js
  $.ig.RevealSdkSettings.setBaseUrl("http://localhost:8080/upmedia-backend/reveal-api/");
```
  - The parameters passed to RevealEngineInitializer.initialize (UpmediaAuthenticationProvider, UpmediaUserContextProvider, UpmediaDashboardProvider) are the providers used to customize Reveal, you’ll need to create your own providers when integrating Reveal into your application. 

## Returning the list of dashboards
With the May-2021 release the Web SDK includes a component that renders a preview (a thumbnail) for a dashboard, the information required to render that preview is returned from the server. This section describes how to add that endpoint to your server application.

The upmedia-backend projects ([upmedia-backend-tomcat](upmedia-backend-tomcat) and [upmedia-backend-spring](upmedia-backend-spring)) are both configured to return this list of dashboards, to verify it you can run any of them and the open this page in a browser: http://localhost:8080/upmedia-backend/reveal-api/dashboards.

These samples are taking advantage of a new set of extensions to the Reveal SDK ([sdk-java-ext](https://github.com/RevealBi/sdk-java-ext)), these projects provide base implementations to some services usually needed along the Reveal SDK, like services to return the list of dashboards, the list of data sources and credentials associated to them, authorization, etc.

### Implementing a dashboard repository
In particular, for the list of dashboards, the upmedia-backend samples are using the following classes:
- [IDashboardRepository](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/api/IDashboardRepository.java): An interface extending IRVDashboardProvider (the SDK dashboard provider interface) with two methods for listing and deleting dashboards.
- [BaseDashboardRepository](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/base/BaseDashboardRepository.java): A base implementation of IDashboardRepository to simplify the implementation of dashboard listing, you just need to implement the method getUserDashboardIds (that returns the list of dashboard ids available for a given user) and it will take care of returning the dashboard list needed client side to render the thumbnails.
- [DashboardRepositoryFactory](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/api/DashboardRepositoryFactory.java): a class used to register the singleton instance of IDashboardRepository that should be used.
- [DashboardsResource](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-rest/src/main/java/io/revealbi/sdk/ext/rest/DashboardsResource.java): A REST service that uses the implementation of IDashboardRepository and returns the list of dashboards to the client.

If you take a look at [UpmediaDashboardProvider](upmedia-backend-tomcat/src/main/java/com/pany/analytics/upmedia/reveal/UpmediaDashboardProvider.java) you'll see we're implementing getUserDashboardIds() as follows:
```java
@Override
protected String[] getUserDashboardIds(String arg0) throws IOException {
    return new String[] {
        "Campaigns",
	"Manufacturing",
	"Marketing",
	"Sales"
    };
}
```
here we're returning a hard-coded list of dashboards, including those files distributed with the sample, locate in [resources](upmedia-backend-tomcat/src/main/resources) and loaded in the implementation of getDashboard:
```java
@Override
public InputStream getDashboard(String userId, String dashboardId) throws IOException {
    InputStream dashboardStream = getClass().getResourceAsStream("/" + dashboardId + ".rdash");
    if (dashboardStream == null) {
        log.warning("Dashboard not found: " + dashboardId);
    }
    return dashboardStream;
}
```

### Adding the dashboard repository to your application
In order to add the list of dashboards service to your application, you'll need to implement the dashboard repository interface (most likely by extending BaseDashboardRepository) and register the REST service defined in DashboardsResource in your JAX-RS application:

#### Maven Dependencies
In order to use these extension services there are some more Maven dependencies you need to add:
```xml
<dependency>
    <groupId>io.revealbi.sdk.ext</groupId>
    <artifactId>reveal-ext-api</artifactId>
    <version>1.0.1</version>
</dependency>
<dependency>
    <groupId>io.revealbi.sdk.ext</groupId>
    <artifactId>reveal-ext-rest</artifactId>
    <version>1.0.1</version>
</dependency>
```

#### Configuring Reveal to use the dashboard repository
Once you have implemented the dashboard repository, the next step is to configure Reveal to use it. 

Using the upmedia-backend samples as a reference, this is performed when Reveal is initialized, for the upmedia-backend-tomcat sample, this is performed in [WebAppListener](upmedia-backend-tomcat/src/main/java/com/pany/analytics/upmedia/reveal/WebAppListener.java):
```java
UpmediaDashboardProvider dashboardProvider = new UpmediaDashboardProvider();
	
//set it as the dashboard repository, used by DashboardsResource (that returns the list of dashboards at /reveal-api/dashboards)
DashboardRepositoryFactory.setInstance(dashboardProvider);
		
//by default all access is denied, we're setting here a provider that allows reading all dashboards and listing them
AuthorizationProviderFactory.setInstance(new AllowAllReadAuthorizationProvider());
```

Before returning a dashboard or the list of them, the REST service implemented in [DashboardsResource](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-rest/src/main/java/io/revealbi/sdk/ext/rest/DashboardsResource.java) checks for permissions, using the [IAuthorizationProvider](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/api/IAuthorizationProvider.java) instance set in [AuthorizationProviderFactory](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/api/AuthorizationProviderFactory.java).

By default, this factory uses a "deny all" implementation, so you need to set some other implementation in order to access dashboards, there's a convenience implementation you can use to grant read-only access to all users: [AllowAllReadAuthorizationProvider](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-api/src/main/java/io/revealbi/sdk/ext/auth/simple/AllowAllReadAuthorizationProvider.java).

#### Configuring the REST Service
You also need to publish the REST Service (implemented in [DashboardsResource](https://github.com/RevealBi/sdk-java-ext/blob/main/reveal-ext-rest/src/main/java/io/revealbi/sdk/ext/rest/DashboardsResource.java)), how to do it depends if you're using Spring or regular JEE like Tomcat.

- For Spring:
You can do the same we do in [RevealJerseyConfig](upmedia-backend-spring/src/main/java/com/pany/anaylitics/upmedia/reveal/RevealJerseyConfig.java):
```java
register(DashboardsResource.class);
```
- For Tomcat:
You can do the same we do in [WebAppListener](upmedia-backend-tomcat/src/main/java/com/pany/analytics/upmedia/reveal/WebAppListener.java):
```java
RevealEngineInitializer.registerResource(DashboardsResource.class);
```

#### How to confirm the dashboard repository is working fine
For both Spring and Tomcat servers, assuming the port you're using is 8080 and you're using the suggested "reveal-api" path, you can get the list of dashboards using this URL: http://localhost:8080/upmedia-backend/reveal-api/dashboards, where "upmedia-backend" needs to be replaced with your application name.

### Creating your own service without using the sdk-java-ext packages
If you don't want to use the basic services in [sdk-java-ext](https://github.com/RevealBi/sdk-java-ext), you can create your own service returning the summary for your dashboards, here is a code snippet of what you need to do:
```java
InputStream in = getDashboard(userId, dashboardId);
RVDashboardSummary summary = RVSerializationUtilities.getDashboardSummary(in);
Map<String, Object> json = summary.toJson();
```
Where "in" is an InputStream with the contents of the dashboard in "rdash" format. The value of that map (json) is what you need client side to render the thumbnail.
Now, you just need to serialize the "json" variable to JSON and send it to the client, that JSON document is what you need to pass as the dashboardInfo property to $.ig.RevealDashboardThumbnailView JS class client side.

### Out-of-the-box implementations
In addition to the base auxiliary classes described before, there are ready to use implementations that provides repositories for dashboards, data sources and credentials and also the REST resources that exposes such services.

This is still a work in progress, for example the only implementation available today for these services uses the file system to store data (for example dashboards are loaded/saved to a directory in the file system, or data sources are loaded from a JSON file in the file system). These services are implemented in [reveal-ext-fs](https://github.com/RevealBi/sdk-java-ext/tree/main/reveal-ext-fs).

The REST services are implemented in [reveal-ext-rest](https://github.com/RevealBi/sdk-java-ext/tree/main/reveal-ext-rest) and they use the services registered in the factories defined in reveal-ext-api, so you could create your own implementation of the repositories, for example storing dashboards in a relational database, register them (for example in DashboardRepositoryFactory) and then use the REST services implemented here to access them.

