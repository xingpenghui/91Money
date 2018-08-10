package com.qfedu.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;
import java.util.Date;

/**
 *@Author feri
 *@Date Created in 2018/8/10 09:52
 */
public class LoanVo {
    private int id;
    private int type;
    private String typename;
    private int rate;
    private int monthes;
    private int money;
    private int completemoney;//已经完成的金额
    private int investperson;//投资人数
    private int minmoney;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date authdate;//审核通过日期--起始日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enddate;//审核通过日期--结束日期
    private int days;//投标 的日期 天数 2 3 4
    private int totalmoney;
    private int flag;
    private String flagname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        switch (type){
            case 1:
                typename="信用标";break;
                case 2:
                typename="抵押标";break;
        }
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getMonthes() {
        return monthes;
    }

    public void setMonthes(int monthes) {
        this.monthes = monthes;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCompletemoney() {
        return completemoney;
    }

    public void setCompletemoney(int completemoney) {
        this.completemoney = completemoney;
    }

    public int getInvestperson() {
        return investperson;
    }

    public void setInvestperson(int investperson) {
        this.investperson = investperson;
    }

    public int getMinmoney() {
        return minmoney;
    }

    public void setMinmoney(int minmoney) {
        this.minmoney = minmoney;
    }

    public Date getAuthdate() {
        return authdate;
    }

    public void setAuthdate(Date authdate) {
        this.authdate = authdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(authdate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        enddate=calendar.getTime();
    }

    public int getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(int totalmoney) {
        this.totalmoney = totalmoney;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
        switch (flag){
            case 0:flagname="未审核";break;
            case 1:flagname="投标中";break;
            case 2:flagname="收益中";break;
        }
    }

    public String getFlagname() {
        return flagname;
    }

    public void setFlagname(String flagname) {
        this.flagname = flagname;
    }
}
