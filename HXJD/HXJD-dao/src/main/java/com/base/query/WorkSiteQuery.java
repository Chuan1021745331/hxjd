package com.base.query;

import com.base.model.JWorksite;

import javax.swing.plaf.PanelUI;
import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.query
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 16:20
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class WorkSiteQuery {
    protected static final JWorksite DAO = new JWorksite();
    private static final WorkSiteQuery QUERY = new WorkSiteQuery();

    public static WorkSiteQuery me() {
        return QUERY;
    }

    public JWorksite findById(int id){
        return DAO.findById(id);
    }

    public List<JWorksite> findAllCircuit(){
        return DAO.find("SELECT * FROM j_worksite");
    }

    public List<JWorksite> findByCirciuteId(int id){
        return DAO.find("select * from j_worksite where circuitid="+id);
    }

    /**
     * 删除工点byId
     * @param id
     * @return
     */
    public boolean delWorksiteById(int id){
        return DAO.deleteById(id);
    }
}
