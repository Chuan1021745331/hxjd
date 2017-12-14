package com.base.service;

import com.base.model.JOffuser;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

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
public class OffUserQuery extends JBaseQuery {
    protected static final JOffuser DAO=new JOffuser();
    private static final OffUserQuery QUERY=new OffUserQuery();
    public static OffUserQuery me() {
        return QUERY;
    }
    public JOffuser findById(final Integer terminalId) {
        return DAO.findById(terminalId);
    }

    /**
     * 保存离线消息到离线表，如果有记录，则将离线消息id追加在原有的基础上
     * @param jOffuser
     * @return
     */
    public boolean updateAndSave(JOffuser jOffuser){
        JOffuser offuser=DAO.findFirst("SELECT * FROM j_offuser WHERE mediatorId=? and groupId=?",jOffuser.getMediatorId(),jOffuser.getGroupId());
        if(offuser!=null){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(offuser.getMsgId()+"|"+jOffuser.getMsgId());
            offuser.setMsgId(stringBuilder.toString());
            offuser.update();
            return true;
        }else {
            return jOffuser.save();
            //保存
        }
    }

    /**
     * 通过mediatorId查询离线的用户
     * @param mediatorId
     * @return
     */
    public List<JOffuser> GainOffUserByMedatorId(int mediatorId){
        return DAO.find("SELECT * FROM j_offuser WHERE mediatorId=?",mediatorId);
    }
    public int delByMedid(String medId){
        return DAO.doDelete("mediatorId=?",medId);
    }
    /**
     * 删除离线用户
     * @param mediatorId
     * @param groupId
     * @return
     */
    public boolean delByMediatorIdandGroupId(int mediatorId,int groupId){
        int res = DAO.doDelete("mediatorId=? and groupId=?",mediatorId,groupId);
        if(res!=0){
            return true;
        }
        return false;
    }
    public List<JOffuser> selectAll(){
        return DAO.find("select * from j_offuser");
    }
    public List<JOffuser> getByMediatorId(int id){
    	return DAO.find("select * from j_offuser where mediatorId = ? ",id);
    }
    
}
