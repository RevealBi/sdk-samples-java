# Changelog

This is actually acting as a Changelog for RevealBI Java SDK for now.

## [1.0.3] - 2021-05-06
- Added Snowflake connector, including support for data blending between tables in the same Snowflake database.
- The Reveal BI Engine now supports the same limits supported in other platforms.
  - This is to avoid the server to crash if a user creates a visualization that requires too much data to be sent back to the client.
  - There are a few new properties in InitializeParameterBuilder to control this: maxInMemoryCells, maxStorageCells, maxStringCellSize, maxTotalStringSize.

## [1.0.2] - 2021-04-16
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
