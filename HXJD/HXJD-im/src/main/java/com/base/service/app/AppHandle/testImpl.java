package com.base.service.app.AppHandle;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

/**
 * Created by hxjd on 2017/5/27.
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.INITIALIZE_CODE)
public class testImpl extends BaseHandleImpl {
    @Override
    public String init(RequestDto data) {
        ResponseDto responseDto=new ResponseDto();
        responseDto.setResponseStatus(9);
        responseDto.setHandle(9);
        responseDto.setCode(9);
        return JSON.toJSONString(responseDto);
    }
}
