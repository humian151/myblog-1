package com.humian.blog.dto;

/**
 * 返回bootstraptable所需要的数据
 * Created by DELL on 2018/9/26.
 */
public class DataTable<T> {
    private Integer total;
    private T rows;

    public DataTable() {
    }

    public DataTable(Integer total, T rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
