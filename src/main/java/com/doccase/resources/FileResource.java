package com.doccase.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import com.doccase.domain.File;
import com.doccase.domain.URL;
import com.doccase.service.FileService;

@Path("file")
public class FileResource {

	private FileService fileService = new FileService();

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveFileData(
			@Context HttpServletRequest request,
			@FormDataParam("file") InputStream fileStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		String fileName = contentDispositionHeader.getFileName();
		int fileSize = request.getContentLength();
		System.out.println("file named " + fileName + " has size " + fileSize);

		byte fileData[] = new byte[fileSize];
		int byteRead = 0;
		int totalBytesRead = 0;
		while (totalBytesRead < fileSize) {
			try {
				byteRead = fileStream.read(fileData, totalBytesRead, fileSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			totalBytesRead += byteRead;
		}

		System.out.println("filedata.. " + fileData);
		File file = new File();
		file.setName(fileName);
		file.setData(fileData);

		String id = fileService.saveFile(file);

		System.out.println("file saved...with id ..." + id);

		URL url = new URL();
		url.setUrl("/rest/file/" + id);

		return Response.status(Status.OK).entity(url).build();

	}

	@GET
	@Path("{id}/inline")
	public Response retrieveFileAsInline(@PathParam("id") String id) {

		final File file = fileService.retrieveFile(id);

		StreamingOutput st = getFileData(file);

		ResponseBuilder response = Response.status(Status.OK);

		String docName = file.getName();
		String[] docNameArray = docName.split("\\.");
		response.header("content-type", "image/" + docNameArray[1]);
		response.header("content-disposition",
				"inline; filename=" + file.getName());

		return response.entity(st).build();
	}

	private StreamingOutput getFileData(final File file) {
		StreamingOutput st = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException {
				try {
					os.write(file.getData());
					os.close();
				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};
		return st;
	}
}
