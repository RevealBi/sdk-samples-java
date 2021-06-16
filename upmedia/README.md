# Tomcat sample application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) | [Docs](https://help.revealbi.io/en/developer/java-sdk/overview.html) | [API Reference](https://help.revealbi.io/en/developer/java-sdk/api-reference.html)

## Usage
Download the code and run the following to get the WAR file to deploy in Tomcat:

```sh
mvn package
```

Now you can deploy the file `target/upmedia.war` to your Tomcat server, this application has been tested using Tomcat 9.

Assuming Tomcat is using the standard port, you can access the application at http://localhost:8080/upmedia.
