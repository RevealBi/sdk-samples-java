# Sample applications using [RevealBI](https://revealbi.io) Java SDK

## Usage

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
  <version>0.9.5</version>
</dependency>
```

### Integrating Reveal into existing applications
Here we include some additional information about integrating Reveal into existing Spring Boot and Tomcat applications.

#### Adding Reveal to an existing Tomcat application
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
- Add a dependency to “reveal-sdk” (“version_here” must be replaced with the version you’re using, like 0.9.5):
```xml
   <dependency>
         <groupId>com.infragistics.reveal.sdk</groupId>
         <artifactId>reveal-sdk</artifactId>
         <version>version_here</version>
   </dependency>
```
- Add a ServletContextListener class that will initialize Reveal. You can copy the class WebAppListener from [upmedia-backend-tomcat](upmedia-backend-tomcat) source code (in package com.pany.analytics.upmedia.reveal).
  - The parameters passed to RevealEngineInitializer.initialize (UpmediaAuthenticationProvider, UpmediaUserContextProvider, UpmediaDashboardProvider) are the providers used to customize Reveal, you’ll need to create your own providers when integrating Reveal into your application. 


#### Adding Reveal to an existing Spring Boot application
If you have an existing Spring Boot application and you want to add Reveal, you need to follow these steps:
- Add a dependency to “spring-starter-jersey” (if not added yet):
```xml
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jersey</artifactId>
   </dependency>
```
- Add a dependency to “reveal-sdk” (“version_here” must be replaced with the version
you’re using, like 0.9.5):
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

