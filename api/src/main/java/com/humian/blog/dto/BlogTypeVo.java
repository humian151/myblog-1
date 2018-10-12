package com.humian.blog.dto;

import java.math.BigInteger;

/**
 * Created by DELL on 2018/8/29.
 */
public class BlogTypeVo {
    private Integer typeId;
    private String typename;
    private Integer count;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
