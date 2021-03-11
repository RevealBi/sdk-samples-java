README.txt

UpMedia eclipse project

	Goal: Learn about the SDK with a Web project for eclipse that should be as easy as possible to use.

	Software to Install
		* Eclipse: https://www.eclipse.org/downloads/packages/ (Tested with "Eclipse IDE for Enterprise Java Developers" 2020-09)
		* Java SDK https://www.oracle.com/java/technologies/javase-downloads.html (Tested with 15.0.1)
		* Maven: 
			Download: https://maven.apache.org/download.cgi (Tested with 3.6.3)
			How to install: https://maven.apache.org/install.html
		* Tomcat: https://tomcat.apache.org/download-90.cgi (Tested with 9.0.41)
		* reveal-sdk

	Steps:
		* Open Eclipse, File -> Import -> Existing Projects into Workspace. Choose the <REVAL-SDK-PATH> as Root directory, the 'upmedia' project will be picked up by Eclipse. It is advised to check "Copy projects into workspace". Press 'Finish'.
		* At this point, the project should be loaded and compiling, ready to be inspected. The following are instructions to run it on tomcat.

		Run in tomcat from Eclipse:
			* in the upmedia project, right click -> Run on Server. Choose Tomcat 9 and configure the root path to the path where Tomcat was installed. Go through the default settings and then run. Eclipse's internal browser doesn't work pretty well (in Windows), so try with Chrome (the default URL will be http://localhost:8080)

		Alternatively:
			* Run as -> maven build. Use the "package" goal. A WAR file will be created, you can try it by manually deploying it to the downloaded Tomcat.


General steps for the SDK
	
	Goal: An overview of the SDK, that can provide the basics to both understand the sample eclipse project, or create one from scratch for any IDE

	The SDK web application sample 'upmedia':
		* src/.../WebAppListener.java: a Servlet @WebListener that initializes the RevealEngine, like this:
			RevealEngineInitializer.initialize(new UpmediaAuthenticationProvider(), new UpmediaUserContextProvider(), new UpmediaDashboardProvider());
			This step is mandatory, even if there are no custom Authentication/Dashboard/etc. providers to register (i.e. all null parameters)
		* src/.../UpmediaAuthenticationProvider.java: implementation of IRVAuthenticationProvider, which allows SDK integrators to provide credentials for accessing datasources.
		* src/.../UpmediaDashboardProvider.java: implementation of IRVDashboardProvider, which allows SDK integrators to provide the dashboard file
		* src/../UpmediaUserContextProvider.java: implementation of IRVUserContextProvider, which allows SDK integrators to tell the SDK what user is performing a given request (basically, to tell the IRVAuthenticatorProvider which is the current user)
		* WebContent/Reveal, this folder contains the .js files that must be served to the client side (they are referenced in the web page, as explained in https://www.infragistics.com/help/reveal-dev/setup-configuration-client-web)
		* WebContent/lib, this folder contains javascript libraries needed to run Reveal client side API (e.g. jquery, dayjs, ...)
		* pom.xml: Project dependencies to compile and create a WAR file.
		    - Reveal repository reference: 	http://revealpackages.eastus.cloudapp.azure.com/repository/public
			- reveal-sdk dependency:
				code:
				   	<dependency>
				   		<groupId>com.infragistics.reveal.sdk</groupId>
						<artifactId>reveal-sdk</artifactId>
						<version>0.9.4-SNAPSHOT</version>
				   	</dependency>
				bootstraps reveal-sdk and all its dependencies, except for the jax-ws-rs implementation

			- jax-ws-rs implementation, in this sample app we use Jersey, but other implementations can be used (e.g. Resteasy). These are the dependencies for Jersey:
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

Export Image functionality setup:
* Some linux distributions may lack basic stuff needed for export image. Playwright may complain about that, and output the required and missing libraries. 
  
  sudo apt-get install libatk1.0-0\
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
  
* If the export image never returns, and a 'chrome' process can be seen running and consuming significant CPU resources, try with the following:
  
  apt-get update && apt-get install -y --no-install-recommends \
    xvfb          
  

Export functionality setup:
* Copy the <REVEAL-SDK-PATH>/native folders to the WEB-INF/native folder.
* For Linux/OSX, install libgdiplus.
	- For Linux, the installation procedure depends on the actual Linux distr. For example, in Ubuntu it is 'sudo apt-get install -y libgdiplus'
	  Linux may also miss some additional components: 
	  	apt-get update && \
    		apt-get install -y --allow-unauthenticated libgdiplus libc6-dev

		apt-get update && \
    		apt-get install -y --allow-unauthenticated libx11-dev
	  	 
	- For OSX, the installation proc. is described here: https://docs.microsoft.com/th-th/dotnet/core/install/macos#libgdiplus
