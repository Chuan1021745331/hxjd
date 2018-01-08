package com.base.service;

import com.base.service.BaseService;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBService extends BaseService {
	private final static Logger logger= LoggerFactory.getLogger(MongoDBService.class);

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
//			logger.info(l.get("_id").toString());
//		}
		logger.info(""+i);
	}
}
