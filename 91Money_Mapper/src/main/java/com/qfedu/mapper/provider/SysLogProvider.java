package com.qfedu.mapper.provider;


import org.apache.ibatis.jdbc.SQL;
import java.util.Map;
import java.util.Objects;
/**
 *@Author feri
 *@Date Created in 2018/7/30 15:06
 */
public class SysLogProvider {

    public String queryByPage(Map<String,Object> map){
        String sql= new SQL(){
            {
                FROM("sys_log");
                WHERE("id=>2");
                //UPDATE()
               // DELETE_FROM()
                //INSERT_INTO()
               // LEFT_OUTER_JOIN()
                //RIGHT_OUTER_JOIN()
                //INNER_JOIN()
               // ORDER_BY()
                //GROUP_BY()
               // HAVING()
//                OR();
//                WHERE("")
            }

        }.toString();
        Object obj1=map.get("index");
        Object obj2=map.get("size");
        if(obj1!=null && obj2!=null){
            int index=(Integer)obj1;
            int size=(Integer)obj2;
            if(index>=0){
                //sql+=" limit "+index+","+size;
                sql+=" limit #{index},#{size}";
            }
        }
        return sql;
    }
}
