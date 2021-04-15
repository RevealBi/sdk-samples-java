package io.revealbi.samples.mybi.dashboards;

public class DataSourcesRepositoryFactory {
	private static IDataSourcesRepository instance;
	
	public static IDataSourcesRepository getInstance() {
		return instance;
	}
	
	public static void setInstance(IDataSourcesRepository instance) {
		DataSourcesRepositoryFactory.instance = instance;
	}
	
}
