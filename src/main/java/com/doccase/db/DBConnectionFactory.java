package com.doccase.db;

import java.net.UnknownHostException;

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
	}

	public DBCollection getCollection(String collectionName) {
		DB db = client.getDB("test");
		DBCollection coll = db.getCollection(collectionName);
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
