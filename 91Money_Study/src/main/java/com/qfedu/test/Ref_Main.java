package com.qfedu.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 *@Author feri
 *@Date Created in 2018/7/29 20:57
 */
public class Ref_Main {
    public static void main(String[] args) {
        //强引用  ---霸道  只有引用存在就不会被回收
        Object obj1=new Object();
        //软引用   --      内存不够时，会强制回收
        SoftReference<Object> obj2=new SoftReference<>(new Object());
        obj2.get();
        //弱引用  --       GC回收
        WeakReference<Object> obj3=new WeakReference<>(new Object());
        obj3.get();
        obj3.isEnqueued();//验证是否可用

        //虚引用（幽灵引用） --随时可被回收   回收的时候会触发finalize方法
        PhantomReference<Object> obj4=new PhantomReference<>(new Object(),new ReferenceQueue<Object>());
        obj4.get();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
