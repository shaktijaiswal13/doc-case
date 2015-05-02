package com.doccase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.types.Binary;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * Hello world!
 * 
 */
public class App {

	private static final Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		try {
			MongoClient mongo = new MongoClient(new ServerAddress("localhost",
					27017));
			logger.log(Level.INFO, "Connected to MongoDB");

			DB testDb = mongo.getDB("test");
			logger.log(Level.INFO, "Connected to database {0}", "test");

			DBCollection testColl = testDb.getCollection("testCollection");
			System.out.println(testDb.getCollectionNames());
			System.out.println(testColl.count());

			// testColl.insert(new BasicDBObject("name","Neha").append("age",
			// 28));
			// testColl.insert(new BasicDBObject("name","Shakti").append("age",
			// 28));

			System.out.println(testColl.getCount());
			System.out.println(testColl.findOne());

			insertImage(testColl);

			retrieveImage(testColl);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private static void retrieveImage(DBCollection coll) {
		DBCursor cursor = coll.find(new BasicDBObject("name", "test image"));
		DBObject obj = (DBObject) cursor.next();
		byte[] imageData = (byte[]) obj.get("data");
		try (FileOutputStream os = new FileOutputStream(new File(
				"/Users/shaktikumar/Desktop/mongoDBImage.jpg"))) {
			os.write(imageData);
			logger.log(Level.INFO, "Image retrieved");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void insertImage(DBCollection coll) {
		File imageFile = new File("/Users/shaktikumar/Desktop/mehndi.jpg");

		try (FileInputStream in = new FileInputStream(imageFile)) {
			byte[] buffer = new byte[in.available()];
			in.read(buffer);

			Binary data = new Binary(buffer);
			coll.insert(new BasicDBObject("name", "test image").append("data",
					data));

			System.out.println(coll.getCount());
			logger.log(Level.INFO, "Image saved");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
