package com.js.sample.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Student implements Serializable{

    /**
     * response : limitbuy
     * productList : [{"id":"1102539","name":"雅培金装","pic":"","price":"79","limitPrice":"78","leftTime":"3600"},{"id":"1102539","name":"雅培金装","pic":"","price":"79","limitPrice":"78","leftTime":"3600"}]
     * listCount : 15
     */

    private String response;
    private String listCount;
    /**
     * id : 1102539
     * name : 雅培金装
     * pic :
     * price : 79
     * limitPrice : 78
     * leftTime : 3600
     */

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String listCount) {
        this.listCount = listCount;
    }
}
