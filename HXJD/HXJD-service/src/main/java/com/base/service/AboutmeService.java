package com.base.service;

import com.base.constants.MessageConstants;
import com.base.model.JUser;
import com.base.query.UserQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.EncryptUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AboutmeService {
	private static final AboutmeService SERVICE = new AboutmeService();
	private final static Logger logger= LoggerFactory.getLogger(AboutmeService.class);
	public static AboutmeService me() {
		return SERVICE;
	}
	
	@Before(Tx.class)
	public String edit(Integer userId, UploadFile uploadFile, JUser newUser){
		JUser user = UserQuery.me().findById(userId);
		if(null==user){
			//renderAjaxResultForError(MessageConstants.USER_NULL);
			return MessageConstants.USER_NULL;
		}
		if (null != uploadFile) {
			String newPath = AttachmentUtils.moveFile(uploadFile,"avatar");
			user.setAvatar(newPath);
			logger.info(newPath);
		}
			
		user.setNickname(newUser.getNickname());
		user.setRelname(newUser.getRelname());
		user.setGender(newUser.getGender());
		user.setEmail(newUser.getEmail());
		user.setMobile(newUser.getMobile());
		boolean a = user.update();
		if(a){
			return MessageConstants.EDIT_SUCCESS;
		}		
		return MessageConstants.EDIT_DEFEAT;
	}
	
	@Before(Tx.class)
	public String saveNewPwd(String op, String np, String dnp, JUser user){
		if (!EncryptUtils.verlifyUser(user.getPassword(), user.getSalt(), op)) {
			return MessageConstants.RAW_PASS_ERROR;
		}
		if(np.equals(dnp)){
			String newPassword = EncryptUtils.encryptPassword(np, user.getSalt());
			user.setPassword(newPassword);
			user.update();
			//CookieUtils.put(AboutMeC, Consts.COOKIE_LOGINED_USER, user.getId().toString());
			return MessageConstants.EDIT_SUCCESS;
		} else {
			return MessageConstants.PASS_INCONFORMITY;
		}
	
	}

}
