package io.revealbi.samples.browser.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.revealbi.samples.browser.reveal.TagsService;
import io.revealbi.sdk.ext.api.DashboardInfo;

public class DashboardsResource extends io.revealbi.sdk.ext.rest.DashboardsResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tag/{tag}")
	public DashboardInfo[] getDashboardsWithTag(@PathParam("tag") String tag) throws IOException {
		return TagsService.getInstance().getDashboardsWithTag(getUserId(), tag);
	}	
}
