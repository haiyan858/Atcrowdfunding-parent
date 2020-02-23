package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.AdvertMappper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-23 16:31
 * @Description:
 */

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMappper advertMappper;

    @Override
    public Page queryPage(Map paramMap) {

        Page page = new Page((Integer)paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);

        List<Advertisement> datas = advertMappper.queryList(paramMap);

        page.setDatas(datas);

        Integer totalsize = advertMappper.queryCount(paramMap);

        page.setTotalsize(totalsize);

        return page;
    }

    @Override
    public int insertAdvert(Advertisement advert) {
        return advertMappper.insert(advert);
    }

    @Override
    public int deleteById(Integer id) {
        return advertMappper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchByVO(Data data) {
        //System.out.println(data.getIds());
        return advertMappper.deleteBatchByVO(data);
    }

    @Override
    public Advertisement getById(Integer id) {
        return advertMappper.selectByPrimaryKey(id);
    }

    @Override
    public int updateAdvert(Advertisement advert) {
        return advertMappper.update(advert);
    }
}
