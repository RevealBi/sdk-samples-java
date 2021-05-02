package io.revealbi.samples.mybi.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.infragistics.reveal.sdk.api.model.RVDashboardDocument;
import com.infragistics.reveal.sdk.util.RVSerializationUtilities;

import io.revealbi.samples.mybi.dashboards.DashboardInfo;
import io.revealbi.samples.mybi.dashboards.DashboardRepositoryFactory;

@Path("/dashboards")
public class DashboardsResource {
	@Context
	private ContainerRequestContext requestContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DashboardInfo[] getDashboards() throws IOException {
		return DashboardRepositoryFactory.getInstance().getUserDashboards(getUserId());
	}
	
	@DELETE	
	@Path("/{dashboardId}")
	public void deleteDashboard(@PathParam("dashboardId") String dashboardId) throws IOException {
		DashboardRepositoryFactory.getInstance().deleteDashboard(getUserId(), dashboardId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{dashboardId}")
	public Map<String, Object> getDashboardJson(@PathParam("dashboardId") String dashboardId) throws IOException {
		InputStream rdashStream = DashboardRepositoryFactory.getInstance().getDashboard(getUserId(), dashboardId);
		if (rdashStream != null) {
			RVDashboardDocument doc = RVSerializationUtilities.getDashboardDocument(rdashStream);
			if (doc != null) {
				return doc.toJson();
			}
		}
		throw new WebApplicationException(Response.Status.NOT_FOUND);
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/export/{dashboardId}")
	public Response getDashboard(@PathParam("dashboardId") String dashboardId) throws IOException {
		final InputStream rdashStream = DashboardRepositoryFactory.getInstance().getDashboard(getUserId(), dashboardId);
		if (rdashStream != null) {
			StreamingOutput output = new StreamingOutput() {				
				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException {
					RVSerializationUtilities.copyStream(rdashStream, output);
				}
			};
			return Response.
					ok(output, MediaType.APPLICATION_OCTET_STREAM).
					header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dashboardId + ".rdash").
					build();
		}
		throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/upload")
	public Response uploadDashboards(@FormDataParam("files") List<FormDataBodyPart> files) throws IOException, ServletException {
		for (FormDataBodyPart file : files) {
			FormDataContentDisposition disposition = file.getFormDataContentDisposition();
			String fileName = disposition.getFileName();
			if (fileName == null || !fileName.endsWith(".rdash")) {
				continue;
			}
			String dashboardId = fileName.substring(0, fileName.length() - ".rdash".length());
			try (InputStream inputStream = file.getValueAs(InputStream.class)) {
				DashboardRepositoryFactory.getInstance().saveDashboard(getUserId(), dashboardId, inputStream);
			}
		}
		return Response.ok().build();
	}
	
	private String getUserId() {
		return UserIdProvider.getUserId(requestContext);
	}
}
