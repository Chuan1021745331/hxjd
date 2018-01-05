package com.base.service;

import org.joda.time.DateTime;

import com.base.model.JUser;
import com.base.model.JUserrole;
import com.base.query.UserQuery;
import com.base.query.UserRoleQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;

public class UserService {
	private static final UserService SERVICE = new UserService();
	public static UserService me() {
		return SERVICE;
	}
	
	public JUser findById(final Integer userId) {
		return UserQuery.me().findById(userId);
	}
	
	public boolean delUserById(int id){
		JUser user = UserQuery.me().findById(id); 
		boolean a = user.delete();
		if(a){
			CacheKit.remove("user", id);
			UserRoleQuery.me().delByUserId(id);
			return true;
		}		
		return false;
	}
	
	public JUser findUserByUserName(String username){
		return UserQuery.me().findUserByUserName(username);
	}
	
	@Before(Tx.class)
	public boolean addUserSave(JUser user, Integer roleId){
		String salt = EncryptUtils.salt();
		user.setSalt(salt);
		user.setCreated(DateTime.now().toDate());
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String password = EncryptUtils.encryptPassword(user.getPassword(), salt);
			user.setPassword(password);
		}
		boolean a = user.save();
		if(a){			
			JUserrole jUserrole= new JUserrole();
			jUserrole.setUserId(user.getId());
			jUserrole.setRoleId(roleId);
			boolean b = jUserrole.save();
			if(b){
				return true;
			}
		}
		return false;
	}
	
	@Before(Tx.class)
	public boolean editUserSave(JUser user, UploadFile uploadFile, Integer roleId){
		JUser u = UserQuery.me().findById(user.getId());
		u.setUsername(user.getUsername());
		u.setNickname(user.getNickname());
		u.setRelname(user.getRelname());
		u.setEmail(user.getEmail());
		u.setGender(user.getGender());
		u.setMobile(user.getMobile());
		if (null != uploadFile) {
			String newPath = AttachmentUtils.moveFile(uploadFile,"avatar");
			user.setAvatar(newPath);
			//System.out.println(newPath);
			u.setAvatar(user.getAvatar());
		}
		boolean a = u.update();

		CacheKit.remove("user", u.getId());
		if(a){
			if("administrator".equals(user.getRole())){
				return true;
			}else{				
				UserRoleQuery.me().delByUserId(user.getId());
				JUserrole jUserrole= new JUserrole();
				jUserrole.setUserId(user.getId());
				jUserrole.setRoleId(roleId);
				boolean b = jUserrole.save();
				if(b){
					return true;
				}
			}
			
		}

		return false;
	}
}
