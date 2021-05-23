package io.revealbi.samples.browser.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import io.revealbi.samples.browser.reveal.TagsService;
import io.revealbi.samples.browser.reveal.TagsService.TagDefinition;
import io.revealbi.sdk.ext.rest.BaseResource;
import io.revealbi.sdk.ext.rest.UserIdProvider;

@Path("/tags")
public class TagsResource extends BaseResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TagDefinition> getTags() {
		return TagsService.getInstance().getTags();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{dashboardId}")
	public TagsList getDashboardTags(@PathParam("dashboardId") String dashboardId) throws IOException {
		Set<String> tags = TagsService.getInstance().getDashboardTags(dashboardId);
		return new TagsList(new ArrayList<String>(tags));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{dashboardId}")
	public void setDashboardTags(@PathParam("dashboardId") String dashboardId, TagsList tags) throws IOException {
		if (UserIdProvider.getUserId(requestContext) == null) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}
		Set<String> tagsSet = tags.tags == null ? new HashSet<String>() : new HashSet<String>(tags.tags);
		TagsService.getInstance().setDashboardTags(dashboardId, tagsSet);
	}
	
	public static class TagsList {
		public List<String> tags;
	
		public TagsList(List<String> tags) {
			this.tags = tags;
		}
	}
}
