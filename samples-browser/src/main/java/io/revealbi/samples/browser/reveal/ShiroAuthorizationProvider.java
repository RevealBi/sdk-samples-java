package io.revealbi.samples.browser.reveal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import io.revealbi.sdk.ext.api.IAuthorizationProvider;
import io.revealbi.sdk.ext.auth.BaseAuthorizationProvider;

public class ShiroAuthorizationProvider extends BaseAuthorizationProvider {
	static final String DASHBOARDS_LIST_PERMISSION = "dashboards:list";
	static final String DASHBOARD_READ_PERMISSION_PREFIX = "dashboard:read:";	

	private boolean allowAnonymousReadAccess;
	public ShiroAuthorizationProvider(boolean allowAnonymousReadAccess) {
		this.allowAnonymousReadAccess = allowAnonymousReadAccess;
	}
	
	private boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || !subject.isAuthenticated()) {
			return allowAnonymousReadAccess && isReadPermission(permission); 
		}
		return subject.isPermitted(permission);
	}

	private static boolean isReadPermission(String permission) {
		return permission.startsWith(DASHBOARD_READ_PERMISSION_PREFIX) || permission.equals(DASHBOARDS_LIST_PERMISSION);
	}

	@Override
	public boolean hasDashboardsPermission(String userId, DashboardsActionType action) {
		return hasPermission(getDashboardsActionPermission(action));
	}

	@Override
	public boolean hasDashboardPermission(String userId, String dashboardId, DashboardActionType action) {
		return hasPermission(getDashboardActionPermission(dashboardId, action));
	}
	
	private static String getDashboardActionPermission(String dashboardId, DashboardActionType action) {
		return "dashboard:" + getDashboardActionPermissionText(action) + ":" + dashboardId;
	}
	
	private static String getDashboardActionPermissionText(DashboardActionType action) {
		return action.name().toLowerCase();
	}
	
	private static String getDashboardsActionPermission(DashboardsActionType action) {
		return "dashboards:" + getDashboardsActionPermissionText(action);
	}
	
	private static String getDashboardsActionPermissionText(DashboardsActionType action) {
		return action.name().toLowerCase();
	}

}
