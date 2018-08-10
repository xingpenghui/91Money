package com.qfedu.core.util;

import com.qfedu.core.constant.SystemConst;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *@Author feri
 *@Date Created in 2018/7/24 23:01
 */
public class DecimalFormatUtil {
    // 金额
    public static BigDecimal amountFormat(BigDecimal number) {
        number = number.setScale(SystemConst.STORE_SCALE, RoundingMode.HALF_UP);
        return number;
    }

    // 利率
    public static BigDecimal rateFormat(BigDecimal number) {
        number = number.setScale(SystemConst.STORE_SCALE, RoundingMode.HALF_UP);
        return number;
    }

    public static BigDecimal decimalRateFormat(BigDecimal number) {
        return number.multiply(BigDecimal.valueOf(100));
    }

    // 月利率
    public static BigDecimal monthRateFormat(BigDecimal number) {
        return number.multiply(BigDecimal.valueOf(100)).divide(
                BigDecimal.valueOf(12), SystemConst.CAL_SCALE,
                RoundingMode.HALF_UP);
    }

    public static BigDecimal formatBigDecimal(BigDecimal data, int scal) {
        if (null == data) {
            return new BigDecimal(0.00);
        }
        return data.setScale(scal, BigDecimal.ROUND_HALF_UP);
    }
}
