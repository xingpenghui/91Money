package com.qfedu.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/8 09:33
 */
public class ES_Main1 {
    //
    private static String clustername="es01";
    private static String host="10.8.155.34";
    private static int port=9300;
    public static void main(String[] args) throws Exception{
        //connect();
        crud();
    }
    //连接ES服务器
    private static void connect() throws Exception{
        //1、构建设置信息  需要指定集群名称
       Settings settings= Settings.builder().put("cluster.name",clustername).build();
       //2、创建客户端对象
       TransportClient client=new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(host),port));
       //获取所有节点
        List<DiscoveryNode> nodes=client.connectedNodes();
        for(DiscoveryNode n:nodes){
            System.err.println(n.getName()+"-->"+n.getHostName());
        }
        //4、关闭
        client.close();
    }
    //索引的CRUD
    private static void crud() throws Exception{
        //1、构建设置信息  需要指定集群名称
        Settings settings= Settings.builder().put("cluster.name","es01").build();
        //2、创建客户端对象
        TransportClient client=new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(host),port));
        //3、新建索引
//        JSONObject object=new JSONObject();
//        object.put("title","娶妻生子");
//        object.put("money",2000000);
        Loan loan=new Loan(1003,"学习深造",880000);
        JSONObject object=JSONObject.parseObject(JSON.toJSONString(loan));
        //新增索引
//        IndexResponse response=client.prepareIndex("loans","loan",loan.getId()+"").
//                setSource(object,XContentType.JSON).get();
//        System.err.println("添加数据："+response.getIndex());

        //查询索引
//        GetResponse response1=client.prepareGet("loans","loan","1001").get();
//        System.err.println("查询索引："+response1.getSourceAsString());

        //修改索引
//        UpdateResponse response=client.prepareUpdate("loans","loan","1001").setDoc(object,XContentType.JSON).get();
//        System.err.println("修改索引："+response.status().toString());
        //删除索引
        DeleteResponse response1=client.prepareDelete("feri","books","1001").get();
        System.err.println("删除索引："+response1.status().toString());

        //4、关闭
        client.close();


    }
}
