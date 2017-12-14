package com.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.utils
 * @描述: 返回数据给CHART表格
 * @所属: 华夏九鼎
 * @日期: 2017/9/16 16:53
 * @版本: V1.0
 * @创建人：yanyong
 * @修改人：yanyong
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class ChartData {

    List<String> name = new ArrayList<>();
    List<Double> blueScore = new ArrayList<>();
    List<Double> redScore = new ArrayList<>();
    List<Integer> id = new ArrayList<>();


    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Double> getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(List<Double> blueScore) {
        this.blueScore = blueScore;
    }

    public List<Double> getRedScore() {
        return redScore;
    }

    public void setRedScore(List<Double> redScore) {
        this.redScore = redScore;
    }
}
