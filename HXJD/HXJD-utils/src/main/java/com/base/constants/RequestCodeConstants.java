package com.base.constants;

/**
 * Created by hxjd on 2017/5/27.
 */
public class RequestCodeConstants {
    public static final String INITIALIZE_CODE="1";//初始化
    public static final String DEV_INFORMATION_UPLOAD_CODE="2";//设备信息上传*
    public static final String DEV_CAPS_CODE="3";//获取当前用户信息
    public static final String DEV_SEAT_CODE="4";//获取当前用户席位
    public static final String DEV_ALL_SEAT_CODE="5";//获取全部席位*
    public static final String DEV_PEL_CODE="6";//获取自身阵营所属图元
    public static final String PEL_INFORMATION_CODE="7";//获取所选图元信息
    public static final String OWN_GROUP_CODE="8";//获取所属群组
    public static final String GROUP_GET_CODE="9";//获取当前群组用户
    public static final String USER_INFORMATION_CODE="10";//获取阵营调理员信息
    public static final String SERVER_TIME_CODE="11";//获取服务器时间*
    public static final String NEW_PEL_CODE="12";//新增图元
    public static final String SEND_MES_CODE="13";//发送消息
    public static final String FILE_UPLOAD_CODE="14";//文件上传*
    public static final String INSTRUCT_UP_CODE="15";//指令上报
    public static final String MAP_CENTER_POINT_CODE="16";//获取地图中心点
    public static final String PUSH_MSG_CODE="17";//推送
    
    
    public static final String SEND_SUCCESS_CODE="18";//发送成功
    public static final String SEND_FALE_CODE="19";//发送失败
    public static final String PUSH_SUCCESS = "20";//推送成功

    public static final String PUSH_DEV_CODE="101";//下发设备信息
    public static final int PUSH_GROUP_CODE=108;//下发设备信息
    public static final int PUSH_PEL_CODE=107;//下发变化的图元信息
    public static final int PUSH_SEAT_CODE=102;//下发自身席位信息
    public static final int PUSH_INSTRUCT_UP_CODE = 105;//下发指令消息
}
