package com.base.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.base.service.app.AppHandle.AppBean.DevInstructionBeanDto;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.GZipUtil;
import com.jfinal.aop.Before;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 104 on 2017/6/13.
 */
@RouterMapping(url = "admin/instruction", viewPath = "/view/admin/instruction")
@RouterNotAllowConvert
public class instructionController extends BaseController {

    @Before(ButtonInterceptor.class)
    public void index() {
        render("instruction.html");
    }

    public void instructionData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");

        List<JInstruction> list = InstructionQuery.me().findList(start, length, column, order, search);
        long count = InstructionQuery.me().findConunt(search);
        renderPageResult(draw, (int) count, (int) count, list);
    }


    public void send() {
        Integer id = getParaToInt("id");
        JInstruction instruction = InstructionQuery.me().findById(id);
        List<JMediator> jMediator = new ArrayList<>();
        //获取全部的调理员信息
        List<JMediator> jMediators = MediatorQuery.me().getMediatorByGroupId(1);
        //获取已经选择了的调度员信息
        List<JMediator> exist = MediatorQuery.me().getExist();
        //获取自己选中的调度员
        for (JMediator jM : jMediators) {
            jMediator.add(jM);
        }

        setAttr("jMediator", jMediator);
        setAttr("Jinstruction", instruction);
        render("sendinstruction.html");
    }

    public void issued() {

        String[] medId = getParaValues("medId");
        String message = getPara("Jinstruction.message");
        String msgId = getPara("Jinstruction.id");

        DevInstructionBeanDto devInstructionBeanDto = new DevInstructionBeanDto();
        devInstructionBeanDto.setCamp(1);
        devInstructionBeanDto.setSenderName("平台");
        devInstructionBeanDto.setMsg(message);

        MessageBean mesg=new MessageBean();
        mesg.setMsgType("0");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mesg.setSendTime(df.format(new Date()));
        mesg.setGroupId(1);
        mesg.setMsg(message);
        mesg.setSendName("平台");

        for (String mId:medId) {
            String[] mediator=mId.split("\\+");
            boolean isoff=false;
                ResponseDto responseDto=new ResponseDto();
                responseDto.setHandle(2);
                responseDto.setCode(RequestCodeConstants.PUSH_INSTRUCT_UP_CODE);
                responseDto.setResponseStatus(1);

               JRecinstruction jRecinstruction = new JRecinstruction();
               jRecinstruction.setMediatorId(Integer.parseInt(mediator[0]));
               jRecinstruction.setMediatorName(mediator[1]);
               RecInstructionQuery.me().ReciviceSave(jRecinstruction);

                JTerminal terminal = TerminalQuery.me().getByMediator(Integer.parseInt(mediator[0]));
                if(terminal!=null){
                ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
                    if (context != null) {
                    //可能出现的情况是设备与调理员没绑定
                    IMPacket packet=new IMPacket();
                    responseDto.setData(devInstructionBeanDto);
                    String respo= JSON.toJSONString(responseDto);
                    byte[] doZipMsg = new byte[0];
                    try {
                        doZipMsg = GZipUtil.doZip(respo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    packet.setBody(doZipMsg);
                    Aio.send(context,packet);
                    } else {
                        JMsg jMsg=new JMsg();
                        jMsg.setSendName(mesg.getSendName());
                        jMsg.setMessage(mesg.getMsg());
                        jMsg.setMsgType(mesg.getMsgType());
                        jMsg.setSendTime(mesg.getSendTime());

                        if(!isoff){
                            jMsg.save();
                            isoff=true;
                        }
                        JOffuser offuser=new JOffuser();
                        offuser.setGroupId(1);
                        offuser.setMsgId(jMsg.getId().toString());
                        offuser.setMediatorId(Integer.parseInt(mediator[0]));
                        OffUserQuery.me().updateAndSave(offuser);

                    }
                }
            }



        }

    }
