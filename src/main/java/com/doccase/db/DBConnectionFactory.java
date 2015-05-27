package com.doccase.db;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * 
 * @author shakumar
 * 
 */
public class DBConnectionFactory {

	private static DBConnectionFactory INSTANCE;

	private MongoClient client;

	private DBConnectionFactory() throws UnknownHostException {
		client = new MongoClient();
		System.out.println("New mongo client is created..");
		DB db = client.getDB("test");
		DBCollection coll = db.getCollection("documentCollection");
		coll.createIndex(new BasicDBObject("name", "text").append(
				"description", "text").append("labels", "text"));
		System.out.println("index created..");

	}

	public DBCollection getDocumentCollection() {
		DB db = client.getDB("test");
		DBCollection coll = db.getCollection("documentCollection");
		return coll;
	}

	public DBCollection getLabelCollection() {
		DB db = client.getDB("test");
		DBCollection coll = db.getCollection("labelCollection");
		return coll;
	}

	public DBCollection getFileCollection() {
		DB db = client.getDB("test");
		DBCollection coll = db.getCollection("fileCollection");
		return coll;
	}

	public Mongo getClient() {
		return client;
	}

	public void closeConnection() {
		if (client != null) {
			client.close();
			System.out.println("Mongo client is closed..");
		}
	}

	public static DBConnectionFactory getSharedFactory() {
		if (INSTANCE == null) {
			synchronized (DBConnectionFactory.class) {
				if (INSTANCE == null) {
					try {
						INSTANCE = new DBConnectionFactory();
					} catch (UnknownHostException e) {
						e.printStackTrace();
						throw new RuntimeException(
								"Not able to connect to database. Cause: "
										+ e.getMessage());
					}
				}
			}
		}
		return INSTANCE;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		client.close();

	}
}
