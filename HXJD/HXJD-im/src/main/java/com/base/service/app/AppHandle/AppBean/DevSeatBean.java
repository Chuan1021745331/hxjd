package com.base.service.app.AppHandle.AppBean;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${DevSeatBean}
 * @包名: com.base.service.app.AppHandle.AppBean
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/5/31 15:47
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DevSeatBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;//席位id
    private String seatName;
    private String camp;
    private String directing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirecting() {
        return directing;
    }

    public void setDirecting(String directing) {
        this.directing = directing;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }
}
