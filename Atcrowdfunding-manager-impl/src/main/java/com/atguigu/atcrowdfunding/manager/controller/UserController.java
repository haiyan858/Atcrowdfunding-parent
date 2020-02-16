package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Cont;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-07 13:04
 * @Description:
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    //分配角色
    @ResponseBody
    @RequestMapping("/doAssignRole")
    public AjaxResult doAssignRole(Integer userid,Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = userService.saveUserRoleRelationship(userid,data);
            ajaxResult.setSuccess(count == data.getIds().size());
            ajaxResult.setMessage("分配角色成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("分配角色失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    //取消分配角色
    @ResponseBody
    @RequestMapping("/doUnAssignRole")
    public AjaxResult doUnAssignRole(Integer userid,Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = userService.deleteUserRoleRelationship(userid,data);
            ajaxResult.setSuccess(count == data.getIds().size());
            ajaxResult.setMessage("取消分配角色成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("取消分配角色失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


    //同步请求用map
    @RequestMapping("/assignRole")
    public String assignRole(Integer id, Map map) {
        List<Integer> roleIds = userService.queryRoleByUserId(id);
        List<Role> allListRole = userService.queryAllRole();

        List<Role> leftRoleList = new ArrayList<>(); //已分配角色
        List<Role> rightRoleList = new ArrayList<>(); //未分配角色

        for (Role role: allListRole){
            if (roleIds.contains(role.getId())){
                rightRoleList.add(role);
            }else {
                leftRoleList.add(role);
            }
        }

        map.put("leftRoleList",leftRoleList);
        map.put("rightRoleList",rightRoleList);
        return "user/assignRole";
    }


    //接收多条数据
    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = userService.deleteBatchUserByVO(data);
            ajaxResult.setSuccess(count == data.getDatas().size());
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    /*
    //接收一个参数名带多个值
    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Integer[] id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = userService.deleteBatchUserById(id);
            ajaxResult.setSuccess(count == id.length);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }*/


    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = userService.deleteUserById(id);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


    //异步请求
    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(User user) {
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = userService.updateUser(user);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("更新成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("更新失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


    //同步请求用map
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map map) {
        User user = userService.getUserById(id);
        map.put("user", user);
        return "/user/update";
    }


    //异步请求
    @ResponseBody
    @RequestMapping("/doAdd")
    //public Object doAdd(String floginacct, String fusername, String femail) {
    public Object doAdd(User user) {
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = userService.save(user);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("保存成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/user/add";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    //异步请求
    @RequestMapping("/index")
    public String index() {
        return "/user/index";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object queryUserPage(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                                @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
                                String queryText) {

        AjaxResult ajaxResult = new AjaxResult();

        try {
            Map paramMap = new HashMap();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);

            if (StringUtils.isNotEmpty(queryText)) {
                if (queryText.contains("%")) {
                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                paramMap.put("queryText", queryText);
            }
            Page page = userService.queryPage(paramMap);
            ajaxResult.setSuccess(true);
            ajaxResult.setPage(page);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            e.printStackTrace();
            ajaxResult.setMessage("查询数据失败！");
        }

        return ajaxResult;
    }

//    //异步请求
//    @ResponseBody
//    @RequestMapping("/index")
//    public Object queryUserPage(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
//                                @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
//                                Map map) {
//
//        AjaxResult ajaxResult = new AjaxResult();
//
//        try {
//            Page page = userService.queryPage(pageno, pagesize);
//            ajaxResult.setSuccess(true);
//            ajaxResult.setPage(page);
//        } catch (Exception e) {
//            ajaxResult.setSuccess(false);
//            e.printStackTrace();
//            ajaxResult.setMessage("查询数据失败！");
//        }
//
//        return ajaxResult;
//    }

    /*
    //同步请求
    //  <a href="${APP_PATH}/user/index.do"><i class="glyphicon glyphicon-user"></i> 用户维护</a>
    @RequestMapping("/index")
    public String queryUserPage(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                                @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,
                                Map map){

        Page page = userService.queryPage(pageno, pagesize);
        map.put("page",page);
        return "user/index1";
    }*/

}
