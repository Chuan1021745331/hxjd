package com.base.service;

import com.base.model.JDepartment;
import com.base.model.dto.MenuSimpDto;
import com.base.model.dto.TreeSimpDto;
import com.base.query.DepartmentQuery;
import com.base.query.DepartmentUserQuery;
import com.base.query.DepartmentVisitorQuery;
import com.base.query.UserQuery;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentService
 * @包名: com.base.service
 * @描述: 部门业务服务
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 10:37
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DepartmentService {
    private final static DepartmentService SERVICE=new DepartmentService();
    public static DepartmentService me(){
        return SERVICE;
    }

    public JDepartment findDepartmentById(int departmentId){
        return DepartmentQuery.me().findById(departmentId);
    }

    public boolean saveOrUpdate(JDepartment department){
        //父节点为根节点，直接添加
        if(department.getParentId()==0){
            return department.saveOrUpdate();
        }
        JDepartment parent = this.findDepartmentById(department.getParentId());
        //如果上一级是职称，不能添加
        if(parent.getType()==JDepartment.TYPE_POSITION){
            return false;
        }
        return department.saveOrUpdate();
    }
    public JDepartment saveOrUpdateForDepartment(JDepartment department){
        boolean b = saveOrUpdate(department);
        if(b){
            return department;
        }else{
            return null;
        }
    }
    public List<JDepartment> getAll(){
        return DepartmentQuery.me().getAll();
    }

    /**
     * @MethodName: del
     * @Description: (删除部门,当该部门的子部门有用户关联时,不能删除)
     * @param id
     * @return: boolean
     */
    public boolean del(String id){

        JDepartment department = DepartmentQuery.me().findById(Integer.parseInt(id));
        return department.delete();
    }

    public boolean delTree(int departmentId){
        JDepartment department = DepartmentQuery.me().findById(departmentId);
        List<JDepartment> departmentList = DepartmentQuery.me().findByParentId(departmentId);
        if(departmentList.size()>0){
            for(JDepartment d:departmentList){
                delTree(d.getId());
            }
        }
        return department.delete();
    }

    public JDepartment findDepartmentByUserId (int userId){
        return DepartmentQuery.me().findByUserId(userId);
    }

    public JDepartment findDepartmentByVisitorId(Integer visitorId){
        return DepartmentQuery.me().findByVisitorId(visitorId);
    }

    public List<JDepartment> getChildren(int departmentId){
        return DepartmentQuery.me().findByParentId(departmentId);
    }

    /**
     * @MethodName: getJDepartmentSimp
     * @Description: 获取部门id为departmentId下面的所有子部门,并封装为树形数据
     * @param departmentId
     * @return: java.util.List<com.base.model.dto.TreeSimpDto<com.base.model.JDepartment>>
     */
    public TreeSimpDto getDepartmentSimp(int departmentId){
        List<JDepartment> children = DepartmentQuery.me().findByParentId(departmentId);
        TreeSimpDto dtt = new TreeSimpDto();
        if(departmentId!=0){
            JDepartment department = DepartmentQuery.me().findById(departmentId);
            dtt.setId(department.getId()+"");
            dtt.setName(department.getName());
            dtt.setScore(department.getType()+"");
        }else{
            dtt.setId("0");
            dtt.setName("根节点");
        }
        List<TreeSimpDto> treeChildren= new ArrayList<>();
        if(children.size()>0){
            for(JDepartment d:children){
                //如果子节点不是部门，则不添加进去
                if(d.getType()!=JDepartment.TYPE_DEPARTMENT){
                    continue;
                }
                treeChildren.add(getDepartmentSimp(d.getId()));
            }
        }
        dtt.setChildren(treeChildren);
        return dtt;
    }
    /**
     * @MethodName: getParents
     * @Description: 返回传入部门的所有上级部门
     * @param departmentId
     * @return: java.util.List<com.base.model.JDepartment>
     */
    public List<JDepartment> getParents(int departmentId){
        List<JDepartment> parents=new ArrayList<>();
        this.getParents(departmentId,parents);
        return parents;
    }

    /**
     * @MethodName: getParents
     * @Description: 递归去取出每个部门的上一级并放在集合中
     * @param departmentId
     * @param parents
     * @return: void
     */
    public void getParents(int departmentId,List<JDepartment> parents){
        JDepartment department =null;
        if(departmentId==0){
            department =new JDepartment();
            department.setId(0);
            department.setName("根节点");
            parents.add(department);
            return;
        }
        department=DepartmentQuery.me().findById(departmentId);
        if(null!=department){
            getParents(department.getParentId(),parents);
            parents.add(department);
        }
    }

    public Long findCountPositionByDepartmentId(Integer departmentId){
        return DepartmentQuery.me().findCountPositionBydepartmentId(departmentId);
    }

    //获取部门的下职位
    public List<JDepartment> findPositionByDepartmentId(Integer page, Integer limit, Integer departmentId, long count){
        List<JDepartment> positionList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            positionList = DepartmentQuery.me().findPositionByDepartmentId(page, limit, departmentId);
        }
        return positionList;
    }

    /**
     * @MethodName: isCanDel
     * @Description: (判断该不能能不能删除,当子部门下的职位关联了用户时，不能删除)
     * @param departmentId
     * @return: boolean
     */
    public boolean isCanDel(Integer departmentId){
        JDepartment department = DepartmentQuery.me().findById(departmentId);
        List<JDepartment> departmentList = DepartmentQuery.me().findByParentId(departmentId);
        if(departmentList.size()<=0){
            //查看与部门关联的用户，有:返回false,无:返回true
            long countDU = DepartmentUserQuery.me().findCountByDepartmentId(departmentId);
            long countDV = DepartmentVisitorQuery.me().findCountByDepartmentId(departmentId);
            return countDU==0&&countDV==0;
        }
        else{
            for(JDepartment d:departmentList){
                if(!isCanDel(d.getId()))
                    return false;
            }
            return true;
        }
    }

}
