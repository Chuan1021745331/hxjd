package com.base.model;

import com.base.model.base.BaseJJob;
import com.base.model.core.Table;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: JJob.java
 * @包名: com.base.model
 * @描述: TODO
 * @所属: 华夏九鼎
 * @日期: 2018年1月29日 上午9:43:38
 * @版本: V1.0
 * @创建人：zY
 * @修改人：zY
 * @版权: 2018 hxjd Inc. All rights reserved.
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@Table(tableName="job",primaryKey="id")
public class JJob extends BaseJJob<JJob> {
	private static final long serialVersionUID = 1L;

}
