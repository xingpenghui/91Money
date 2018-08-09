package com.qfedu.oracle;

import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *@Author feri
 *@Date Created in 2018/8/6 15:56
 */
public class OJdbc_Main {
    public static void main(String[] args) throws Exception {
        String url="jdbc:oracle:thin:@localhost:1521:zzjava";
        String name="system";
        String pass="qfjava";
        //1、加载驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2、获取连接
        Connection connection=DriverManager.getConnection(url,name,pass);
        //3、获取操作sql对象
        Statement statement=connection.createStatement();
        //4、操作sql
       // statement.executeUpdate("create table t_msg(id number primary key,msg varchar2(50),mtime date)");
        statement.executeUpdate("insert into t_msg(id,msg,mtime) values(11,'下课了',to_date('2018-08-20','yyyy-mm-dd'))");
        ResultSet resultSet=statement.executeQuery("select * from t_msg");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id")+"--->"+resultSet.getString("msg")+"---->"+resultSet.getDate("mtime"));
        }
        //5、关闭
        resultSet.close();
        statement.close();
        connection.close();

    }
}
