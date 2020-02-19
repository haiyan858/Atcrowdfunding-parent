package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.commons.lang3.StringUtils;
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
 * @Create_Time 2020-02-07 13:40
 * @Description:
 */

@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private RoleService roleServiceimpl;

    @Autowired
    private PermissionService permissionService;



    @RequestMapping("/assignPermission")
    public String assignPermission() {
        return "role/assignPermission";
    }

    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleid){
        List<Permission> root = new ArrayList<Permission>();

        List<Permission> childredPermissons =  permissionService.queryAllPermission();

        //根据角色id查询该角色之前所分配过的许可.
        List<Integer> permissonIdsForRoleid = permissionService.queryPermissionidsByRoleid(roleid);

        Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100

        for (Permission innerpermission : childredPermissons) {
            map.put(innerpermission.getId(), innerpermission);

            if(permissonIdsForRoleid.contains(innerpermission.getId())){
                innerpermission.setChecked(true);
            }
        }

        for (Permission permission : childredPermissons) { //100
            permission.setOpen(true);
            //通过子查找父
            //子菜单
            //Permission child = permission ; //假设为子菜单
            if(permission.getPid() == null ){
                root.add(permission);
            }else{
                //父节点
                Permission parent = map.get(permission.getPid());
                parent.getChildren().add(permission);
            }
        }

        return root ;
    }

    @ResponseBody
    @RequestMapping("/doAssignPermission")
    public AjaxResult doAssignPermission(Integer roleid,Data datas){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = roleServiceimpl.saveRolePermissionRelationship(roleid, datas);
            ajaxResult.setSuccess(count == datas.getIds().size());
            ajaxResult.setMessage("分配权限成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("分配权限失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


    @ResponseBody
    @RequestMapping("/batchDelete")
    public AjaxResult batchDelete(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = roleServiceimpl.batchDelete(data);
            ajaxResult.setSuccess(count == data.getDatas().size());
            ajaxResult.setMessage("批量删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("批量删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public AjaxResult doUpdate(Role role){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = roleServiceimpl.updateRole(role);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("更新成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("更新失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    @RequestMapping("/edit")
    public String edit(Integer pageno, Integer id, Map map) {
        Role role = roleServiceimpl.selectByPrimaryKey(id);
        map.put("role",role);
        return "/role/edit";
    }


    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(Integer uid){
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = roleServiceimpl.deleteByPrimaryKey(uid);
            if (count == 1){
                ajaxResult.setSuccess(true);
            }
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            e.printStackTrace();
            ajaxResult.setMessage("删除角色失败！");
        }
        return ajaxResult;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/role/add";
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public AjaxResult doAdd(Role role){
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = roleServiceimpl.save(role);
            if (count == 1){
                ajaxResult.setSuccess(true);
            }
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            e.printStackTrace();
            ajaxResult.setMessage("新增角色失败！");
        }

        return ajaxResult;
    }


    //异步请求
    @ResponseBody
    @RequestMapping("/pageQuery")
    public AjaxResult pageQuery(Integer pageno, Integer pagesize, String queryText) {

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
            Page page = roleServiceimpl.queryPage(paramMap);

            ajaxResult.setSuccess(true);
            ajaxResult.setPage(page);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            e.printStackTrace();
            ajaxResult.setMessage("查询数据失败！");
        }

        return ajaxResult;
    }

    @RequestMapping("/index")
    public String index() {
        return "/role/index";
    }


    @RequestMapping("/role")
    public String role() {
        return "role";
    }

}
