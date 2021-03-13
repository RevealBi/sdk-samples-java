package com.pany.analytics.upmedia.reveal;


import com.infragistics.reveal.sdk.api.IRVAuthenticationProvider;
import com.infragistics.reveal.sdk.api.IRVDataSourceCredential;
import com.infragistics.reveal.sdk.api.RVUsernamePasswordDataSourceCredential;
import com.infragistics.reveal.sdk.api.model.RVDashboardDataSource;
import com.infragistics.reveal.sdk.api.model.RVSqlServerDataSource;

public class UpmediaAuthenticationProvider implements IRVAuthenticationProvider {
	@Override
	public IRVDataSourceCredential resolveCredentials(String userId, RVDashboardDataSource dataSource) {
		// Here's how returning credentials would look like, for a SqlServer data source:
		if (dataSource instanceof RVSqlServerDataSource) {
			String host = ((RVSqlServerDataSource)dataSource).getHost();
			if (host != null && host.equals("10.10.10.10")) {
				return new RVUsernamePasswordDataSourceCredential("someuser", "somesecret", "somedomain");
			}
		} 
		return null;
	}

}
