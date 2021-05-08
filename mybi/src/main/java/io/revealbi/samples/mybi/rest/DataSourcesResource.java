package io.revealbi.samples.mybi.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.revealbi.samples.mybi.dashboards.DataSourcesInfo;
import io.revealbi.samples.mybi.dashboards.DataSourcesRepositoryFactory;

@Path("/dataSources")
public class DataSourcesResource extends BaseResource {	
	@GET
	@Produces("application/json")
	public DataSourcesInfo getDataSources() throws IOException {
		return DataSourcesRepositoryFactory.getInstance().getUserDataSources(getUserId());
	}
}
