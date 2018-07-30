package com.qfedu.core.test;

import com.qfedu.core.util.EncrypUtil;

/**
 *@Author feri
 *@Date Created in 2018/7/30 09:26
 */
public class Md5_Main {
    public static void main(String[] args) {
        System.out.println("admin--->"+EncrypUtil.md5Pass("admin"));
    }
}
