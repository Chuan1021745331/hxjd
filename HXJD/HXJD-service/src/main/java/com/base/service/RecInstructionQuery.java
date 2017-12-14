package com.base.service;

import com.base.model.JRecinstruction;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/6/8 11:47
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class RecInstructionQuery extends JBaseQuery {
    protected static final JRecinstruction DAO=new JRecinstruction();
    private static final RecInstructionQuery QUERY = new RecInstructionQuery();
    public static  RecInstructionQuery me(){
        return QUERY;

    }
     public JRecinstruction findById(final Integer terminalId){
         return DAO.findById(terminalId);
     }
    /**
     * 接收者接收指令
     * @param jRecinstruction
     * @return
     */
    public boolean ReciviceSave(JRecinstruction jRecinstruction) {
        JRecinstruction recinstruction = DAO.findFirst("SELECT * FROM j_recinstruction WHERE mediatorId=?", jRecinstruction.getMediatorId());
        if (recinstruction != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(recinstruction.getMsgId() + "|" + jRecinstruction.getMsgId());
            recinstruction.setMsgId(stringBuilder.toString());
            recinstruction.update();
            return true;
        } else {
            return jRecinstruction.save();
        }
    }
}
