package com.base.web.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.core.BaseController;
import com.base.interceptor.AdminInterceptor;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JDrillcode;
import com.base.model.JGrademark;
import com.base.model.JKpi;
import com.base.model.JObject;
import com.base.model.dto.KpiObjDto;
import com.base.model.dto.PushObjectDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.base.utils.IsIntUtils;
import com.base.utils.PersonSorce;
import com.base.utils.ScorePack;
import com.base.utils.ScoreSave;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.log.Log;
import net.sf.json.JSONArray;

import java.io.*;
import java.util.*;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: MenuController.java
 * @包名: com.base.web.controller.admin
 * @描述: 评分管理
 * @所属: 华夏九鼎
 * @日期: 2017年5月127日 上午9:37:27
 * @版本: V1.0
 * @创建人：ziv
 * @修改人：ziv
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/gradeMarkApp", viewPath = "")
@RouterNotAllowConvert
public class GradeMarkApp extends BaseController {
    Set<PersonSorce> childProject = new HashSet();

    /**
     * 评分保存
     */
    public void gradeSave() {
        String gradeSave = getPara("score");
        Boolean isSave = false;
        if(gradeSave != null){
            JSONArray jsonArray = JSONArray.fromObject(gradeSave);
            for (int i = 0; i < jsonArray.size(); i++) {
                net.sf.json.JSONObject scoreSave = jsonArray.getJSONObject(i);
                String drillName = String.valueOf(scoreSave.get("drillname"));
                Integer userId = Integer.valueOf(String.valueOf(scoreSave.get("id")));
                String camp = String.valueOf(scoreSave.get("camp"));
                Integer endNode = Integer.valueOf(String.valueOf(scoreSave.get("endNode")));
                String grade = String.valueOf(scoreSave.get("score"));
                String userName = MediatorQuery.me().findById(userId).getMediatorName();
                JGrademark jGrademark = new JGrademark();
                jGrademark.setGradeParentId(endNode);
                jGrademark.setName(userName);
                jGrademark.setScore(grade);
                jGrademark.setCamp(camp);
                jGrademark.setDrillname(drillName);
                Integer isExist = GradeMarkQuery.me().findIsExist(endNode, userName, drillName);
                jGrademark.setId(isExist);
                if (isExist != null) {
                    isSave = jGrademark.update();
                } else {
                    isSave = jGrademark.save();
                }
            }

        }
        renderJson(JSON.toJSON(isSave));
    }

    /**
     * 个人得分详情
     */
    public void getPersonSorce() {
        Integer id = getParaToInt("id");
        String drillName = getPara("drillName");
        List<ScorePack> list;
        if (id != null&&drillName != null) {
            String name = MediatorQuery.me().findById(id).getMediatorName();
            List<JGrademark> jGrademarks = GradeMarkQuery.me().findListByName(name,drillName);
            if (!jGrademarks.isEmpty()) {
                for (JGrademark jg : jGrademarks) {
                    getOneProjectScrce(jg, jg.getDrillname());
                }
                list = packScroe(0);
            } else {
                list = null;
                System.out.println("暂时无评分");
            }
        } else {
            list = null;
            System.out.println("未注册");
        }
        renderJson(JSON.toJSONString(list));
    }

    /**把个人的评分信息tree从子叶节点拿到根节点
     * @param jGrademark
     * @param drillName
     */
    public void getOneProjectScrce(JGrademark jGrademark, String drillName) {
        Integer parentId = jGrademark.getGradeParentId();
        JGrademark parent = GradeMarkQuery.me().findById(parentId);
        PersonSorce personSorce = new PersonSorce();
        personSorce.setId(jGrademark.getId());
        personSorce.setName(jGrademark.getName());
        personSorce.setScore(jGrademark.getScore());
        personSorce.setParentId(parentId);
        childProject.add(personSorce);
        if (!parentId.equals(0)) {
            getOneProjectScrce(parent, drillName);
        }
    }

    /**
     * 重根节点到子叶节点封装评分数据
     * @param id
     * @return
     */
    public  List<ScorePack> packScroe(Integer id) {
        List<ScorePack> child = new ArrayList<>();
        Iterator<PersonSorce> iter = childProject.iterator();
        while (iter.hasNext()) {
            PersonSorce per = iter.next();
            if (per.getParentId().equals(id)) {
                ScorePack scorePack = new ScorePack();
                scorePack.setName(per.getName());
                scorePack.setScore(per.getScore());
                Integer childId = per.getId();
                child.add(scorePack);
                scorePack.setChild(packScroe(childId));
            }
        }
        return child;
    }


}
