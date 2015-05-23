package com.doccase.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.doccase.domain.Result;
import com.doccase.service.LabelService;

@Path("/labels")
public class LabelResource {

	private LabelService labelService;

	public LabelResource() {
		labelService = new LabelService();
	}

	@Path("test")
	@GET
	public String test() {
		return "test completed...";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveLabels(List<String> labels) {
		List<String> savedLabelIds = labelService.saveLabels(labels);
		return Response.status(Status.OK).entity(savedLabelIds).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> retrieveLabels() {
		return labelService.retrieveLabels();
	}

	@DELETE
	@Path("{label}")
	public Response deleteDocument(@PathParam("label") String label) {
		int labelDeleted = labelService.deleteLabel(label);
		Result result = new Result();
		if (labelDeleted > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return Response.status(Status.OK).entity(result).build();
	}
}
