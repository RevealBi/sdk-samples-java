# Changelog

This is actually acting as a Changelog for RevealBI Java SDK for now.

## [1.0.6] - 2021-06-14
- [JS Files](https://maven.revealbi.io/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.6/reveal-sdk-distribution-1.0.6-js.zip)
- Removed wrong dependency to `javax.servlet.ServletContext` class and `javax.servlet:javax.servlet-api` assembly that was causing NoClassDefFoundError exception (javax.servlet.ServletContext) when running in Grizzly.
- Created [new sample](upmedia-backend-grizzly) showing how to use Reveal with [Grizzly](https://javaee.github.io/grizzly/) server.

## [1.0.5] - 2021-06-07
- [JS Files](https://maven.revealbi.io/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.5/reveal-sdk-distribution-1.0.5-js.zip)
- Added a new type of credentials for REST and Web Resource data sources: RVHeadersDataSourceCredentials that allows using headers and cookies for authentication to these data sources, more information on how to use it in the new sample: [cookies-auth](cookies-auth).
- New RVUserContext class that can be used to store properties in addition to the userId.
- New sample showing how to authenticate to REST or Web Resource data sources using Cookies or other headers: [cookies-auth](cookies-auth).

## [1.0.4] - 2021-05-26
- [JS Files](https://maven.revealbi.io/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.4/reveal-sdk-distribution-1.0.4-js.zip)
- Added thumbnail (preview icon) JS component, more information in the new React sample: [upmedia-browser](https://github.com/RevealBi/sdk-samples-react/blob/main/upmedia-browser/README.md) and in [sdk-samples-java](https://github.com/RevealBi/sdk-samples-java/blob/develop/README.md#returning-the-list-of-dashboards).
- Added a new setting to include session cookies in requests to Reveal backend (which causes the "withCredentials" flag in jQuery.ajax to be turned on):
```javascript
$.ig.RevealSdkSettings.requestWithCredentialsFlag = true;
```
- Improved the "save" event to inform if the action is "saveAs" or a new dashboard is being saved, also allows to set the assigned name and dashboard id. Here sample code that handles the save event and asks for a new name when the dashboard is new or the action is "save as":
```javascript
view.onSave = function(rv, saveEvent) {
  if (saveEvent.saveAs || saveEvent.isNew) {
    var name = prompt("Dashboard name: ", saveEvent.name);
    if (name != null) {
      saveEvent.name = name;
      saveEvent.dashboardId = RevealApi.RevealUtility.generateUID();
    } else {
      return;
    }
  }
  saveEvent.saveFinished();
};
```
- Bug fixes:
  - The first chart in the dashboard will be exported even if you select another chart.
  - Data Stores get displayed automatically in the data source navigator even when they haven't been passed through DataSourcesRequestedCallback.
  - Fixed issue with MS SQL Server connections using instance name instead of port number.
  - Fixed list of date formats available for DateTime data type.
  - Added two new date formats for Date, DateTime and Day level aggregations including day of the week in short format.


## [1.0.3] - 2021-05-06
- [JS Files](https://revealpackages.eastus.cloudapp.azure.com/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.3/reveal-sdk-distribution-1.0.3-js.zip)
- Added Snowflake connector, including support for data blending between tables in the same Snowflake database.
- The Reveal BI Engine now supports the same limits supported in other platforms.
  - This is to avoid the server to crash if a user creates a visualization that requires too much data to be sent back to the client.
  - There are a few new properties in InitializeParameterBuilder to control this: maxInMemoryCells, maxStorageCells, maxStringCellSize, maxTotalStringSize.

## [1.0.2] - 2021-04-16
- [JS Files](https://revealpackages.eastus.cloudapp.azure.com/repository/public/com/infragistics/reveal/sdk/reveal-sdk-distribution/1.0.2/reveal-sdk-distribution-1.0.2-js.zip)
- Fixed an issue with the trial watermark displayed in some cases, even when a valid license is set.

## [1.0.1] - 2021-04-08
- Added a new subtitle property to data sources in JS API that (if present) replaces the connection information in the UI.

## [1.0.0] - 2021-04-05

- Added server-side Data Blending for Redshift, Postgres and MS SQL Server
- Javadoc files uploaded to Maven so IDEs like Eclipse can show the documentation when Intellisense is showing information about a given class or methods

## [0.9.7] - 2021-03-19

- Fixed JS files included in the Maven distribution
  - Maven's distribution plugin was using a cache and thus distributing the JS files from the previous release. JS files were correct in the sample projects, but not in the ZIP file uploaded to Maven repo.

## [0.9.6] - 2021-03-18

- Added an optional schema attribute to Postgres and Redshift data sources, list of tables and views will be filtered by the specified schema.

## [0.9.5] - 2021-03-15

- Switched to Maven distribution, you can read more about this in [README.md](README.md)
- Fixed Export feature issues, added documentation about the needed configuration
- Updated JS libraries to latest stable release
