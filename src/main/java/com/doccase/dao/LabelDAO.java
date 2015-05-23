package com.doccase.dao;

import java.util.ArrayList;
import java.util.List;

import com.doccase.db.DBConnectionFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class LabelDAO {

	public List<String> saveLabels(List<String> labels) {
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getLabelCollection();
		List<String> savedIds = new ArrayList<String>();
		for (String label : labels) {
			BasicDBObject obj = new BasicDBObject("name", label);
			collection.insert(obj);
			String id = String.valueOf(obj.get("_id"));
			System.out.println("Label saved with id " + id);
			savedIds.add(id);
		}
		return savedIds;
	}

	public List<String> retrieveLabels() {
		List<String> labelList = new ArrayList<>();
		DBCollection collection = DBConnectionFactory.getSharedFactory()
				.getLabelCollection();
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject obj = (DBObject) cursor.next();
			labelList.add((String) obj.get("name"));
		}
		return labelList;
	}

	public int deleteLabel(String label) {
		DBCollection labelCollection = DBConnectionFactory.getSharedFactory()
				.getLabelCollection();
		BasicDBObject obj = new BasicDBObject("name", label);
		DBCursor cursor = labelCollection.find(obj);
		DBObject dbObj = (DBObject) cursor.next();
		String retrieveLabel = (String) dbObj.get("name");
		WriteResult result = labelCollection.remove(obj);
		int documentDeleted = result.getN();
		System.out.println(retrieveLabel + " label deleted " + documentDeleted);
		return documentDeleted;
	}

}
