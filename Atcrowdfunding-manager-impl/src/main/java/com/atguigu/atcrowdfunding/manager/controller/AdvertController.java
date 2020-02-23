package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-23 16:31
 * @Description:
 */

@Controller
@RequestMapping("/advert")
public class AdvertController {
    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index() {
        return "advert/index";
    }

    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                            @RequestParam(value = "pagesize", required = false, defaultValue = "2") Integer pagesize,
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
            Page page = advertService.queryPage(paramMap);
            ajaxResult.setSuccess(true);
            ajaxResult.setPage(page);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            e.printStackTrace();
            ajaxResult.setMessage("查询数据失败！");
        }

        return ajaxResult;
    }

    @RequestMapping("/add")
    public String add() {
        return "advert/add";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advert , HttpSession session) {
        AjaxResult result = new AjaxResult();


        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;

            MultipartFile mfile = mreq.getFile("advpic");

            String name = mfile.getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg

            String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pics");
            System.out.println("getResourcePaths---"+servletContext.getResourcePaths("/pics"));
            System.out.println("getContextPath---"+servletContext.getContextPath());

            ///Users/cuihaiyan/py_workspace/javaPro/idea-workspace/Atcrowdfunding-parent/out/artifacts/Atcrowdfunding_main_war_exploded/
            // pics\adv\39e3c45f-e002-45ae-b9cd-84fe439d8cd6.jpg


            String path =realpath+ File.separator+"adv"+File.separator+iconpath;

            System.out.println("--------> ths save path is:"+path);
            mfile.transferTo(new File(path));

            User user = (User)session.getAttribute(Const.LOGIN_USER);
            advert.setUserid(user.getId());
            advert.setStatus("1");
            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object doDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = advertService.deleteById(id);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }

    //batchDelete
    //接收多条数据
    @ResponseBody
    @RequestMapping("/batchDelete")
    public Object doDeleteBatch(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = advertService.deleteBatchByVO(data);
            ajaxResult.setSuccess(count == data.getDatas().size());
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


    //同步请求用map
    @RequestMapping("/edit")
    public String edit(Integer id, Map map) {
        Advertisement advert = advertService.getById(id);
        map.put("advert", advert);
        return "/advert/edit";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/update")
    public Object update(Advertisement advert) {
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = advertService.updateAdvert(advert);
            ajaxResult.setSuccess(count == 1);
            ajaxResult.setMessage("更新成功");
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("更新失败");
            e.printStackTrace();
        }
        return ajaxResult;
    }


}
