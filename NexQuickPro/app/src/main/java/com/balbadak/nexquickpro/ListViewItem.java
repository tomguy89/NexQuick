package com.balbadak.nexquickpro;

public class ListViewItem {
    private String titleStr;
    private String descStr;
    private String urgentStr;
    private int callNum;
    private int orderNum;
    private int quickType;

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }

    public int getCallNum() {
        return callNum;
    }

    public void setCallNum(int callNum) {
        this.callNum = callNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getQuickType() {
        return quickType;
    }

    public void setQuickType(int quickType) {
        this.quickType = quickType;
    }

    public String getUrgentStr() {
        return urgentStr;
    }

    public void setUrgentStr(String urgentStr) {
        this.urgentStr = urgentStr;
    }



}


