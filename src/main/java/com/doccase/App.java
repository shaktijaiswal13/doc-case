package com.doccase;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		try {
			MongoClient mongoDb = new MongoClient(new ServerAddress("localhost",
					27017));
			DB testDb = mongoDb.getDB("test");
			System.out.println(testDb.getCollectionNames());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
