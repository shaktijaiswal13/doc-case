package com.doccase.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

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
			@Context HttpServletRequest request,
			@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		String fileName = contentDispositionHeader.getFileName();
		System.out.println("the file tyep is " + request.getContentType());
		int fileSize = request.getContentLength();
		System.out.println("file named " + fileName + " has size " + fileSize);

		byte fileData[] = new byte[fileSize];
		int byteRead = 0;
		int totalBytesRead = 0;
		while (totalBytesRead < fileSize) {
			try {
				byteRead = file.read(fileData, totalBytesRead, fileSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			totalBytesRead += byteRead;
		}

		System.out.println("filedata.. " + fileData);
		Document document = new Document();
		document.setName(fileName);
		document.setData(fileData);

		documentService.saveDocument(document);

		System.out.println("document saved...");

		return Response.status(Status.OK).entity("Document saved...").build();
	}

	@Path("retrieve")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Document> retrieveDocuments() {
		List<Document> documentList = documentService.retrieveDocuments();
		return documentList;
	}

	@Path("retrieve/{id}")
	@GET
	public Response retrieveDocument(@PathParam("id") String id) {

		final Document document = documentService.retrieveDocument(id);

		StreamingOutput st = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException {
				try {
					os.write(document.getData());
					os.close();
				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};

		ResponseBuilder response = Response.status(Status.OK);

		String docName = document.getName();
		String[] docNameArray = docName.split("/.");
		if (docNameArray[1].equals("jpg")) {
			response.header("content-type", "image/jpg");
		}
		response.header("content-disposition",
				"inline; filename=" + document.getName());

		return response.entity(st).build();
	}
}
