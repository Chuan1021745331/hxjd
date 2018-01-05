package com.base.query;

import com.base.service.BaseService;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.plugin.activerecord.Record;

public class MongoDBService extends BaseService {
	
	public static void save(Record record){
		MongoQuery query = new MongoQuery();
		query.use("test").set(record).save();
	}
	
	public static long getCount(){
		MongoQuery query = new MongoQuery();
		return query.use("test").count();
	}
	
	public static void getList4Time(String t){
		MongoQuery query = new MongoQuery();
		MongoQuery list = query.use("test").eq("t", t);
		long i = 0;
//		while (list.hasNext()) {
//			DBObject l = list.next();
//			i++;
//			System.out.println(l.get("_id").toString());
//		}
		System.out.println(i);
	}
}
