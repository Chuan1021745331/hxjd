package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.AboutmeService;
import com.base.service.UserService;
import com.base.query.UserQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.jfinal.upload.UploadFile;

import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.model.JUser;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: AboutMeController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 用户查看和修改自己信息的相关操作   
 * @所属: 华夏九鼎     
 * @日期: 2017年5月15日 上午9:39:47   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/aboutme", viewPath = "/view/admin/aboutme")
@RouterNotAllowConvert
public class AboutMeController extends BaseController {

	public void index() {
		render("aboutme.html");
	}
	
	public void edit(){
		UploadFile uploadFile = getFile();
		Integer userId = getParaToInt("id");
		JUser newUser = getModel(JUser.class);
		
		String state = AboutmeService.me().edit(userId, uploadFile, newUser);
		if(MessageConstants.EDIT_DEFEAT.equals(state)){
			renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
		} else if(MessageConstants.USER_NULL.equals(state)){
			renderAjaxResultForError(MessageConstants.USER_NULL);
		} else {
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
		}		
	}
	
	public void changePassword(){
		render("changePassword.html");
	}
	
	public void saveNewPassword(){
		String op = getPara("op");
		String np = getPara("np");
		String dnp = getPara("dnp");
		String userId = CookieUtils.get(this, Consts.COOKIE_LOGINED_USER);
		JUser user = UserService.me().findById(new Integer(userId));
		
		String state = AboutmeService.me().saveNewPwd(op, np, dnp, user);
		if(MessageConstants.RAW_PASS_ERROR.equals(state)){
			renderAjaxResultForError(MessageConstants.RAW_PASS_ERROR);
		} else if(MessageConstants.EDIT_SUCCESS.equals(state)){
			CookieUtils.put(this, Consts.COOKIE_LOGINED_USER, user.getId().toString());
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
		} else if(MessageConstants.PASS_INCONFORMITY.equals(state)){
			renderAjaxResultForError(MessageConstants.PASS_INCONFORMITY);
		}
	}
	
}
