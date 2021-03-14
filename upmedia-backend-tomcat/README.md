# Tomcat sample backend application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=http%3A%2F%2Frevealpackages.eastus.cloudapp.azure.com%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](http://revealpackages.eastus.cloudapp.azure.com/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) 

## Usage
Download the code and run the following to get the WAR file to deploy in Tomcat:

```sh
mvn package
```

Now you can deploy the file `target/upmedia-backend.war` to your Tomcat server, this application has been tested using Tomcat 9.
Assuming Tomcat is using the standard port, you can verify the server is working properly by accessing http://localhost:8080/upmedia-backend/reveal-api/DashboardFile/Sales that will return the JSON document for the Sales sample dashboard.

This project provides only the backend piece, you can use it along [upmedia-react](https://github.com/RevealBi/sdk-samples-react/tree/main/upmedia-react) for the frontend.
