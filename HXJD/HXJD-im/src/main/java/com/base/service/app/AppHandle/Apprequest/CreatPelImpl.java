package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.base.utils.GZipUtil;
import com.base.utils.StringUtils;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CreatPelImpl}
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 创建新的图元
 * @所属: 华夏九鼎
 * @日期: 2017/5/31 17:14
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.NEW_PEL_CODE)
public class CreatPelImpl extends BaseHandleImpl {

	@Override
	public String init(RequestDto data) {
		// TODO Auto-generated method stub
		return null;
	}

}
