package com.base.model;

import com.base.model.core.Table;
import com.base.model.base.BaseJRoute;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: JRoute.java
 * @包名: com.base.model
 * @描述: TODO
 * @所属: 华夏九鼎
 * @日期: 2018年1月29日 上午9:44:24
 * @版本: V1.0
 * @创建人：zY
 * @修改人：zY
 * @版权: 2018 hxjd Inc. All rights reserved.
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@Table(tableName="route",primaryKey="id")
public class JRoute extends BaseJRoute<JRoute> {
    //线路
	public static final int LINE_WAY = 0;
	//工点
	public static final int WORK_SITE = 1;
	//盾构机
	public static final int MACHINE = 2;

}
