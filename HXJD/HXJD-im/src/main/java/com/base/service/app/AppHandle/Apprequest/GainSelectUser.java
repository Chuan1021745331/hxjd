package com.base.service.app.AppHandle.Apprequest;

import com.base.constants.RequestCodeConstants;
import com.base.model.dto.RequestDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainSelectUser
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得所选用户信息
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.USER_INFORMATION_CODE)
public class GainSelectUser extends BaseHandleImpl {

	@Override
	public String init(RequestDto data) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
