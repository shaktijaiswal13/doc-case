package com.doccase.dao;

import java.net.UnknownHostException;

import com.doccase.domain.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
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

}
