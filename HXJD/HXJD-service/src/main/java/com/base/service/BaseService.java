package com.base.service;

import java.util.Map.Entry;

import com.jfinal.plugin.activerecord.Record;
import com.mongodb.BasicDBObject;

public class BaseService {
	public static BasicDBObject toDbObject(Record record){
		BasicDBObject object = new BasicDBObject();
		for (Entry<String, Object> e : record.getColumns().entrySet()) {
			object.append(e.getKey(), e.getValue());
		}
		return object;
	}
}
