package io.revealbi.samples.mybi.rest;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

import io.revealbi.samples.mybi.dashboards.DashboardInfo;
import io.revealbi.samples.mybi.dashboards.DashboardRepositoryFactory;
import io.revealbi.samples.mybi.dashboards.DataSourcesInfo;
import io.revealbi.samples.mybi.dashboards.DataSourcesRepositoryFactory;

@Path("/dataSources")
public class DataSourcesResource {
	@Context
	private ContainerRequestContext requestContext;
	
	@GET
	@Produces("application/json")
	public DataSourcesInfo getDataSources() throws IOException {
		return DataSourcesRepositoryFactory.getInstance().getUserDataSources(getUserId());
	}
	
	private String getUserId() {
		return UserIdProvider.getUserId(requestContext);
	}
}
