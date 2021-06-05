package io.revealbi.sdk.samples.cookiesauth;


import java.io.IOException;
import java.io.InputStream;

import com.infragistics.reveal.sdk.api.IRVDashboardProvider;

public class SampleDashboardProvider implements IRVDashboardProvider {

	@Override
	public InputStream getDashboard(String userId, String dashboardId) throws IOException {
		return getClass().getResourceAsStream("/" + dashboardId + ".rdash");
	}	

	@Override
	public void saveDashboard(String userId, String dashboardId, InputStream dashboardStream) throws IOException {
		throw new IOException("Not implemented");
	}

}
