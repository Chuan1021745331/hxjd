package com.base.im.common;

import com.alibaba.fastjson.JSON;
import com.base.model.dto.ResponseDto;
import com.base.utils.GZipUtil;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.im.common
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/6/13 11:14
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class MsgUtil {
    /**
     * 发送信息
     * @param context
     * @param responseDto
     */
    public static void Send(ChannelContext context, ResponseDto responseDto){
        IMPacket packet=new IMPacket();
        String respo= JSON.toJSONString(responseDto);
        byte[] doZipMsg = new byte[0];
        try {
            doZipMsg = GZipUtil.doZip(respo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        packet.setBody(doZipMsg);
        Aio.send(context,packet);
    }
}
