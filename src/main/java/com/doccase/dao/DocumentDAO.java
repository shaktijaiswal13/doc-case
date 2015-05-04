package com.doccase.dao;

import java.net.UnknownHostException;

import com.doccase.domain.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class DocumentDAO {

	private Mongo mongo;
	private DB db;
	private DBCollection coll;

	public DocumentDAO() {
		try {
			mongo = new MongoClient();
			db = mongo.getDB("test");
			coll = db.getCollection("testCollection");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void saveDocument(Document document) {
		coll.insert(new BasicDBObject("name", document.getName()).append(
				"data", document.getData()));
	}

	public Document retrieveDocument(String docName) {
		DBCursor cursor = coll.find(new BasicDBObject("name", docName));
		DBObject obj = (DBObject) cursor.next();
		byte[] docData = (byte[]) obj.get("data");
		Document document = new Document();
		document.setName(docName);
		document.setData(docData);
		return document;
	}

}
