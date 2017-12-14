package com.base.web.controller.file;

import com.alibaba.fastjson.JSON;
import com.base.constants.FileTypeEnum;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.im.server.IMServerAioHandler;
import com.base.model.JMediator;
import com.base.model.JMessageall;
import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.model.JTerminal;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.GroupQuery;
import com.base.service.OffUserQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.AttachmentUtils;
import com.base.utils.AudioUtils;
import com.base.utils.GZipUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.tio.client.ClientChannelContext;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: FlieController
 * @包名: com.base.web.controller.file
 * @描述: 文件上传管理
 * @所属: 华夏九鼎
 * @日期: 2017/6/2 11:27
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/file", viewPath = "")
@RouterNotAllowConvert
public class FlieController extends BaseController {

/*    public void index(){
        render("fileIndex.html");
    }*/

    /**
     * 上传文件
     */
    @Before(Tx.class)
    public void upload(){
        try
        {
            UploadFile upfile=getFile("fileName");
            int fileTypeValue=getParaToInt("fileType");
            MessageBean mesg=getBean(MessageBean.class,"messageBean");
            String fileType=FileTypeEnum.getFileTypeByValue(fileTypeValue);
            mesg.setMsgType(fileTypeValue+"");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            mesg.setSendTime(df.format(new Date()));
            String prefix=fileType;//前缀
            String whatPath=AttachmentUtils.moveFile(upfile,prefix);//   \attachment\image\20171018\b2ca9f12377f4ec18d29a1cb48d449c4.jpg
            
            ResponseDto responseDtoMe_ = new ResponseDto();
            responseDtoMe_.setHandle(2);
            responseDtoMe_.setCode(Integer.parseInt(RequestCodeConstants.SEND_SUCCESS_CODE));
            responseDtoMe_.setResponseStatus(1);
            
            responseDtoMe_.setTime(df.format(new Date()));
            renderText(JSON.toJSONString(responseDtoMe_));
            
            
            //保存信息记录
            JMessageall message = new JMessageall();
            message.setSenderId(Integer.parseInt(mesg.getSender()));
            message.setMsgType(mesg.getMsgType());
            message.setMessage(whatPath);
            message.setSendTime(df.format(new Date()));
            message.setSendName(mesg.getSendName());
            //System.out.println("File - GroupID:" + mesg.getGroupId());
            
            message.setCamp(String.valueOf(GroupQuery.me().GainCampByMediatorId(Integer.parseInt(mesg.getSender())).getCamp()));
            message.setState(1);
           
            
            String type = whatPath.substring(whatPath.lastIndexOf(".")+1, whatPath.length());
            if("amr".equalsIgnoreCase(type)){//AMR需要转码成MP3
            	//String path = JFinal.me().getServletContext().getRealPath("/attachment/audio/");
            	String newFile =  whatPath.substring(0,whatPath.lastIndexOf(".")+1);
            	newFile+="mp3";
            	boolean a = AudioUtils.transAmrToMp3(whatPath, newFile);
            	if(a){
            		message.setMessage(newFile);
            	}
            	
            }
            

            
            List<JMediator> mediators= GroupQuery.me().getGroupMediator(mesg.getGroupId());
            JMsg msg1=new JMsg();//离线信息
            boolean isoff=false;
            ResponseDto responseDtome=new ResponseDto();
            responseDtome.setHandle(2); 
            responseDtome.setCode(13);
            responseDtome.setResponseStatus(1);

            responseDtome.setTime(df.format(new Date()));
           
            
            if(mesg.getGroupId()==1){//指令
                
            	message.saveOrUpdate();//指令才保存
            	
            	
            	//-------------------------发送C平台           
                ClientChannelContext<Object, IMPacket, Object> clientChannelContext = (ClientChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get("IM-C");
                //Aio.send(clientChannelContext, null);//修改Null为协议内容文件
               
                String path = JFinal.me().getServletContext().getRealPath("");
                //byte[] front = "$C".getBytes("utf-8");
               
                if(clientChannelContext==null || clientChannelContext.isClosed() || clientChannelContext.isRemoved()){//与C平台连接掉线, 保存(离线)数据
                	JMsg msg = new JMsg();
                	msg.setMsgType("C-1");
                	msg.setSendTime(message.getSendTime());
                	msg.setMessage(message.getMessage());
                	msg.setSenderId(message.getSenderId());
                	msg.setSendName(message.getSendName());
                	msg.saveOrUpdate();
                	
                } else{
    	            File file = new File(path+whatPath);
    	            System.out.println(file.getAbsolutePath());
    				String fileName = file.getName();
    				System.out.println("name: " + fileName);
    				byte[] nameBytes = fileName.getBytes("GBK");
    				IMPacket packet0 = new IMPacket();
    				//packet0.setBody(ArrayUtils.addAll(front, "$1".getBytes("utf-8")));
    				packet0.setBody("$1".getBytes("GBK"));
    				//=========================
    				Aio.send(clientChannelContext, packet0);  //"$1"
    				IMPacket packet1 = new IMPacket();
    				//packet1.setBody(ArrayUtils.addAll(front,nameBytes));
    				packet1.setBody(nameBytes);
    				System.out.println("文件名长度 " + fileName.length());;
    				System.out.println("文件名： " + fileName);
    				//=========================
    				Aio.send(clientChannelContext, packet1);  //"文件名"
    				long fileLength = file.length();
    				System.out.println("文件长度: " + fileLength);
    				IMPacket packet2 = new IMPacket();
    				//packet2.setBody(ArrayUtils.addAll(front,String.valueOf(fileLength).getBytes("utf-8")));
    				packet2.setBody(String.valueOf(fileLength).getBytes("GBK"));
    				//=========================
    				Aio.send(clientChannelContext, packet2);  //"文件长度(大小)"
    				IMPacket packet3 = new IMPacket();
    				packet3.setBody(message.getCamp().getBytes("GBK"));
    				Aio.send(clientChannelContext, packet3);//阵营
    				//
    				
    				IMPacket packet4 = new IMPacket();
    				packet4.setBody(mesg.getSender().getBytes("GBK"));
    				Aio.send(clientChannelContext, packet4);//发送者ID
    				IMPacket packet5 = new IMPacket();
    				packet5.setBody(mesg.getSendName().getBytes("GBK"));
    				Aio.send(clientChannelContext, packet5);//发送者名字
    				IMPacket packet6 = new IMPacket();
        			packet6.setBody(message.getSendTime().getBytes("GBK"));
        			Aio.send(clientChannelContext, packet6);//发送时间
    				
    				InputStream inputStream = new FileInputStream(file);
    				byte[] bytes = IOUtils.toByteArray(inputStream);
    				IMPacket cPacket = new IMPacket();
    				//cPacket.setBody(ArrayUtils.addAll(front,bytes));
    				cPacket.setBody(bytes);
    				Aio.send(clientChannelContext, cPacket);
    				
    				
    				ResponseDto responseDtoMe = new ResponseDto();
    	            responseDtoMe.setHandle(2);
    	            responseDtoMe.setCode(Integer.parseInt(RequestCodeConstants.INSTRUCT_UP_CODE));
    	            responseDtoMe.setResponseStatus(1);
    	            
    	            responseDtoMe.setTime(df.format(new Date()));
    	            String str = JSON.toJSONString(responseDtoMe);
    	            JTerminal terminal = TerminalQuery.me().getByMediator(new Integer(mesg.getSender()));
    	            if(terminal!=null){
    	            	ChannelContext context= (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
    	            	IMPacket packet=new IMPacket();
    	            	byte[] doZipMsg = new byte[0];
                        doZipMsg = GZipUtil.doZip(str);

    	            	packet.setBody(doZipMsg);
    	            	
    	            	Aio.send(context, packet);
    	            }
    	            
            	}
                //---------------发送C平台结束
            } else {//不是指令发送给其他组成员
            	//遍历所有的群友
                for(JMediator mediator:mediators){
                    ResponseDto responseDto=new ResponseDto();
                    responseDto.setHandle(2);
                    responseDto.setCode(13);
                    responseDto.setResponseStatus(1);
                    responseDto.setTime(df.format(new Date()));

                    JTerminal terminal=TerminalQuery.me().getByMediator(mediator.getId());
                    ChannelContext context=null;
                    if(terminal!=null){
                        //可能出现的情况是设备与调理员没绑定
                        context= (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
                    }
                    mesg.setMsg(whatPath);
                    //如果群友在线，将文件地址变为responseDto发给他
                    if(context!=null){
                        IMPacket packet=new IMPacket();
                        if(!(Integer.parseInt(mesg.getSender())==mediator.getId())){
                            responseDto.setData(mesg);
                        }else {
                            responseDto.setCode(18);
                        }
                        String respo= JSON.toJSONString(responseDto);
//                    String respo= JSON.toJSONString("ma dong de ......");
                        byte[] doZipMsg = new byte[0];
                        try {
                            doZipMsg = GZipUtil.doZip(respo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        packet.setBody(doZipMsg);
                        Aio.send(context,packet);
                    }else {
                        //离线消息
                        msg1.setSendName(mesg.getSendName());
                        msg1.setMessage(mesg.getMsg());
                        msg1.setSenderId(Integer.parseInt(mesg.getSender()));
                        msg1.setMsgType(mesg.getMsgType());
                        msg1.setSendTime(mesg.getSendTime());

                        if(!isoff){
                            msg1.save();
                            isoff=true;
                        }

                        JOffuser offuser=new JOffuser();
                        offuser.setGroupId(mesg.getGroupId());
                        offuser.setMsgId(msg1.getId().toString());
                        offuser.setMediatorId(mediator.getId());
                        OffUserQuery.me().updateAndSave(offuser);
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("出错了");
            e.printStackTrace();
        }
        //上传失败
//        ChannelContext context= Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext,"1");
//        Aio.send(context,);

        //后续阶段继续思考
    }

}
