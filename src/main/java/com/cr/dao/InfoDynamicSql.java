package com.cr.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Iterator;

public class InfoDynamicSql {
    public String InfoSql(@Param("start") String start,@Param("end") String end,@Param("staff_id") int staff_id){
        String sql = "select * from task_info where staff_id = #{staff_id}";
        if (start != null && !"".equals(start)){
            sql += " and info_create_date >= #{start}";
        }
        if (end !=null && !"".equals(end)){
            sql += " and info_create_date <= #{end}";
        }
        return sql;
    }
    public String delete(@Param("ids") String[] ids, @Param("staff_id") int staff_id){
        String sql = "delete from task_info where staff_id = #{staff_id}";
        if (ids.length > 0){
            sql += " and info_id in(";
//            Iterator it = map.keySet().iterator();
//            while (it.hasNext()){
//                String key = (String) it.next();
//                String value = (String) map.get(key);
//                sql += ""+value;
//                sql += ",";
            for (int i =0;i<ids.length;i++){
                sql += ""+ids[i];
                sql += ",";
            }
            sql = sql.substring(0,sql.length()-1);
            sql += ")";
        }
        return sql;
    }
    public String selectGenerate(@Param("staff_id") int staff_id,@Param("start") String start,@Param("end") String end){
        StringBuilder builder = new StringBuilder();
        builder.append("select * from task_info where 1 = 1");
        if (staff_id != 0){
            builder.append(" and staff_id = #{staff_id}");
        }
        if (start != null && !"".equals(start)){
            builder.append(" and info_create_date >= #{start}");
        }
        if (end != null && !"".equals(end)){
            builder.append(" and info_create_date <= #{end}");
        }
        return builder.toString();
    }
}
