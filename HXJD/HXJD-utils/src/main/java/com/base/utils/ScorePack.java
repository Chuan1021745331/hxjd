package com.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.utils
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/9/19 15:21
 * @版本: V1.0
 * @创建人：yanyong
 * @修改人：yanyong
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class ScorePack {

    private String name;
    private String score;
    List<ScorePack> child = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<ScorePack> getChild() {
        return child;
    }

    public void setChild(List<ScorePack> child) {
        this.child = child;
    }
}
