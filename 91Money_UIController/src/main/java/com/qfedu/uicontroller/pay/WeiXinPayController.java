package com.qfedu.uicontroller.pay;

import com.qfedu.core.pay.PayCommonUtil;
import com.qfedu.core.pay.PayConfig;
import com.qfedu.core.pay.XmlUtil;
import com.qfedu.core.pay.ZxingUtil;
import com.qfedu.core.redis.JedisUtil;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.uicontroller.login.Login;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 *@Author feri
 *@Date Created in 2018/8/10 15:17
 */
@RestController
public class WeiXinPayController {

    @Autowired
    private JedisUtil jedisUtil;

    //下单 获取订单的支付链接
    @RequestMapping("wxpaypre.do")
    public R pre(String oid, String price, String body, HttpServletRequest request) throws Exception {
        User user=Login.getU(jedisUtil,request);
        String url=PayCommonUtil.weixin_pay(price,body,oid);
        //jedisUtil.
        jedisUtil.addStr(user.getId()+"",url,TimeUnit.HOURS,2);
        return R.setOK("支付信息成功");
    }
    //将支付链接生成二维码
    @RequestMapping("wxpayimage.do")
    public void payimage(HttpServletResponse response,HttpServletRequest request) throws IOException {
        User user=Login.getU(jedisUtil,request);
        String url=jedisUtil.getStr(user.getId()+"");
        BufferedImage image;
        if(url!=null && url.length()>0) {
            image = ZxingUtil.createImage(url, 200, 200);
        }else {
            image = ZxingUtil.createImage("亲，支付失效，请重新发起！", 200, 200);
        }
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
    //回调
    @RequestMapping("wxpaycallback.do")
    public R callback(HttpServletRequest request) throws IOException, JDOMException {
        InputStream inputStream=request.getInputStream();
        byte[] data=new byte[1024];
        int len;
        StringBuffer buffer=new StringBuffer();
        while ((len=inputStream.read(data))!=-1){
            buffer.append(new String(data,0,len));
        }
        Map<String,String> map=XmlUtil.doXMLParse(buffer.toString());
        //延签
        SortedMap<Object,Object> sortedMap=new TreeMap<>();
        for(String k:map.keySet()){
            String v=map.get(k);
            if(v.length()>0){
                sortedMap.put(k,v);
            }
        }
       if(PayCommonUtil.isTenpaySign("UTF-8",sortedMap,PayConfig.API_KEY)){
           if(map.get("return_code").equals("SUCCESS")){
                if(map.get("result_code").equals("SUCCESS")){
                    //订单支付成功
                    System.err.println("订单id:"+map.get("out_trade_no")+"----成功！");
                    // 完成投资
                    //账户余额冻结
                    //账户余额新增
                }
           }
       }
        return R.setOK("OK");
    }

}
