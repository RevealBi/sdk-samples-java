# Changelog

This is actually acting as a Changelog for RevealBI Java SDK for now.

## [0.9.7] - 2021-03-19

- Fixed JS files included in the Maven distribution
  - Maven's distribution plugin was using a cache and thus distributing the JS files from the previous release. JS files were correct in the sample projects, but not in the ZIP file uploaded to Maven repo.

## [0.9.6] - 2021-03-18

- Added an optional schema attribute to Postgres and Redshift data sources, list of tables and views will be filtered by the specified schema.

## [0.9.5] - 2021-03-15

- Switched to Maven distribution, you can read more about this in [README.md](README.md)
- Fixed Export feature issues, added documentation about the needed configuration
- Updated JS libraries to latest stable release
