package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-18 16:52
 * @Description:
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 加载许可树：用map集合来查找父，来组合父子关系，减少循环的次数，提高性能
     * 解决：一次加载所有数据，减少与数据库的交互次数
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //根节点
            List<Permission> root = new ArrayList<Permission>();
            List<Permission> permissionList = permissionService.queryAllPermission();

            Map<Integer,Permission> map = new HashMap<Integer, Permission>();
            for (Permission permission: permissionList){
                map.put(permission.getId(),permission);
            }

            for (Permission permission: permissionList){
                //Permission child = permission; //假设为子菜单
                permission.setOpen(true);
                if (permission.getPid() == null){
                    root.add(permission);
                }else {
                    //父节点
                    Permission parent = map.get(permission.getPid());
                    parent.getChildren().add(permission);
                }
            }

            ajaxResult.setData(root);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("加载许可树数据失败");
            e.printStackTrace();
        }

        return ajaxResult;
    }


    /**
     * 加载许可树： 双层for循环加载
     * 解决：一次加载所有数据，减少与数据库的交互次数
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //根节点
            List<Permission> root = new ArrayList<Permission>();
            List<Permission> childrenPermissions = permissionService.queryAllPermission();
            for (Permission permission: childrenPermissions){
                Permission child = permission;
                if (child.getPid() == null){
                    root.add(permission);
                }else {
                    for (Permission innerPermission : childrenPermissions){
                        if (child.getPid() == innerPermission.getId()){
                            Permission parent =  innerPermission;
                            parent.getChildren().add(child);
                            break; //跳出内层循环，如果跳出外层循环，需要使用标签跳出
                        }
                    }
                }
            }

            ajaxResult.setData(root);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("加载许可树数据失败");
            e.printStackTrace();
        }

        return ajaxResult;
    }*/


    /**
     * 加载许可树： 递归加载
     * 解决许可树多个层次的问题
     * 缺点：与数据库多次交互
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult ajaxResult = new AjaxResult();

        try {

            //根节点
            List<Permission> root = new ArrayList<Permission>();

            //父节点
            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);
            root.add(permission);

            queryChildPermissions(permission);

            ajaxResult.setData(root);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("加载许可树数据失败");
            e.printStackTrace();
        }

        return ajaxResult;
    }*/
    
    private void queryChildPermissions(Permission parent){
        List<Permission> childrenPermissions = permissionService.getChildrenPermissionByPid(parent.getId());
        parent.setChildren(childrenPermissions);
        for (Permission permission: childrenPermissions){
            permission.setOpen(true);
            queryChildPermissions(permission);
        }
    }


    /**
     * 加载许可树： 循环加载
     * 缺点：树的深度是写死的
     * @return
     *//*
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult ajaxResult = new AjaxResult();

        try {

            //根节点
            List<Permission> root = new ArrayList<Permission>();

            //父节点
            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);
            root.add(permission);

            //子节点
            List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
            //设置父子关系
            permission.setChildren(children);

            for (Permission child: children){
               child.setOpen(true);
               List<Permission> innerChildren = permissionService.getChildrenPermissionByPid(child.getId());
               //设置父子关系
               child.setChildren(innerChildren);
           }
            ajaxResult.setData(root);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("加载许可树数据失败");
            e.printStackTrace();
        }

        return ajaxResult;
    }*/


    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult ajaxResult = new AjaxResult();

        try {

            //List<Permission> permissionList = permissionService.loadData();

            //根节点
            List<Permission> root = new ArrayList<Permission>();

            //父
            Permission permission = new Permission();
            permission.setName("系统权限菜单");
            permission.setOpen(true);

            root.add(permission);

            //子
            List<Permission> children = new ArrayList<Permission>();
            Permission permission1 =new Permission();
            permission1.setName("控制面板");
            Permission permission2 =new Permission();
            permission2.setName("权限管理");
            children.add(permission1);
            children.add(permission2);

            //设置父子关系
            permission.setChildren(children);

            ajaxResult.setData(root);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("加载许可树数据失败");
            e.printStackTrace();
        }

        *//*{"success":true,"message":null,"page":null,
        "data":[
        {"id":null,"pid":null,"name":"系统权限菜单","icon":null,"url":null,"open":true,
            "children":[
                {"id":null,"pid":null,"name":"控制面板","icon":null,"url":null,"open":false,"children":null},
                {"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,"children":null}
                ]}
            ]}
        *//*
        return ajaxResult;
    }*/

    @RequestMapping("/index")
    public String index(){
        return "permission/index" ;
    }
}
