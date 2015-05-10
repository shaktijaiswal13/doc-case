package com.doccase.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.doccase.db.DBConnectionFactory;
import com.doccase.domain.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DocumentDAO {

	public String saveDocument(Document document) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getCollection("documentCollection");
		BasicDBObject obj = new BasicDBObject("name", document.getName())
				.append("scanned", document.getScanned())
				.append("url", document.getUrl())
				.append("coloured", document.getColoured())
				.append("description", document.getDescription())
				.append("signed", document.getSigned())
				.append("type", document.getType())
				.append("label", document.getLabel());
		collection.insert(obj);
		System.out.println("document saved with id "
				+ String.valueOf(obj.get("_id")));
		return String.valueOf(obj.get("_id"));
	}

	public List<Document> retrieveDocuments() {
		List<Document> documentList = new ArrayList<>();
		DBCursor cursor = DBConnectionFactory.getSharedFactory()
				.getCollection("documentCollection").find();
		while (cursor.hasNext()) {
			DBObject obj = (DBObject) cursor.next();
			Document document = new Document();
			document.setId(((ObjectId) obj.get("_id")).toString());
			document.setName((String) obj.get("name"));
			document.setScanned((String) obj.get("scanned"));
			document.setUrl((String) obj.get("url"));
			document.setColoured((String) obj.get("coloured"));
			document.setDescription((String) obj.get("description"));
			document.setSigned((String) obj.get("signed"));
			document.setType((String) obj.get("type"));
			// document.setLabel(obj.get("label"));
			documentList.add(document);
		}
		return documentList;
	}

}
