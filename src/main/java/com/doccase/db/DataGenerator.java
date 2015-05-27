package com.doccase.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DataGenerator {

	public static void main(String[] args) throws IOException {
		// Mongo client = DBConnectionFactory.getSharedFactory().getClient();
		final File folder = new File("/Users/shakumar/Desktop/mydoc");
		// "/Users/shakumar/projects/doc-case/src/main/resources/images");
		listFilesForFolder(folder);
	}

	private static void listFilesForFolder(final File folder)
			throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				DBCollection fileCollection = DBConnectionFactory
						.getSharedFactory().getFileCollection();
				DBCollection documentCollection = DBConnectionFactory
						.getSharedFactory().getDocumentCollection();
				String fullFileName = fileEntry.getName();
				String[] tokensWithDot = fullFileName.split("\\.");
				String tokenBeforeDot = tokensWithDot[0];
				String[] tokens = tokenBeforeDot.split(",");
				String name = tokens[0].replace(" ", "_");
				String description = tokens.length > 1 ? tokens[1] : tokens[0];
				List<String> labels = new ArrayList<String>();
				for (int i = 2; i < tokens.length; i++) {
					labels.add(tokens[i]);
				}
				System.out.println("name:" + name + ", description:"
						+ description);
				String newFileName = name + "." + tokensWithDot[1];
				Object fileId = saveFile(fileEntry, newFileName, fileCollection);
				System.out.println("file save with id " + fileId);
				Object docId = saveDocument(name, description, labels,
						documentCollection, fileId);
				System.out.println("document saved with id "
						+ String.valueOf(docId));
			}
		}
	}

	private static Object saveDocument(String name, String description,
			List<String> labels, DBCollection collection, Object fileId) {
		BasicDBObject docObj = new BasicDBObject("name", name)
				.append("url", "/rest/file/" + fileId)
				.append("description", description).append("labels", labels);
		collection.insert(docObj);
		Object docId = docObj.get("_id");
		return docId;
	}

	private static Object saveFile(final File fileEntry, String fileName,
			DBCollection collection) throws IOException {
		BasicDBObject filObj = new BasicDBObject("name", fileName).append(
				"data", convertToByteArray(fileEntry));
		collection.insert(filObj);
		Object fileId = filObj.get("_id");
		return fileId;
	}

	private static byte[] convertToByteArray(File file) throws IOException {
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		fileInputStream.close();
		return b;
	}

}
