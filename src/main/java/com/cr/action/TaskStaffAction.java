package com.cr.action;


import com.cr.domain.*;
import com.cr.service.BaseTaskStaffService;
import com.cr.util.ExcelTool;
import com.cr.util.Mail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/staff")
public class TaskStaffAction {
    private int number;
    private BaseTaskStaffService taskStaffServiceImp;
    //创建service的get和set方法
    public BaseTaskStaffService getTaskStaffServiceImp() {
        return taskStaffServiceImp;
    }
    @Resource
    public void setTaskStaffServiceImp(BaseTaskStaffService taskStaffServiceImp) {
        this.taskStaffServiceImp = taskStaffServiceImp;
    }
    @RequestMapping("/login")
    @ResponseBody
    public Message login(HttpServletRequest request, HttpSession session) throws IOException{
        String task_workCode = request.getParameter("task_workCode");
        String task_password = request.getParameter("task_password");
        TaskStaff staff =  taskStaffServiceImp.login(task_workCode,task_password);
        Message message = new Message();
        if (staff != null){
            message.setData(staff);
            message.setMsg("欢迎"+staff.getStaff_name()+"操作成功");
            message.setCode(200);
            session.setAttribute("user",staff);
        }else {
            message.setData(null);
            message.setMsg("对不起，账号或密码错误");
            message.setCode(400);
        }
        return message;
    }
    @RequestMapping("/findAllLevel")
    @ResponseBody
    //获取所有的任务等级
    public List<Level> selectLevelAll(HttpServletRequest request, HttpSession session)throws IOException{
        List<Level> levels = taskStaffServiceImp.findAllLevel();
        return levels;
    }
    //获取session对象
    @RequestMapping("/session")
    @ResponseBody
    public TaskStaff getUser(HttpSession session)throws IOException{
        return (TaskStaff) session.getAttribute("user");
    }
    //设计一个方法用来查询所有的部门
    @RequestMapping("/findAllDepartment")
    @ResponseBody
    public List<TaskDepartment> findAllDepartment(HttpServletRequest request,HttpSession session)throws IOException{
        List<TaskDepartment> list = taskStaffServiceImp.selectFindAllDepartment();
        return list;
    }
    //查询所有的状态
    @RequestMapping("/findAllState")
    @ResponseBody
    public List<State> findAllState(HttpServletRequest request,HttpSession session)throws IOException{
        List<State> states = taskStaffServiceImp.findAllState();
        return states;

    }
    //设计一个方法用来查询
    //插入一条任务表单记录
    @RequestMapping("/insertInfo")
    @ResponseBody
    public Message insertInfo(HttpServletRequest request,HttpSession session)throws IOException{
        request.setCharacterEncoding("UTF-8");
        Message message = new Message();
        Info info = new Info();
        //接受前端发送过来的数据
        //创建表格的时间
        String info_create_date = request.getParameter("info_create_date");
        //待完成时间
        String info_end_not_ate = request.getParameter("info_end_not_ate");
        //派发任务的人名称(可以之间从session中获取)
        TaskStaff staff = getUser(session);
        //任务名称
        String into_name = request.getParameter("into_name");
        //接受任务的人是谁
        String staff_id = request.getParameter("staff_id");
        //任务级别
        String task_level = request.getParameter("task_level");
        //任务状态
        String task_state = request.getParameter("task_state");
        //任务说明
        String info_desc = request.getParameter("info_desc");
        //将所有的数据装成一个对象
        //将表格创建时间存入对象中
        info.setInfo_create_date(info_create_date);
        //将待完成时间存入对象中
        info.setInfo_end_not_ate(info_end_not_ate);
        //将派发任务的对象存入对象中
        info.setStaff(staff);
        //将任务名称存入对象中
        info.setInfo_name(into_name);
        //判断接受人是否为null，若为null则是自己
        if (staff_id == null){
            info.setTaskStaff(staff);
        }else {
            TaskStaff task = taskStaffServiceImp.selectAdministrators(staff_id);
            TaskStaff taskStaff = new TaskStaff();
            taskStaff.setStaff_id(Integer.parseInt(staff_id));
            info.setTaskStaff(taskStaff);
            Mail mail = new Mail();
            String content = "您好，您有新的任务，详细请访问：<a href='http://localhost:8080/task/login.html'>任务跟踪管理系统</a>";
            mail.sendHTMLMail("新任务提示！",content,task.getStaff_receiveMail());
        }
        //将任务级别id存入对象中
        Level level = new Level();
        if (task_level != null){
            level.setLevel_id(Integer.parseInt(task_level));
        }
        info.setLevel(level);
        //将任务状态存入对象中
        State state = new State();
        if (task_state != null){
            state.setState_id(Integer.parseInt(task_state));
        }
        info.setState(state);
        //将任务说明存入对象中
        info.setInfo_desc(info_desc);
        //将对象传送给service，true成功 false失败
        if (taskStaffServiceImp.insertInfo(info)){
            message.setMsg("操作成功");
            message.setCode(200);
        }else {
            message.setMsg("操作失败，请联系管理员!ErrorCode:000001");
            message.setCode(400);
        }
        return message;
    }
    //查询当年当月的所有任务
    @RequestMapping("/selectAllMonthTask")
    @ResponseBody
    public List<Info> selectAllMonthTask(HttpServletRequest request,HttpSession session)throws IOException{
        //获取当前年份
        String year = request.getParameter("year");
        //获取当前月份
        String month = request.getParameter("month");
        //获取session中的用户对于的id
        TaskStaff staff = getUser(session);
        int staff_id = staff.getStaff_id();
        List<Info> list = taskStaffServiceImp.selectAllMonthTask(year,month,staff_id);
        return list;
    }
    //查询当天任务
    @RequestMapping("/selectSameDay")
    @ResponseBody
    public List<Info> selectSameDay(HttpServletRequest request,HttpSession session) throws IOException{
        //获取页数起始值
        Map<String,Object> map = new HashMap<>();
        String page = request.getParameter("page");
        //获取页数
        String rows = request.getParameter("rows");
        //获取登录人的信息
        TaskStaff staff = getUser(session);
        List<Info> info =  taskStaffServiceImp.selectSameDay(Integer.parseInt(page),Integer.parseInt(rows),staff.getStaff_id());
        map.put("info",info);
        //查询任务总数
        int count = taskStaffServiceImp.selectSameDayCount(staff.getStaff_id());
        map.put("count",count);
        return info;
    }
    //查询当月的任务
    @RequestMapping("/selectSameMonth")
    @ResponseBody
    public List<Info> selectSameMonth(HttpServletRequest request,HttpSession session)throws IOException{
        Map<String,Object> map = new HashMap<>();
        //获取页数起始值
        String page = request.getParameter("page");
        //获取页数
        String rows = request.getParameter("rows");
        //获取登录人的信息
        TaskStaff staff = getUser(session);
        List<Info> info = taskStaffServiceImp.selectSameMonth(Integer.parseInt(page),Integer.parseInt(rows),staff.getStaff_id());
        map.put("info",info);
        //查询当月任务的数量
        int count = taskStaffServiceImp.selectSameMonthCount(staff.getStaff_id());
        map.put("count",count);
        return info;
    }
    //设计一个方法根据部门id查询部门中的人员
    @RequestMapping("/findAllTaskStaff")
    @ResponseBody
    public List<TaskStaff> findAllTaskStaff(HttpServletRequest request,HttpSession session)throws IOException{
        String department_id = request.getParameter("department_id");
        return taskStaffServiceImp.selectFindAllTaskStaff(Integer.parseInt(department_id));
    }
    //根据时间查询
    @RequestMapping("/selectTask")
    @ResponseBody
    public List<Info> selectTask(HttpServletRequest request,HttpSession session)throws IOException{
        //获取起始值
        String start = request.getParameter("start");
        System.out.println(start);
        String end = request.getParameter("end");
        System.out.println(end);
        TaskStaff staff = getUser(session);
        return taskStaffServiceImp.selectTask(start,end,staff.getStaff_id());
    }
    //设计一个方法用来修改密码
    @RequestMapping("/updateModify")
    @ResponseBody
    public Message updateModify(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取原来的密码
        String old = request.getParameter("old");
        //获取新的密码
        String newPass = request.getParameter("newPass");
        //获取登录人的id
        TaskStaff staff = getUser(session);
        boolean flag = taskStaffServiceImp.updateModify(old,newPass,staff.getStaff_id());
        if (flag){
            message.setMsg("修改成功,请重新登录");
            message.setCode(200);
        }else {
            message.setMsg("密码不正确，请重新输入");
            message.setCode(400);
        }
        return message;
    }
    //设计一个方法用来删除任务
    @RequestMapping("/updateRemove")
    @ResponseBody
    public Message updateRemove(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取任务id
        String info_id = request.getParameter("info_id");
        //获取用户信息
        TaskStaff staff = getUser(session);
        boolean flag = taskStaffServiceImp.updateRemove(info_id,staff.getStaff_id());
        if (flag){
            message.setMsg("删除成功");
            message.setCode(200);
        }else {
            message.setMsg("删除失败");
            message.setCode(400);
        }
        return message;
    }
    //设计一个方法用来查询一条任务信息
    @RequestMapping("/selectTaskInfo")
    @ResponseBody
    public Message selectTaskInfo(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取任务id
        String info_id = request.getParameter("info_id");
        //获取用户信息
        TaskStaff staff = getUser(session);
        Info info = taskStaffServiceImp.selectTaskInfo(info_id,staff.getStaff_id());
        if (info != null){
            message.setCode(200);
            message.setData(info);
        }else {
            message.setCode(400);
        }
        return message;
    }
    //设计一个方法用来修改任务中的完成时间
    @RequestMapping("/updatedCompletionTime")
    @ResponseBody
    public Message updatedCompletionTime(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取任务对应的id
        String info_id_ = request.getParameter("info_id_");
        //获取完成时间
        String info_end_date = request.getParameter("info_end_date");
        //获取用户信息
        TaskStaff staff = getUser(session);
        boolean flag = taskStaffServiceImp.updatedCompletionTime(info_id_,info_end_date,staff.getStaff_id());
        if (flag){
            message.setMsg("修改成功");
            message.setCode(200);
        }else {
            message.setMsg("修改失败");
            message.setCode(400);
        }
        return message;
    }
    //设计一个方法用来批量删除
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Message deleteBatch(HttpServletRequest request,HttpSession session,String[] ids)throws IOException{
        Message message = new Message();
//        Enumeration list = request.getParameterNames();
//        Map<String,Object> map = new HashMap<>();
//        while (list.hasMoreElements()){
//            String key = (String) list.nextElement();
//            String value = request.getParameter(key);
//        }
        //获取用户信息
        TaskStaff staff = getUser(session);
//        boolean flag = taskStaffServiceImp.deleteInfo(map,staff.getStaff_id());

        boolean flag = taskStaffServiceImp.deleteInfo(ids,staff.getStaff_id());

        if (flag) {
            message.setMsg("删除成功");
            message.setCode(200);
        }else {
            message.setMsg("删除失败");
            message.setCode(400);
        }
        return message;
    }
    //设计一个方法条件筛选任务信息
    @RequestMapping("/selectGenerate")
    @ResponseBody
    public Message selectGenerate(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取接收人id
        String taskStaff = request.getParameter("dept_id");
        //获取日期
        String start = request.getParameter("start");
        //获取日期
        String end  = request.getParameter("end");
        List<Info> infos = taskStaffServiceImp.selectGenerate(taskStaff,start,end);
        if (infos != null){
            message.setCode(200);
            message.setData(infos);
        }else {
            message.setCode(400);
        }
        return message;
    }
    /**
     * 导出数据到excel
     * @param session
     * @param  request
     * @throws IOException
     */
    @RequestMapping("/exportDataToExcel")
    public void exportDataToExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response)throws IOException{
        //获取时间参数
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        //获取用户信息
        TaskStaff staff = getUser(session);
        List<Info> list = taskStaffServiceImp.selectTask(start,end,staff.getStaff_id());
        String sheetName = "个人任务";
        //excel workbook poi/jxl 创建工具类
        String[] title = {"编号","任务状态","任务级别","派发人","接收人","任务名称","任务介绍","创建日期","待完成日期","已完成日期"};
        String[][] data = new String[list.size()][];
        for (int i =0;i<data.length;i++){
            Info info = list.get(i);
            data[i] = new String[title.length];
            data[i][0] = info.getInfo_id()+"";
            data[i][1] = info.getState().getState_name();
            data[i][2] = info.getLevel().getLevel_name();
            data[i][3] = info.getStaff().getStaff_name();
            data[i][4] = info.getTaskStaff().getStaff_name();
            data[i][5] = info.getInfo_name();
            data[i][6] = info.getInfo_desc();
            data[i][7] = info.getInfo_create_date();
            data[i][8] = info.getInfo_end_not_ate();
            data[i][9] = info.getInfo_end_date();
        }
        HSSFWorkbook workbook = ExcelTool.createHSSFWorkbook(sheetName,title,data);
        String fileName = "个人任务报表.xls";
        setResponseHeader(response,fileName);
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }
    private void setResponseHeader(HttpServletResponse response,String fileName){
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition","attachment);filename="+fileName);
            response.addHeader("Pargam","no-cache");
            response.addHeader("Cache-Control","no-cache");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //设计一个方法用来判断是否是管理员
    @RequestMapping("/selectAdministrators")
    @ResponseBody
    public int selectAdministrators(HttpServletRequest request,HttpSession session)throws IOException{
        String staff_id = request.getParameter("staff_id");
        TaskStaff staff =  taskStaffServiceImp.selectAdministrators(staff_id);
        return staff.getStaff_ads();
    }
    //设计一个方法用来添加权限
    @RequestMapping("/updateAddJurisdiction")
    @ResponseBody
    public Message updateAddJurisdiction(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取添加管理的id
        String staff_id = request.getParameter("staff_id");
        int flag = taskStaffServiceImp.updateAddJurisdiction(staff_id);
        if (flag == 0){
            message.setMsg("对不起，添加失败！请重试");
        }else {
            message.setMsg("添加成功");
        }
        return message;
    }
    //设计一个方法用来添加权限
    @RequestMapping("/deleteJurisdiction")
    @ResponseBody
    public Message deleteJurisdiction(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取添加管理的id
        String staff_id = request.getParameter("staff_id");
        int flag = taskStaffServiceImp.updateDeleteJurisdiction(staff_id);
        if (flag == 0){
            message.setMsg("对不起，删除失败！请重试");
        }else {
            message.setMsg("删除成功");
        }
        return message;
    }
    //设计一个方法用来找回密码
    @RequestMapping("/updateRetrievePassword")
    @ResponseBody
    public Message updateRetrievePassword(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取邮箱
        String mailbox = request.getParameter("mailbox");
       //获取验证码
        String verification_code = request.getParameter("verification_code");
       //获取密码
        String pass = request.getParameter("pass");
        TaskStaff staff = taskStaffServiceImp.selectRetrievePassword(mailbox);

        System.out.println(number+"---"+Integer.parseInt(verification_code));
        if (Integer.parseInt(verification_code) == number){
            int staff_id = staff.getStaff_id();
            boolean flag = taskStaffServiceImp.updatePassword(pass,staff_id);
            if (flag){
                message.setMsg("操作成功");
                message.setCode(200);
            }else {
                message.setMsg("邮箱或验证码不正确");
                message.setCode(400);
            }
        }else {
            message.setMsg("邮箱或验证码不正确");
            message.setCode(400);
        }
        return message;
    }
    //检查邮箱是否正确
    @RequestMapping("/sendMailbox")
    @ResponseBody
    public Message sendMailbox(HttpServletRequest request,HttpSession session)throws IOException{
        Message message = new Message();
        //获取邮箱
        String mailbox = request.getParameter("mailbox");
        //判断邮箱是否正确
        TaskStaff staff = taskStaffServiceImp.selectRetrievePassword(mailbox);

        if (staff == null || "".equals(staff)){
            message.setCode(400);
            message.setMsg("邮箱或验证码不正确");
        }else {
            int num = random();
            number = num;
            String content = "您正在使用邮箱认证，验证码为:"+num+",5分钟内有效";
            Mail mail = new Mail();
            mail.sendHTMLMail("密码修改提示",content,mailbox);
            message.setMsg("操作成功");
            message.setCode(200);
        }
        return message;
    }
    /**
     * 用于随机生成100000 - 999999 范围的随机数字
     * @return
     */
    private static int random() {
        Random r = new Random();
        //100000-999999
        int num = r.nextInt(900000)+100000;
        return num;
    }

}
