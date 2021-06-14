# Grizzly sample application using [RevealBI](https://revealbi.io/) Java SDK
[![maven_version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.revealbi.io%2Frepository%2Fpublic%2Fcom%2Finfragistics%2Freveal%2Fsdk%2Freveal-sdk%2Fmaven-metadata.xml)](https://maven.revealbi.io/#basicsearch/com.infragistics.reveal.reveal-sdk)
#### [Website](https://revealbi.io/) 

## Usage
Download the code and run:

```sh
mvn clean package
```
To build the application, and then:

```sh
mvn exec:java
```

This will execute the Grizzly server at port 8080, you can verify the server is working properly by accessing http://localhost:8080/upmedia-backend/reveal-api/dashboards that will return a JSON document with the list of all sample dashboards.

This project provides only the backend piece, you can use it along [upmedia-react](https://github.com/RevealBi/sdk-samples-react/tree/main/upmedia-react) for the frontend.
