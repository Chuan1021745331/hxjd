package com.base.service;

import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.model.JUser;
import com.base.query.UserQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AboutmeService {
	private final static Logger logger= LoggerFactory.getLogger(AboutmeService.class);

	private static final AboutmeService SERVICE = new AboutmeService();
	public static AboutmeService me() {
		return SERVICE;
	}
	
	@Before(Tx.class)
	public int edit(Integer userId, UploadFile uploadFile, JUser newUser){
		JUser user = UserQuery.me().findById(userId);
		if(null==user){
			//renderAjaxResultForError(MessageConstants.USER_NULL);
			return 2;
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
			return 1;
		}		
		return 0;
	}
	
	@Before(Tx.class)
	public int saveNewPwd(String op, String np, String dnp, JUser user){
		if (!EncryptUtils.verlifyUser(user.getPassword(), user.getSalt(), op)) {
			return 0;
		}
		if(np.equals(dnp)){
			String newPassword = EncryptUtils.encryptPassword(np, user.getSalt());
			user.setPassword(newPassword);
			user.update();
			//CookieUtils.put(AboutMeC, Consts.COOKIE_LOGINED_USER, user.getId().toString());
			return 1;
		} else {
			return 2;
		}
	
	}

}
