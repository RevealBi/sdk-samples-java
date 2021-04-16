# Sample applications using [RevealBI](https://revealbi.io) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=http%3A%2F%2Frevealpackages.eastus.cloudapp.azure.com%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](http://revealpackages.eastus.cloudapp.azure.com/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) | [Docs](https://help.revealbi.io/en/developer/java-sdk/overview.html)

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
    <url>http://revealpackages.eastus.cloudapp.azure.com/repository/public</url>
  </repository>	
</repositories>
```

and the following dependency, both to your `pom.xml` file:
```xml
<dependency>
  <groupId>com.infragistics.reveal.sdk</groupId>
  <artifactId>reveal-sdk</artifactId>
  <version>1.0.2</version>
</dependency>
```
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
- Add a dependency to “reveal-sdk” (“version_here” must be replaced with the version you’re using, like 1.0.2):
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
you’re using, like 1.0.2):
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
