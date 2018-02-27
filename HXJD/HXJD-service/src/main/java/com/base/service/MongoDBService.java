package com.base.service;

import com.base.service.BaseService;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * 
 * @类名: MongoDBService.java
 * @包名: com.base.service
 * @描述: mongodb测试service
 * @所属: 华夏九鼎
 * @日期: 2018年1月24日 上午9:45:24
 * @版本: V1.0
 * @创建人：Administrator
 * @修改人：Administrator
 * @版权: 2018 hxjd Inc. All rights reserved.
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class MongoDBService extends BaseService {
	private final static Logger logger = LoggerFactory.getLogger(MongoDBService.class);

	public static void save(String collection,Object o){
		MongoQuery query = new MongoQuery();
		query.use(collection).set(o).save();
	}

	public static void save(Record record) {
		MongoQuery query = new MongoQuery();
		query.use("test").set(record).save();
	}

	public static long getCount() {
		MongoQuery query = new MongoQuery();
		return query.use("test").count();
	}

	public static void getList4Time(String t) {
		MongoQuery query = new MongoQuery();
		MongoQuery list = query.use("test").eq("t", t);
		long i = 0;
		// while (list.hasNext()) {
		// DBObject l = list.next();
		// i++;
		// logger.info(l.get("_id").toString());
		// }
		logger.info("" + i);
	}
}
