package com.pany.analytics.upmedia.reveal;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import io.revealbi.sdk.ext.base.BaseDashboardRepository;

/**
 * Dashboard provider implementation for Upmedia sample, we're extending BaseDashboardRepository in 
 * order to return the list of dashboards and display them using thumbnails (preview icons).
 * This implementation doesn't support saving or deleting dashboards.
 */
public class UpmediaDashboardProvider extends BaseDashboardRepository {
	private static Logger log = Logger.getLogger(UpmediaDashboardProvider.class.getName());

	@Override
	protected String[] getUserDashboardIds(String arg0) throws IOException {
		return new String[] {
			"Campaigns",
			"Manufacturing",
			"Marketing",
			"Sales"
		};
	}
	
	@Override
	public InputStream getDashboard(String userId, String dashboardId) throws IOException {
		InputStream dashboardStream = getClass().getResourceAsStream("/" + dashboardId + ".rdash");
		if (dashboardStream == null) {
			log.warning("Dashboard not found: " + dashboardId);
		}
		return dashboardStream;
	}	

	@Override
	public void saveDashboard(String userId, String dashboardId, InputStream dashboardStream) throws IOException {
		throw new IOException("Not implemented");
	}

	@Override
	public void deleteDashboard(String userId, String dashboardId) throws IOException {
		throw new IOException("Not implemented");
	}
}
