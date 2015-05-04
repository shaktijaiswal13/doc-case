package com.doccase.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.doccase.domain.Document;
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

	@Path("upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(
			@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		String fileName = contentDispositionHeader.getFileName();
		byte[] fileData = null;

		try {
			fileData = new byte[file.available()];
			file.read(fileData);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Document document = new Document();
		document.setName(fileName);
		document.setData(fileData);

		documentService.saveDocument(document);

		return Response.status(Status.OK).entity("Document saved...").build();
	}
}
