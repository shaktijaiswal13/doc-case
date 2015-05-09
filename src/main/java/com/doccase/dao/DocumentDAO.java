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

	public void saveDocument(Document document) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getCollection("testCollection");
		collection.insert(new BasicDBObject("name", document.getName()).append(
				"data", document.getData()));
	}

	public Document retrieveDocument(String id) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getCollection("testCollection");
		DBCursor cursor = collection.find(new BasicDBObject("_id",
				new ObjectId(id)));
		DBObject obj = (DBObject) cursor.next();
		String docName = (String) obj.get("name");
		byte[] docData = (byte[]) obj.get("data");
		Document document = new Document();
		document.setName(docName);
		document.setData(docData);
		return document;
	}

	public List<Document> retrieveDocuments() {
		List<Document> documentList = new ArrayList<>();
		DBCursor cursor = DBConnectionFactory.getSharedFactory().getCollection("testCollection").find();
		while (cursor.hasNext()) {
			DBObject obj = (DBObject) cursor.next();
			ObjectId _id = (ObjectId) obj.get("_id");
			String docName = (String) obj.get("name");
			// byte[] docData = (byte[]) obj.get("data");
			Document document = new Document();
			document.set_id(_id.toString());
			document.setName(docName);
			// document.setData(docData);
			documentList.add(document);
		}
		return documentList;
	}


}
