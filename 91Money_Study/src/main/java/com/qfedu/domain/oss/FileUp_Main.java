package com.qfedu.domain.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

/**
 *@Author feri
 *@Date Created in 2018/7/27 11:09
 */
public class FileUp_Main {
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAIhTvqTSmlmjeQ";
    private static String accessKeySecret = "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv";

    private static String bucketName = "feriteach";
    public static void main(String[] args) throws Exception{
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        System.out.println(ossClient);
        System.out.println(ossClient.getBucketInfo(bucketName).getBucket().getName());
        System.out.println(new File("91Money_Study/2017110_1609406.jpg").getAbsolutePath());
// 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        //ossClient.putObject(bucketName, "Obj1", new File("2017110_1609406.jpg"));
// 关闭OSSClient。
        //String content = "Hello OSS";
        File file=new File("91Money_Study/abc.jpg");
        FileInputStream fis=new FileInputStream(file);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int len=0;
        while ((len=fis.read(data))!=-1){
            baos.write(data,0,len);
        }

        PutObjectResult result=ossClient.putObject(bucketName, file.getName(), new ByteArrayInputStream(baos.toByteArray()));
//        result.setCallbackResponseBody();

        ossClient.shutdown();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,3);
        System.out.println("URL:"+ossClient.generatePresignedUrl(bucketName,file.getName(),calendar.getTime()));

    }
}
