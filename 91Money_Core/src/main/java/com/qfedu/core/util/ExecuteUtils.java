package com.qfedu.core.util;

import com.qfedu.core.vo.R;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:48
 */
public class ExecuteUtils {
    public static R getR(int num,String msg){
        if(num>0){
            return R.setOK(msg+"成功");
        }else{
            return R.setError(msg+"失败");
        }
    }
}
