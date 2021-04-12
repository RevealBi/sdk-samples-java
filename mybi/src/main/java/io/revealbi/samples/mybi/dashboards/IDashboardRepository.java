package io.revealbi.samples.mybi.dashboards;

import java.io.IOException;

import com.infragistics.reveal.sdk.api.IRVDashboardProvider;

public interface IDashboardRepository extends IRVDashboardProvider {
	DashboardInfo[] getUserDashboards(String userId) throws IOException;
	void deleteDashboard(String userId, String dashboardId) throws IOException;
}
