package com.example.flower.http.bmob;

import com.example.flower.http.bean.KnowFlowerResultBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户识花结果表
 *
 * @author ShenBen
 * @date 2020/1/29 20:28
 * @email 714081644@qq.com
 */
public class IdentifyResultBean extends BmobObject {
    private UserBean user;
    private BmobFile picture;
    private List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> results;

    public IdentifyResultBean() {
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> getResults() {
        return results;
    }

    public void setResults(List<KnowFlowerResultBean.ResponseBean.IdentifyResultsBean> results) {
        this.results = results;
    }
}
