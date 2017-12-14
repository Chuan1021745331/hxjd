package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.AdminInterceptor;
import com.base.interceptor.ButtonInterceptor;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用于用户管理相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:34:14
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "admin/backup", viewPath = "/view/admin/backup/")
@Before(AdminInterceptor.class)
@RouterNotAllowConvert
public class BackUpController extends BaseController {
    
	@Before(ButtonInterceptor.class)
    public void index() {
    	File file = new File(path);
        File[] fileReadName = file.listFiles();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < fileReadName.length; i++) {
            list.add(fileReadName[i].getName());
        }
        setAttr("list", list);
        render("backup.html");
    }
    
	private String host = PropKit.use("db.properties").get("db_host");
	private String port = PropKit.use("db.properties").get("db_host_port");
	private String name = PropKit.use("db.properties").get("db_name");
	private String user = PropKit.use("db.properties").get("db_user");
	private String password = PropKit.use("db.properties").get("db_password");
	private String path = JFinal.me().getServletContext().getRealPath("/backup/");
    
    
	//创建备份
    public void Save() {
        String fileName = getPara("fileName");
        Boolean isSucc = true; 
        
        //若fileName为空，以时间创建文件名
        if(!StringUtils.isNotEmpty(fileName)){
        	Date date = new Date(System.currentTimeMillis());   
            SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            fileName = sdFormatter.format(date);
        }
              
        File file = new File(path + "\\" + fileName);
        //判断文件(名)是否存在
        File existFile = new File(path);
        if(null != existFile){
        	File[] fileReadName = existFile.listFiles();
            for (File files : fileReadName) {
            	if(files.getName().equals(file.getName())){
    				renderAjaxResultForError("该文件已存在！");
    				return;
    			}
    		}
        }
         
        //创建文件夹
        if (!file.exists()) {
            file.mkdir();
            //renderAjaxResultForSuccess(MessageConstants.ADD_FILE_SUCCESS);
        }
            PrintWriter printWriter = null;
            BufferedReader bufferedReader = null;
            try {

                printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path + "\\" +  fileName + "\\beifen.sql"), "utf8"));
                Process process= Runtime.getRuntime().exec("mysqldump -h " + host + " " + "-u" + user + " " + "-p" + password + " " + name);
                // Process process = Runtime.getRuntime().exec(str);
                															//192.168.0.99     -uroot               -p123456          jungle
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    printWriter.println(line);

                }
                printWriter.flush();
                if (process.waitFor() == 0) {//0 表示线程正常终止。
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
                isSucc = false;
            } catch (InterruptedException e) {
                e.printStackTrace();                
                isSucc = false;
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!isSucc){
                	delFile(path + "\\" + fileName);
                	renderAjaxResultForError("备份失败！！");
                	return;
                }
                
            }
            renderAjaxResultForSuccess(MessageConstants.ADD_FILE_SUCCESS);
    }


    //删除备份
    private  void delFile(String filePath){
        File file=new File(filePath);
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(int i = 0 ; i < files.length; i++){
                delFile(files[i].getPath());
            }
        }
           file.delete();
    }
    
    //删除备份
    public void del(){
        String fileName = getPara("fileName");
         File file = new File(path + "\\" + fileName);
         if(file.isDirectory()){
             File [] files = file.listFiles();
             for(int i = 0 ; i < files.length; i++){
                 delFile(files[i].getPath());
         }
        }
        boolean a =  file.delete();
       if(a){
           renderAjaxResultForSuccess(MessageConstants.DEL_FILE_SUCCESS);
           return ;
       }
        renderAjaxResultForError(MessageConstants.DEL_FILE_DEFEAT);
    }

    //还原备份
     public void edit(){
         String fileName = getPara("fileName");
         File file = new File(path + "\\" + fileName);
          if(file.isDirectory()) {
              try {
                  Process process = Runtime.getRuntime().exec("mysql -h " + host + " " + "-u" + user + " " + "-p" + password + " " + name +" --default-character-set=utf8");
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                          new FileInputStream(path + "\\" + fileName + "\\beifen.sql"),
                          "utf-8"));
                  OutputStream outputStream = process.getOutputStream();
                  StringBuffer stringBuffer = new StringBuffer();
                  String str = null;
                  while ((str = bufferedReader.readLine()) != null) {
                      stringBuffer.append(str + "\r\n");
                  }
                  str = stringBuffer.toString();
                  OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                          "utf-8");
                  writer.write(str);
                  writer.flush();
                  outputStream.close();
                  bufferedReader.close();
                  writer.close();
              } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
                  renderAjaxResultForError("还原失败！！");
                  return;
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
                  renderAjaxResultForError("还原失败！！");
                  return;
              } catch (IOException e) {
                  e.printStackTrace();
                  renderAjaxResultForError("还原失败！！");
                  return;
              }
              renderAjaxResultForSuccess(MessageConstants.REDUCITION_FILE_SUCCESS);
              return;
          }
             renderAjaxResultForSuccess(MessageConstants.REDUCITION_FILE_SUCCESS);
     }
}

