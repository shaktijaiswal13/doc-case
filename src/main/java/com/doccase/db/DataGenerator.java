package com.doccase.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DataGenerator {
	private static Random random = new Random();

	public static void main(String[] args) throws IOException {
		// Mongo client = DBConnectionFactory.getSharedFactory().getClient();
		final File folder = new File(// "/Users/shakumar/Desktop/WallPaper1");
				"/Users/shakumar/projects/doc-case/src/main/resources/images");
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
				String fileNameWithoutExt = fullFileName.split("\\.")[0];
				String[] tokens = fileNameWithoutExt.replace("-", " ")
						.replace("_", " ").split(",");
				String name = tokens[0];
				String description = tokens.length > 1 ? tokens[1] : tokens[0];
				System.out.println("name:" + name + ", description:"
						+ description);
				Object fileId = saveFile(fileEntry, fileNameWithoutExt + "."
						+ fullFileName.split("\\.")[1], fileCollection);
				System.out.println("file save with id " + fileId);
				Object docId = saveDocument(name, description,
						documentCollection, fileId);
				System.out.println("document saved with id "
						+ String.valueOf(docId));
			}
		}
	}

	private static Object saveDocument(String name, String description,
			DBCollection collection, Object fileId) {
		BasicDBObject docObj = new BasicDBObject("name", name)
				.append("scanned", String.valueOf(random.nextBoolean()))
				.append("url", "/rest/file/" + fileId)
				.append("coloured", String.valueOf(random.nextBoolean()))
				.append("description", description)
				.append("signed", String.valueOf(random.nextBoolean()))
				.append("type", "jpeg").append("label", "");
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
