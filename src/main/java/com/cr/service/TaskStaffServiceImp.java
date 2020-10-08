package com.cr.service;


import com.cr.dao.TaskStaffDao;

import com.cr.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskStaffServiceImp implements BaseTaskStaffService{
    @Resource
    private TaskStaffDao staffDao;
    //dao的get和set方法
    public TaskStaffDao getStaffDao() {
        return staffDao;
    }
    public void setStaffDao(TaskStaffDao staffDao) {
        this.staffDao = staffDao;
    }
    @Override
    public TaskStaff login(String workCode,String password) {
        try {
            return staffDao.login(workCode,password);
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Level> findAllLevel() {
        try{
            return staffDao.findAllLevel();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<TaskStaff> findAllStaff() {
        try{
            return staffDao.findAllStaff();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<State> findAllState() {
        try {
            return staffDao.findAllState();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean insertInfo(Info info) {
        try {
            int i = staffDao.insertInto(info);
            return i==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Info> selectAllMonthTask(String year, String month, int staff_id) {
        try{
            return staffDao.selectAllMonthTask(year,month,staff_id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Info> selectSameDay(int page, int rows,int staff_id) {
        try{
            page = (page-1)*rows;
            return staffDao.selectSameDay(page,rows,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int selectSameDayCount(int staff_id) {
        try {
            return staffDao.selectSameDayCount(staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Info> selectSameMonth(int page, int rows, int staff_id) {
        try {
            page = (1-page)*rows;
            return staffDao.selectSameMonth(page,rows,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int selectSameMonthCount(int staff_id) {
        try {
            return staffDao.selectSameMonthCount(staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TaskDepartment> selectFindAllDepartment() {
        try {
            return staffDao.selectFindAllDepartment();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaskStaff> selectFindAllTaskStaff(int department_id) {
        try {
            return staffDao.selectFindAllTaskStaff(department_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Info> selectTask(String start, String end, int staff_id) {
        try {
            return staffDao.selectTask(start,end,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateModify(String old, String newPass, int staff_id) {
        int i = staffDao.updateModify(old,newPass,staff_id);
      return 1==i;
    }

    @Override
    public boolean updateRemove(String info_id, int staff_id) {
        int id = Integer.parseInt(info_id);
        try {
            int i = staffDao.updateRemove(id,staff_id);
            if (i == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Info selectTaskInfo(String info_id, int staff_id) {
        int id = Integer.parseInt(info_id);
        try {
            return staffDao.selectTaskInfo(id,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatedCompletionTime(String info_id, String info_end_date, int staff_id) {
        int id = Integer.parseInt(info_id);
        try {
            int i = staffDao.updatedCompletionTime(id,info_end_date,staff_id);
            System.out.println(i);
            if (i == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteInfo(String[] ids, int staff_id) {
        try {
            staffDao.deleteInfo(ids,staff_id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Info> selectGenerate(String staff_id, String start, String end) {
        try {
            int id = 0;
            if (staff_id != null && !"".equals(staff_id)){
                id = Integer.parseInt(staff_id);
            }
            return staffDao.selectGenerate(id,start,end);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TaskStaff selectAdministrators(String staff_id) {
        try {
            int id = Integer.parseInt(staff_id);
            return staffDao.selectAdministrators(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateAddJurisdiction(String staff_id) {
        try {
            int id = Integer.parseInt(staff_id);
            return staffDao.updateAddJurisdiction(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateDeleteJurisdiction(String staff_id) {
        try {
            int id = Integer.parseInt(staff_id);
            return staffDao.updateDeleteJurisdiction(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public TaskStaff selectRetrievePassword(String staff_receiveMail) {
        try {
           return staffDao.selectRetrievePassword(staff_receiveMail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePassword(String pass, int staff_id) {
        try {
            int i = staffDao.updatePassword(pass,staff_id);
            if (i == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
