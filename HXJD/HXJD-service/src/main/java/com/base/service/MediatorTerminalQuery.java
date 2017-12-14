package com.base.service;

import com.base.model.JMediatorterminal;

import java.util.List;

/**
 * Created by hxjd on 2017/5/23.
 */
public class MediatorTerminalQuery extends JBaseQuery {
    protected static final JMediatorterminal DAO=new JMediatorterminal();
    private static final MediatorTerminalQuery QUERY=new MediatorTerminalQuery();
    public static MediatorTerminalQuery me() {
        return QUERY;
    }
    public JMediatorterminal findById(final Integer terminalId) {
//        return DAO.getCache(terminalId, new IDataLoader() {
//            @Override
//            public Object load() {
        return DAO.findById(terminalId);
//            }
//        });
    }
    /**
     * 获取所有的MediatorTerminal
     * @return
     */
    public List<JMediatorterminal>getAll(){
        return DAO.find("select * from j_mediatorterminal order by id asc");
    }

    /**
     * 通过mediatorId查询出terminalId
     * @param mediatorId
     * @return
     */
    public JMediatorterminal findByMediatorId(int mediatorId){
        return DAO.findFirst("select * from j_mediatorterminal where mediator_id=?",mediatorId);
    }
    public JMediatorterminal findByTerminalId(int terminalId){
        return DAO.findFirst("SELECT mt.* FROM j_mediatorterminal mt JOIN j_mediator m ON m.id = mt.mediator_id JOIN j_drillcode dc ON dc.id = m.drillId  WHERE dc.state = 0  and terminal_id=?",terminalId);
    }
    
    public List<JMediatorterminal> getByCurrentDrill(){//
    	return DAO.find("SELECT mt.* FROM j_mediatorterminal mt JOIN j_mediator m ON mt.mediator_id = m.id JOIN j_drillcode dc ON dc.id = m.drillId WHERE dc.state = 0 ");
    }
 }
