package io.revealbi.samples.studio.reveal;

import io.revealbi.sdk.ext.auth.BaseAuthorizationProvider;

/**
 * Be careful if you use this implementation for production applications, as it will allow all users to read and write dashboards without checking permissions.
 */
public class AllowAllAuthorizationProvider extends BaseAuthorizationProvider {

	@Override
	public boolean hasDashboardsPermission(String userId, DashboardsActionType action) {
		return true;
	}

	@Override
	public boolean hasDashboardPermission(String userId, String dashboardId, DashboardActionType action) {
		return true;
	}

}
