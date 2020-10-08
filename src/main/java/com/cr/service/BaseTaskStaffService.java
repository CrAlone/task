package com.cr.service;
import com.cr.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseTaskStaffService {
    /**
     * 根据工号和密码进行登录操作
     * @param workCode 工号
     * @param password 密码
     * @return 如果登录成功则返回一个对应 失败则返回为null
     */
    TaskStaff login(String workCode,String password);
    /**
     * 查询所有的任务等级
     * @return 返回所有的任务等级
     */
    List<Level> findAllLevel();

    /**
     * 查询所有的用户信息
     * @return 返回所有的任务信息
     */
    List<TaskStaff> findAllStaff();

    /**
     * 查询所有的任务状态
     * @return 返回所有的任务状态
     */
    List<State> findAllState();

    /**
     * 插入一条info信息
     * @param info 要插入的信息
     * @return 成功true 失败false
     */
    boolean insertInfo(Info info);

    /**
     * 查询当前月份的所有任务信息
     * @param year 当前年份
     * @param month 当前月份
     * @param staff_id 当前用户的id
     * @return 返回所有的任务
     */
    List<Info> selectAllMonthTask(String year, String month, int staff_id);

    /**
     * 查询当天任务
     * @param page 页数起始值
     * @param rows 总共的页数
     * @param staff_id 当前用户的id
     * @return 返回当天任务
     */
    List<Info> selectSameDay(int page,int rows,int staff_id);

    /**
     * 查询当天任务的数量
     * @param staff_id 当前用户的id
     * @return 放回当天所有任务的数量
     */
    int selectSameDayCount(int staff_id);

    /**
     * 查询当月任务
     * @param page 页数的起始值
     * @param rows 总共的页数
     * @param staff_id 当前用户的id
     * @return 放回当月的任务
     */
    List<Info> selectSameMonth(int page,int rows,int staff_id);

    /**
     * 查询当月任务的数量
     * @param staff_id 当前任务的id
     * @return 放回当前任务的数量
     */
    int selectSameMonthCount(int staff_id);

    /**
     * 查询所有的部门
     * @return 返回所有的部门
     */
    List<TaskDepartment> selectFindAllDepartment();

    /**
     * 根据部门id查询部门人员
     * @param department_id 部门id
     * @return 对应的人员信息
     */
    List<TaskStaff> selectFindAllTaskStaff(int department_id);

    /**
     * 设计一个方法根据条件查询任务
     * @param start 从什么时间
     * @param end 到什么时间
     * @param staff_id 用户id
     * @return
     */
    List<Info> selectTask(String start,String end,int staff_id);

    /**
     * 设计一个方法用来修改密码
     * @param old 原有密码
     * @param newPass 新密码
     * @param staff_id 用户id
     * @return 成功true 失败false
     */
    boolean updateModify(String old,String newPass,int staff_id);
    /**
     * 删除一个任务记录
     * @param info_id 任务id
     * @param staff_id 用户id
     * @return 成功true 失败false
     */
    boolean updateRemove(String info_id,int staff_id);

    /**
     * 查询一条任务记录
     * @param info_id 任务id
     * @param staff_id 用户id
     * @return 返回一条任务信息
     */
    Info selectTaskInfo(String info_id,int staff_id);

    /**
     * 设计一个方法用来修改完成时间
     * @param info_id 对应的任务id
     * @param info_end_date 完成时间
     * @param staff_id 用户id
     * @boolean true 成功 false 失败
     */
    boolean updatedCompletionTime(String info_id,String info_end_date,int staff_id);

    /**
     * 设计一个方法用来批量删除
     * @param ids 删除的任务id
     * @param staff_id 用户id
     * @return 成功true 失败false
     */
    boolean deleteInfo(String[] ids,int staff_id);

    /**
     * 设计一个方法用来条件查找任务信息
     * @param staff_id 任务接受人id
     * @param start 开始时间
     * @param end 结束时间
     * @return 满足条件的任务信息
     */
    List<Info> selectGenerate(String staff_id,String start,String end);

    /**
     * 设计一个方法用来查询是否是管理员
     * @param staff_id 人员id
     * @return 1是 0不是
     */
    TaskStaff selectAdministrators(String staff_id);

    /**
     * 设计一个方法用来添加管理人员
     * @param staff_id 人员id
     * @return 1成功
     */
    int updateAddJurisdiction(String staff_id);

    /**
     * 设计一个方法用来删除管理人员
     * @param staff_id 人员id
     * @return 1成功
     */
    int updateDeleteJurisdiction(String staff_id);

    /**
     * 设计一个方法用判断邮箱是否存在
     * @param staff_receiveMail
     * @return true 存在 false不存在
     */
    TaskStaff selectRetrievePassword(String staff_receiveMail);

    /**
     * 设计一个方法用来修改密码
     * @param pass 新密码
     * @param staff_id 修改对象的id
     * @return 成功1
     */
    boolean updatePassword(String pass,int staff_id);


}
