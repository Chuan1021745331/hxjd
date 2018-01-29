package com.base.query;


import com.base.model.JTbmrepair;
import com.base.model.dto.TbmrepairSearchDto;
import com.base.utils.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmrepairQuery
 * @包名: com.base.query
 * @描述: (盾构机维修记录查询)
 * @所属: 华夏九鼎
 * @日期: 2018/1/22 10:12
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TbmrepairQuery {
    protected static final JTbmrepair DAO = new JTbmrepair();
    private static final TbmrepairQuery QUERY = new TbmrepairQuery();

    public static TbmrepairQuery me() {
        return QUERY;
    }

    public JTbmrepair findByTbmrepairId(int tbmrepairId){
        return DAO.findFirst("select * from j_tbmrepair where id="+tbmrepairId);
    }

    /**
     * @MethodName: findByTbmId
     * @Description: 通过盾构机id分页查询该盾构机维修记录
     * @param page
     * @param limit
     * @param tbmId
     * @return: java.util.List<com.base.model.JTbmrepair>
     */
    public List<JTbmrepair> findByTbmId(int page,int limit,int tbmId){
        StringBuilder sql=new StringBuilder("select * from j_tbmrepair ");
        sql.append(" where tbmId=? ");
        sql.append(" order by repairtime desc limit ?,?");
        LinkedList<Object> params = new LinkedList<>();
        params.add(limit*page-limit);
        params.add(limit);
        return DAO.find(sql.toString(),params.toArray());
    }

    public List<Record> findListTbmrepairTbmUser(int page,int limit,int tbmId){
        StringBuilder sql=new StringBuilder("select tr.*,t.name as tbmName,u.relname as writer");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id ");
        sql.append(" where tr.tbmId=? ");
        sql.append(" order by repairtime desc limit ?,?");
        LinkedList<Object> params = new LinkedList<>();
        params.add(tbmId);
        params.add(limit*page-limit);
        params.add(limit);
        return Db.find(sql.toString(),params.toArray());
    }


    public JTbmrepair findLatestByTbmId(int tbmId){
        StringBuilder sql=new StringBuilder("select * from j_tbmrepair ");
        sql.append(" where tbmId="+tbmId);
        sql.append(" order by repairtime desc");
        return DAO.findFirst(sql.toString());
    }

    public List<JTbmrepair> findByTbmId(int tbmId){
        return DAO.find("select * from j_tbmrepair where tbmId="+tbmId);
    }

    public long  findCountByTbmId(int tbmId){
        return Db.queryLong("select count(*) from j_tbmrepair where tbmId=" + tbmId);
    }

    /**
     * @MethodName: findCountTbmrepairTbmUser
     * @Description: 按条件分页查询盾构机维修记录总数
     * @param where
     * @return: long
     */
    public long findCountTbmrepairTbmUser(String where){
        StringBuilder sql=new StringBuilder("select count(*)");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id ");
        if(StringUtils.isNotBlank(where)){
            sql.append(" where CONCAT( IFNULL(tr.id, ''),IFNULL(tr.repairman, ''),IFNULL(tr.content, ''),IFNULL(t.name, ''),IFNULL(u.relname, ''),IFNULL(u.username, '')) LIKE '%"+where+"%' ");
        }
        return Db.queryLong(sql.toString());
    }

    /**
     * @MethodName: findListTbmrepairTbmUser
     * @Description: 按条件分页查询盾构机维修记录
     * @param page
     * @param limit
     * @param where
     * @return: java.util.List<com.jfinal.plugin.activerecord.Record>
     */
    public List<Record> findListTbmrepairTbmUser(int page, int limit,String where){

        StringBuilder sql=new StringBuilder("select tr.*,t.name as tbmName,u.relname as writer");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id ");
        if(StringUtils.isNotBlank(where)){
            sql.append(" where CONCAT( IFNULL(tr.id, ''),IFNULL(tr.repairman, ''),IFNULL(tr.content, ''),IFNULL(t.name, ''),IFNULL(u.relname, ''),IFNULL(u.username, '')) LIKE '%"+where+"%' ");
        }
        sql.append(" order by tr.createtime desc LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(limit*page-limit);
        params.add(limit);

        return Db.find(sql.toString(), params.toArray());
    }

    /**
     * 多条件查询结果总数
     * @param searchDto
     * @return
     */
    public long findCountTbmrepairTbmUser(TbmrepairSearchDto searchDto){
        StringBuilder sql=new StringBuilder("select count(*)");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id where 1=1 ");
        if(searchDto!=null){
            //盾构机名不为空
            if(StringUtils.isNotBlank(searchDto.getTbmname())){
                sql.append(" and (t.name like '%"+searchDto.getTbmname()+"%' or tr.tbmname like '%"+searchDto.getTbmname()+"%')");
            }
            //维保人
            if(StringUtils.isNotBlank(searchDto.getRepairman())){
                sql.append(" and tr.repairman like '%"+searchDto.getRepairman()+"%' ");
            }
            //维保开始时间
            if(StringUtils.isNotBlank(searchDto.getRepairtimeStart())){
                sql.append(" and tr.repairtime > '"+searchDto.getRepairtimeStart()+"'");
            }
            //维保结束时间
            if(StringUtils.isNotBlank(searchDto.getRepairtimeEnd())){
                sql.append(" and tr.repairtime < '"+searchDto.getRepairtimeEnd()+"'");
            }
            //下次维保开始时间
            if(StringUtils.isNotBlank(searchDto.getCycleStart())){
                sql.append(" and tr.cycle > '"+searchDto.getCycleStart()+"'");
            }
            //下次维保结束时间
            if(StringUtils.isNotBlank(searchDto.getCycleEnd())){
                sql.append(" and tr.cycle < '"+searchDto.getCycleEnd()+"'");
            }
        }
        return Db.queryLong(sql.toString());
    }

    /**
     *多条件查询结果
     * @param page
     * @param limit
     * @param searchDto
     * @return
     */
    public List<Record> findListTbmrepairTbmUser(int page, int limit, TbmrepairSearchDto searchDto){
        StringBuilder sql=new StringBuilder("select tr.*,t.name as tbmName,u.relname as writer");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id where 1=1");
        if(searchDto!=null){
            //盾构机名
            if(StringUtils.isNotBlank(searchDto.getTbmname())){
                sql.append(" and (t.name like '%"+searchDto.getTbmname()+"%' or tr.tbmname like '%"+searchDto.getTbmname()+"%')");
            }
            //维保人
            if(StringUtils.isNotBlank(searchDto.getRepairman())){
                sql.append(" and tr.repairman like '%"+searchDto.getRepairman()+"%' ");
            }
            //维保开始时间
            if(StringUtils.isNotBlank(searchDto.getRepairtimeStart())){
                sql.append(" and tr.repairtime > '"+searchDto.getRepairtimeStart()+"'");
            }
            //维保结束时间
            if(StringUtils.isNotBlank(searchDto.getRepairtimeEnd())){
                sql.append(" and tr.repairtime < '"+searchDto.getRepairtimeEnd()+"'");
            }
            //下次维保开始时间
            if(StringUtils.isNotBlank(searchDto.getCycleStart())){
                sql.append(" and tr.cycle > '"+searchDto.getCycleStart()+"'");
            }
            //下次维保结束时间
            if(StringUtils.isNotBlank(searchDto.getCycleEnd())){
                sql.append(" and tr.cycle < '"+searchDto.getCycleEnd()+"'");
            }
        }
        sql.append(" order by tr.id desc LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(limit*page-limit);
        params.add(limit);

        return Db.find(sql.toString(), params.toArray());
    }
}
