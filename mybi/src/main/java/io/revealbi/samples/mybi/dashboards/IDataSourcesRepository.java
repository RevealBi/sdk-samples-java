package io.revealbi.samples.mybi.dashboards;

import java.io.IOException;

public interface IDataSourcesRepository {
	DataSourcesInfo getUserDataSources(String userId) throws IOException;
}
