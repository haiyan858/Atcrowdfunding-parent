package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @RequestMapping("/assignPermission")
    public String assignPermission() {
        return "assignPermission";
    }


}
