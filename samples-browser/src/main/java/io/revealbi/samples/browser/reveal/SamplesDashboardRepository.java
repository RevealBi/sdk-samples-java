package io.revealbi.samples.browser.reveal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import io.revealbi.sdk.ext.api.DashboardInfo;
import io.revealbi.sdk.ext.fs.FileSystemDashboardRepository;

public class SamplesDashboardRepository extends FileSystemDashboardRepository {

	public SamplesDashboardRepository(String rootDir, boolean personal) {
		super(rootDir, personal);
	}

	@Override
	public void deleteDashboard(String userId, String dashboardId) throws IOException {
		super.deleteDashboard(userId, dashboardId);
		TagsService.getInstance().dashboardDeleted(dashboardId);
	}	
	
	@Override
	public DashboardInfo[] getUserDashboards(String userId) throws IOException {		
		DashboardInfo[] dashboards = super.getUserDashboards(userId);
		if (dashboards != null) {
			for (DashboardInfo d : dashboards) {				
				Set<String> tags = TagsService.getInstance().getDashboardTags(d.getId());
				if (tags != null) {
					d.getInfo().put("DashboardTags", new ArrayList<String>(tags));
				}
			}
		}
		return dashboards;
	}
}
