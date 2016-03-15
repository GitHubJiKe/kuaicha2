package com.ypf.kuaicha.util;

import com.ypf.kuaicha.TApplication;

/**
 * Created by Administrator on 2016/3/6.
 */
public class StringUtil {
    /**
     * 获取res中的字符串.
     *
     * @param resID      the res id
     * @param formatArgs the format args
     * @return the string
     */
    public static String getString(int resID, Object... formatArgs) {
        if (TApplication.getInstance() != null) {
            return TApplication.getInstance().getString(resID, formatArgs);
        }
        return "";
    }
}
