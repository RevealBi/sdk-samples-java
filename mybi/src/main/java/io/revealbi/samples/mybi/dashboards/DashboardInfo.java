package io.revealbi.samples.mybi.dashboards;

import java.util.Map;

public class DashboardInfo {
	private String id;
	private Map<String, Object> info;
	
	public DashboardInfo() {			
	}
	
	public DashboardInfo(String id, Map<String, Object> info) {
		this.id = id;
		this.info = info;
	}
		
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
