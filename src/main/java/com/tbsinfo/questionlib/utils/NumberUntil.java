package com.tbsinfo.questionlib.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author jayMamba
 * @date 2019/9/29
 * @time 17:41
 * @desc
 */
public class NumberUntil {

    public static String parseNumber(String pattern, Object bd) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }
}
