package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JInstruction;

import java.util.LinkedList;
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
public class InstructionQuery extends JBaseQuery {
    protected static final JInstruction DAO = new JInstruction();
    private static final InstructionQuery QUERY = new InstructionQuery();

    public static InstructionQuery me() {
        return QUERY;
    }

    public JInstruction findById(final Integer InstructionId) {
        return DAO.findById(InstructionId);
    }

    public List<JInstruction> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from j_instruction i");
        String c = column == 0 ? "instructionType" : column == 1 ? "message" : "sendTime";
        sqlBuilder.append(" WHERE CONCAT ( IFNULL(instructionType, ''),IFNULL(message, ''),IFNULL(sendTime, '')) LIKE '%" + search + "%' ");
        sqlBuilder.append(" order by " + c + " " + order + " LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(page);
        params.add(pagesize);
        if (params.isEmpty()) {
            return DAO.find(sqlBuilder.toString());
        } else {
            return DAO.find(sqlBuilder.toString(), params.toArray());
        }
    }

    public Long findConunt(String search) {
        if (StringUtils.isEmpty(search)) {
            return DAO.doFindCount();
        } else {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" WHERE CONCAT( IFNULL(instructionType, ''),IFNULL(message, ''),IFNULL(sendTime, '')) LIKE '%" + search + "%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }
}
