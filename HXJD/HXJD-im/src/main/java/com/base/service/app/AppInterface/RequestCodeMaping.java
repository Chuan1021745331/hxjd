package com.base.service.app.AppInterface;

import java.lang.annotation.*;

/**
 * Created by hxjd on 2017/5/27.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RequestCodeMaping {
    String requestCode();
}
