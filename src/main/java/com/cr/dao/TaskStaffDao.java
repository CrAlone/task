package com.cr.dao;

import com.cr.domain.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface TaskStaffDao {
    //设计一个方法用来登录
    @Results(id = "selectOne",value = {
            @Result(property = "staff_id",column = "staff_id",id = true),
            @Result(property = "staff_name",column = "staff_name"),
            @Result(property = "staff_workCode",column = "staff_workcode"),
            @Result(property = "staff_password",column = "staff_password"),
            @Result(property = "staff_ads",column = "staff_ads"),
            @Result(property = "department",javaType = TaskDepartment.class,column = "department_id",one = @One(select = "selectOrderByDepartmentId",fetchType = FetchType.EAGER))
    })
    @Select("SELECT STAFF_ID,DEPARTMENT_ID,STAFF_NAME,staff_workcode,STAFF_PASSWORD,STAFF_ADS FROM TASK_STAFF WHERE STAFF_WORKCODE = #{workCode} and staff_password = #{password}")
    TaskStaff login(@Param("workCode") String workCode, @Param("password") String password);
    //设计一个辅助方法用来查询department
    @Select("SELECT DEPARTMENT_ID,DEPARTMENT_NAME,DEPARTMENT_CODE FROM TASK_DEPARTMENT WHERE DEPARTMENT_ID = #{department_id}")
    TaskDepartment selectOrderByDepartmentId(Integer department_id);



    //查询所有等级
    @Select("SELECT LEVEL_ID,LEVEL_NAME FROM TASK_LEVEL")
    List<Level> findAllLevel();




    @Select("SELECT STAFF_ID,STAFF_NAME FROM TASK_STAFF")
    //查询所有的个人信息
    List<TaskStaff>  findAllStaff();



    //查询所有的任务状态
    @Select("SELECT STATE_ID,STATE_NAME FROM TASK_STATE")
    List<State> findAllState();


    //设计一个方法用来插入一条数据
    @Insert("insert into task_info(state_id,level_id,staff_id,tas_staff_id,info_name,info_desc,info_create_date,info_end_not_ate) values(" +
            "#{state.state_id},#{level.level_id},#{staff.staff_id},#{taskStaff.staff_id},#{info_name},#{info_desc},#{info_create_date},#{info_end_not_ate})")
    int insertInto(Info info);



    //设计一个方法查询当前月的所有任务信息
    @Results(id = "selectInfo", value = {
            @Result(property = "info_id",column = "info_id",id = true),
            @Result(property = "info_name",column = "info_name"),
            @Result(property = "info_desc",column = "info_desc"),
            @Result(property = "info_create_date",column = "info_create_date"),
            @Result(property = "info_end_not_ate",column = "info_end_not_ate"),
            @Result(property = "info_end_date",column = "info_end_date"),

            @Result(property = "state",javaType = State.class,column = "state_id",one = @One(select = "selectState",fetchType = FetchType.LAZY)),
            @Result(property = "level",javaType = Level.class,column = "level_id",one = @One(select = "selectLevel",fetchType = FetchType.LAZY)),
            @Result(property = "staff",javaType = TaskStaff.class,column = "staff_id",one = @One(select = "selectStaff",fetchType = FetchType.LAZY)),
            @Result(property = "taskStaff",javaType = TaskStaff.class,column = "tas_staff_id",one = @One(select = "selectStaff",fetchType = FetchType.LAZY))
    })
    @Select("SELECT * FROM TASK_INFO WHERE YEAR(STR_TO_DATE(info_create_date,'%Y-%m-%d'))=#{year} AND MONTH(str_to_date(info_create_date,'%Y-%m-%d'))=#{month} AND STAFF_ID=#{staff_id}")
    List<Info> selectAllMonthTask(@Param("year") String year,@Param("month") String month,@Param("staff_id") int staff_id);
    //辅助查询方法
    @Select("select * from task.task_state where state_id = #{state_id}")
    State selectState(int state_id);
    @Select("select * from task.task_level where level_id = #{level_id}")
    Level selectLevel(int level_id);
    @Select("SELECT * FROM task.task_staff WHERE staff_id = #{staff_id}")
    TaskStaff selectStaff(int staff_id);



    //设计一个方法用来查询某人当天的所有任务信息
    @ResultMap("selectInfo")
    @Select("select info_id,state_id,level_id,staff_id,tas_staff_id,info_name,info_name,info_desc,info_create_date,info_end_not_ate,info_end_date from task_info where str_to_date(info_create_date,'%Y-%m-%d')=str_to_date(now(),'%Y-%m-%d') and staff_id = #{staff_id} limit #{page},#{rows}")
    List<Info> selectSameDay(@Param("page") int page,@Param("rows") int rows,@Param("staff_id") int staff_id);


    //设计一个方法查询总数
    @Select("select count(*) from task_info where str_to_date(info_create_date,'%Y-%m-%d')=str_to_date(now(),'%Y-%m-%d') and staff_id=#{staff_id}")
    int selectSameDayCount(int staff_id);



    //设计一个方法用来查询当前用户的所有任务信息
    @ResultMap("selectInfo")
    @Select("select info_id,state_id,level_id,staff_id,tas_staff_id,info_name,info_name,info_desc,info_create_date,info_end_not_ate,info_end_date from task_info where month(str_to_date(info_create_date,'%Y-%m-%d'))=month(str_to_date(now(),'%Y-%m-%d')) and year(str_to_date(info_create_date,'%Y-%m-%d'))=year(str_to_date(now(),'%Y-%m-%d')) and staff_id = #{staff_id} limit #{page},#{rows}")
    List<Info> selectSameMonth(@Param("page") int page,@Param("rows") int rows,@Param("staff_id") int staff_id);


    //设计一个方法用来查询当月任务的总数
    @Select("select count(*) from task_info where month(str_to_date(info_create_date,'%Y-%m-%d'))=month(str_to_date(now(),'%Y-%m-%d')) and staff_id=#{staff_id}")
    int selectSameMonthCount(int staff_id);


    //设计一个方法用来查询所有的部门
//    @Results(id = "selectDepartment",value = {
//            @Result(property = "department_id",column = "department_id",id = true),
//            @Result(property = "department_name",column = "department_name"),
//            @Result(property = "department_code",column = "department_code"),
//            @Result(property = "staffs",javaType = java.util.List.class,column = "department_id",many = @Many(select = "selectsTaskStaffAll",fetchType = FetchType.LAZY))
//    })
    @Select("select * from task_department")
    List<TaskDepartment> selectFindAllDepartment();
    //辅助查询
//    @Select("select * from task_staff where department_id = #{department_id}")
//    List<TaskStaff> selectsTaskStaffAll(int department_id);



    //根据部门id查询部门人员
    @Select("select * from task_staff where department_id = #{department_id}")
    List<TaskStaff> selectFindAllTaskStaff(int department_id);


    //设计一个方法根据条件来查找任务
    @SelectProvider(type = InfoDynamicSql.class,method = "InfoSql")
    @ResultMap("selectInfo")
    List<Info> selectTask(@Param("start") String start,@Param("end") String end,@Param("staff_id") int staff_id);



    //设计一个方法用来修改密码
    @Update("update task_staff set staff_password = #{newPass} where staff_id = #{staff_id} and staff_password = #{old}")
    int updateModify(@Param("old") String old,@Param("newPass") String newPass,@Param("staff_id") int staff_id);



    //设计一个方法用来删除一个任务列表
    @Update("delete from task_info where info_id = #{info_id} and staff_id = #{staff_id}")
    int updateRemove(@Param("info_id") int info_id,@Param("staff_id") int staff_id);



    //查询一条任务信息
    @ResultMap("selectInfo")
    @Select("select * from task_info where info_id = #{info_id} and staff_id = #{staff_id}")
    Info selectTaskInfo(@Param("info_id") int info_id,@Param("staff_id") int staff_id);



    //设计一个方法用来修改完成时间
    @Update("update task_info set info_end_date = #{info_end_date} where info_id = #{info_id} and staff_id = #{staff_id}")
    int updatedCompletionTime(@Param("info_id") int info_id,@Param("info_end_date")String info_end_date,@Param("staff_id")int staff_id);



    //设计一个方法用来批量修改
    @SelectProvider(type = InfoDynamicSql.class,method = "delete")
    void deleteInfo(@Param("ids") String[] ids, @Param("staff_id")int staff_id);


    //设计一个方法根据条件筛选任务信息
    @ResultMap("selectInfo")
    @SelectProvider(type = InfoDynamicSql.class,method = "selectGenerate")
    List<Info> selectGenerate(@Param("staff_id") int staff_id,@Param("start") String start,@Param("end") String end);


    //设计一个方法用来添加权限人员
    @Update("update task_staff set staff_ads = 1 where staff_id = #{staff_id}")
    int updateAddJurisdiction(int staff_id);


    //设计一个方法用来删除权限人员
    @Update("update task_staff set staff_ads = 0 where staff_id = #{staff_id}")
    int updateDeleteJurisdiction(int staff_id);


    //设计一个方法查找是否是管理员（获取邮箱地址）
    @Select("select * from task_staff where staff_id = #{staff_id}")
    TaskStaff selectAdministrators(int staff_id);

    //设计一个方法判断邮箱是否正确
    //设计一个方法查找是否是管理员（获取邮箱地址）
    @Select("select * from task_staff where staff_receiveMail = #{staff_receiveMail}")
    TaskStaff selectRetrievePassword(String staff_receiveMail);


    //设计一个方法用来修改密码
    @Update("update task_staff set staff_password = #{pass} where staff_id = #{staff_id}")
    int updatePassword(@Param("pass") String pass,@Param("staff_id") int staff_id);
}
