package com.doccase.dao;

import org.bson.types.ObjectId;

import com.doccase.db.DBConnectionFactory;
import com.doccase.domain.File;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class FileDAO {

	public String saveFile(File file) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getFileCollection();
		BasicDBObject obj = new BasicDBObject("name", file.getName()).append(
				"data", file.getData());
		collection.insert(obj);
		System.out.println("file save with id " + obj.get("_id"));
		return String.valueOf(obj.get("_id"));
	}

	public File retrieveFile(String id) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getFileCollection();
		DBCursor cursor = collection.find(new BasicDBObject("_id",
				new ObjectId(id)));
		DBObject obj = (DBObject) cursor.next();
		String docName = (String) obj.get("name");
		byte[] docData = (byte[]) obj.get("data");
		File file = new File();
		file.setName(docName);
		file.setData(docData);
		return file;
	}

	public int deleteFile(String id) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getFileCollection();
		WriteResult result = collection.remove(new BasicDBObject("_id",
				new ObjectId(id)));
		return result.getN();
	}

}
