package io.revealbi.samples.mybi.dashboards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSourcesInfo {
	private boolean showDataSourcesInDashboard;
	private List<Map<String, Object>> dataSources;
	private List<Map<String, Object>> dataSourceItems;
	
	public DataSourcesInfo() {			
		showDataSourcesInDashboard = true;
		dataSources = new ArrayList<>();
		dataSourceItems = new ArrayList<>();
	}

	public boolean isShowDataSourcesInDashboard() {
		return showDataSourcesInDashboard;
	}

	public void setShowDataSourcesInDashboard(boolean showDataSourcesInDashboard) {
		this.showDataSourcesInDashboard = showDataSourcesInDashboard;
	}

	public List<Map<String, Object>> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<Map<String, Object>> dataSources) {
		this.dataSources = dataSources;
	}

	public List<Map<String, Object>> getDataSourceItems() {
		return dataSourceItems;
	}

	public void setDataSourceItems(List<Map<String, Object>> dataSourceItems) {
		this.dataSourceItems = dataSourceItems;
	}

}
