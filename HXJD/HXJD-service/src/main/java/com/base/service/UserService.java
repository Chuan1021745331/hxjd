package com.base.service;

import java.util.ArrayList;
import java.util.List;

import com.base.model.JDepartment;
import com.base.model.JDepartmentuser;
import com.base.query.DepartmentUserQuery;
import org.joda.time.DateTime;

import com.base.constants.Consts;
import com.base.constants.MagicValueConstants;
import com.base.constants.MessageConstants;
import com.base.message.Actions;
import com.base.message.MessageKit;
import com.base.model.JUser;
import com.base.model.JUserrole;
import com.base.query.UserQuery;
import com.base.query.UserRoleQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
	private static final UserService SERVICE = new UserService();
	private final static Logger logger= LoggerFactory.getLogger(UserService.class);
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
			DepartmentUserQuery.me().delByUserId(id);
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
	public boolean addUserSave(JUser user, Integer roleId,Integer departmentId){
		boolean b = addUserSave(user, roleId);
		if(b){
			return saveDepartmentUser(user.getId(),departmentId);
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
			//logger.info(newPath);
			u.setAvatar(user.getAvatar());
		}
		boolean a = u.update();

		CacheKit.remove("user", u.getId());
		if(a){
			if(MagicValueConstants.ADMINISTRATOR.equals(user.getRole())){
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

	@Before(Tx.class)
	public boolean editUserSave(JUser user, UploadFile uploadFile, Integer roleId,Integer departmentId) {
		boolean b = this.editUserSave(user, uploadFile, roleId);
		if(b){
			DepartmentUserQuery.me().delByUserId(user.getId());
			return saveDepartmentUser(user.getId(),departmentId);
		}
		return false;
	}

	public boolean saveDepartmentUser(Integer userId,Integer departmentId){
		JDepartmentuser jDepartmentuser=new JDepartmentuser();
		jDepartmentuser.setUserId(userId);
		jDepartmentuser.setDepartmentId(departmentId);
		boolean save = jDepartmentuser.save();
		if(save){
			return true;
		}
		return false;
	}

	@Before(Tx.class)
	public Boolean login(JUser user, String password){
		if (EncryptUtils.verlifyUser(user.getPassword(), user.getSalt(), password)) {			
			user.setLogined(DateTime.now().toDate());
			user.saveOrUpdate();
			return true;			
		} else {
			return false;
		}
	}
	
	public long findConuntUserRole(){
		return UserQuery.me().findConuntUserRole();
	}
	
	public List<Record> findListUserRole(Integer page, Integer limit, String where, long count){
		List<Record> list = new ArrayList<>();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
	        list = UserQuery.me().findListUserRole(page, limit, where);
	        for(Record record:list){
				JDepartment department = DepartmentService.me().findDepartmentByUserId(record.getInt("id"));
				if(department!=null){
					record.set("departmentName",department.getName());
				}
				else{
					record.set("departmentName","暂无职称");
				}
			}
		}
		return list;
	}
}
