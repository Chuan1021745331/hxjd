package com.base.service;

import com.base.model.JMediator;
import com.base.model.JMedseat;
import com.base.model.JObjectmed;

import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class ObjectMedQuery {
    protected static final JObjectmed DAO = new JObjectmed();
    private static final ObjectMedQuery QUERY = new ObjectMedQuery();

    public static ObjectMedQuery me() {
        return QUERY;
    }

    public JObjectmed findById(final Integer pelId) {
                return DAO.findById(pelId);
    }

    public List<JObjectmed> getAll(){
        return DAO.find("select * from j_objectmed order by id asc");
    }

  /*  public List<JObjectmed> getAllExist(){
        return DAO.find("select * from j_objectmed where objectId = ?");
    }*/

    //根据objectID获取mediatorID
    public List<JObjectmed> getMediatorId(int id){
        return DAO.find("select * from j_mediator  WHERE objectId =?",id);
    }

    public Boolean delectByObjectId(int objectId){
        int b = DAO.doDelete("objectId =?",objectId);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }

    /*public  List<JObjectmed> seatIsExist(int id){
        return DAO.doFind("seatId =? ",id);
    }*/

    public Boolean delectByMedId(int id){
        int b = DAO.doDelete("mediatorId =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }




}
