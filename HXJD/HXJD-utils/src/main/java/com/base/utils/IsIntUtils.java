package com.base.utils;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.utils
 * @描述: 判断是否为int
 * @所属: 华夏九鼎
 * @日期: 2017/9/4 13:06
 * @版本: V1.0
 * @创建人：yanyong
 * @修改人：yanyong
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class IsIntUtils {
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
