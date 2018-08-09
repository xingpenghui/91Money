package com.qfedu.domain.invest;

import java.util.Date;

public class Income {
    private Integer id;

    private Integer invertid;

    private Integer expectmoney;

    private Integer overduemoney;

    private Date createdate;

    private Date startdate;

    private Integer flag;

    private Date enddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvertid() {
        return invertid;
    }

    public void setInvertid(Integer invertid) {
        this.invertid = invertid;
    }

    public Integer getExpectmoney() {
        return expectmoney;
    }

    public void setExpectmoney(Integer expectmoney) {
        this.expectmoney = expectmoney;
    }

    public Integer getOverduemoney() {
        return overduemoney;
    }

    public void setOverduemoney(Integer overduemoney) {
        this.overduemoney = overduemoney;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}