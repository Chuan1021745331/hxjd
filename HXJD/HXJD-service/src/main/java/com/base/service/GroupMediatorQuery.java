package com.base.service;

import java.util.List;

import com.base.model.JGroup;
import com.base.model.JGroupmediator;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 16:51
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class GroupMediatorQuery {
	
	protected static final JGroupmediator DAO = new JGroupmediator();
    private static final GroupMediatorQuery QUERY = new GroupMediatorQuery();
    public static GroupMediatorQuery me() {
        return QUERY;
    }
    
    public JGroupmediator findByTerminal(String sdnum){
    	return DAO.findFirst("select * from j_groupmediator gm join j_mediatorterminal mt on mt.mediator_id = gm.mediatorId join j_terminal t on t.id = mt.terminal_id where gm.groupId in (2,3) AND t.sdnum = ?", sdnum);
    }
}
