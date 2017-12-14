package com.base.service.app.AppInterface;

import com.base.model.JMediatorterminal;
import com.base.model.JTerminal;
import com.base.model.dto.RequestDto;
import com.base.service.MediatorTerminalQuery;
import com.base.service.TerminalQuery;
import com.base.utils.ClassScaner;
import com.base.utils.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by hxjd on 2017/5/26.
 */
public class AppService {

    /**
     * 初始化终端
     */
    public static int initializeTerminal(String sd) {
        int mediatorId=0;
        if (StringUtils.isNotBlank(sd)) {
            JTerminal terminal = TerminalQuery.me().initializeTerminal(sd);
            if (terminal != null) {
                JMediatorterminal  mediatorterminal= MediatorTerminalQuery.me().findByTerminalId(terminal.getId());
                mediatorId=mediatorterminal.getMediatorId();
                return mediatorId;
            }
        }
        return mediatorId;
    }
    private static List<Class<BaseHandleImpl>> BaseHandleImplClassList = ClassScaner.scanSubClass(BaseHandleImpl.class);
    public static String handleCore(RequestDto requestDto){
        if(BaseHandleImplClassList!=null){
            for(Class<?> impl:BaseHandleImplClassList){
                RequestCodeMaping requestCodeMaping=impl.getAnnotation(RequestCodeMaping.class);
                if(requestCodeMaping!=null&&StringUtils.isNotBlank(requestCodeMaping.requestCode())){
//                    JSONObject object=JSON.parseObject(requestStr);
                    if(requestDto.getCode()==Integer.parseInt(requestCodeMaping.requestCode())){
                        try {
                        	int i = requestDto.getCode();
                            Method method= impl.getMethod("init",RequestDto.class);
                            try {
                                Object obj=impl.newInstance();
                                try {
                                    return (String)method.invoke(obj,requestDto);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "";
    }
}
