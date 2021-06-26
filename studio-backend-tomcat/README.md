# Tomcat sample backend application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) 

## Usage
Download the code and run the following to get the WAR file to deploy in Tomcat:

```sh
mvn package
```

Now you can deploy the file `target/studio-backend.war` to your Tomcat server, this application has been tested using Tomcat 9.
Assuming Tomcat is using the standard port, you can verify the server is working properly by accessing http://localhost:8080/studio-backend/reveal-api/dashboards that will return a JSON document with an empty list, as there are no dashboards included initially.

This project provides only the backend piece, you can use it along [studio-react](https://github.com/RevealBi/sdk-samples-react/tree/main/studio-react) for the frontend.
