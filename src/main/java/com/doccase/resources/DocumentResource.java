package com.doccase.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.doccase.domain.Document;
import com.doccase.domain.Result;
import com.doccase.service.DocumentService;

@Path("/documents")
public class DocumentResource {

	private DocumentService documentService;

	public DocumentResource() {
		documentService = new DocumentService();
	}

	@Path("test")
	@GET
	public String test() {
		return "test completed...";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveDocuments(List<Document> documents) {

		List<String> documentIdsList = new ArrayList<>();
		for (Document document : documents) {
			documentIdsList.add(documentService.saveDocument(document));
		}

		return Response.status(Status.OK).entity(documentIdsList).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Document> retrieveDocuments(@QueryParam("query") String query) {

		List<Document> documentList = new ArrayList<>();

		documentList.addAll(documentService.retrieveDocuments(query));
		return documentList;
	}

	@DELETE
	@Path("{id}")
	public Response deleteDocument(@PathParam("id") String id) {

		int documentDeleted = documentService.deleteDocument(id);
		Result result = new Result();
		result.setId(id);

		if (documentDeleted > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return Response.status(Status.OK).entity(result).build();
	}
}
