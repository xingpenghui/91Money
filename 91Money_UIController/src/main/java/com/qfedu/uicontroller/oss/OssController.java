package com.qfedu.uicontroller.oss;

import com.qfedu.core.util.FileUtils;
import com.qfedu.core.util.OSSUtil;
import com.qfedu.core.vo.R;
import com.qfedu.domain.oss.OSSPo;
import com.qfedu.service.user.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *@Author feri
 *@Date Created in 2018/7/27 12:07
 */
@RestController
public class OssController {
    @Autowired
    private OSSUtil ossUtil;
    @Autowired
    private UserDetailService service;
    //文件上传
    @PostMapping("/fileup")
    public R fileup(@RequestParam("feri") CommonsMultipartFile file){
        String fn=FileUtils.createFileName(file.getOriginalFilename());
        String url=ossUtil.fileUp(fn,file.getBytes());
        OSSPo po=new OSSPo();
        po.setObjname(fn);
        po.setOurl(url);
        po.setPeriod(-1L);
        service.save(po);
        return new R(0,url,null);
    }

}
