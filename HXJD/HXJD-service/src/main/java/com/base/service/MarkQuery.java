package com.base.service;


import com.base.model.JMark;

import java.util.List;

/**
 * Created by 104 on 2017/5/26.
 */
public class MarkQuery {
    protected static final JMark DAO = new JMark();
    private static final MarkQuery QUERY = new MarkQuery();

    public static MarkQuery me() {
        return QUERY;
    }

    public JMark findById(final Integer markId) {
        return DAO.findById(markId);
    }
    public List<JMark> getMarkAll(){
        return DAO.find("select * from j_mark order by id asc");
    }

}
