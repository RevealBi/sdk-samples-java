package io.revealbi.samples.mybi.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

import io.revealbi.samples.mybi.dashboards.DashboardInfo;
import io.revealbi.samples.mybi.dashboards.DashboardRepositoryFactory;

@Path("/dashboards")
public class DashboardsResource {
	@Context
	private ContainerRequestContext requestContext;
	
	@GET
	@Produces("application/json")
	public DashboardInfo[] getDashboards() throws IOException {
		return DashboardRepositoryFactory.getInstance().getUserDashboards(getUserId());
	}
	
	private String getUserId() {
		return UserIdProvider.getUserId(requestContext);
	}
}
