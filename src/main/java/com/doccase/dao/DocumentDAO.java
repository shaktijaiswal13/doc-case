package com.doccase.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.doccase.db.DBConnectionFactory;
import com.doccase.domain.Document;
import com.doccase.service.LabelService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DocumentDAO {

	FileDAO fileDAO = new FileDAO();
	
LabelService labelService=new LabelService();
	public String saveDocument(Document document) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getDocumentCollection();
		BasicDBObject obj = new BasicDBObject("name", document.getName())
				.append("url", document.getUrl())
				.append("description", document.getDescription())
				.append("type", document.getType())
				.append("labels", document.getLabels());
		collection.insert(obj);
		List<String> savedLabelIds = labelService.saveLabels(document.getLabels());
		System.out.println("document saved with id "
				+ String.valueOf(obj.get("_id")));
		return String.valueOf(obj.get("_id"));
	}

	public List<Document> retrieveDocuments(String query) {
		List<Document> documentList = new ArrayList<>();
		DBCursor cursor;

		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getDocumentCollection();

		if (query != null && !query.isEmpty()) {
			cursor = collection.find(new BasicDBObject("$text",
					new BasicDBObject("$search", query)));
		} else {
			cursor = collection.find();
		}
		while (cursor.hasNext()) {
			DBObject obj = (DBObject) cursor.next();
			Document document = new Document();
			document.setId(((ObjectId) obj.get("_id")).toString());
			document.setName((String) obj.get("name"));
			document.setUrl((String) obj.get("url"));
			document.setDescription((String) obj.get("description"));
			document.setType((String) obj.get("type"));
			 document.setLabels((List<String>)obj.get("labels"));
			documentList.add(document);
		}
		return documentList;
	}

	public int deleteDocument(String id) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getDocumentCollection();

		BasicDBObject obj = new BasicDBObject("_id", new ObjectId(id));
		DBCursor cursor = collection.find(obj);
		DBObject dbObj = (DBObject) cursor.next();
		String url = (String) dbObj.get("url");

		String fileId = url.substring(url.lastIndexOf('/') + 1, url.length());
		int fileDeleted = fileDAO.deleteFile(fileId);
		System.out.println("fileDeleted " + fileDeleted);
		int documentDeleted = 0;
		if (fileDeleted > 0) {
			WriteResult result = collection.remove(obj);
			documentDeleted = result.getN();
		}
		System.out.println("documentDeleted " + documentDeleted);
		return documentDeleted;
	}

}
