package com.qfedu.domain.user;

import java.util.Date;

public class UserDetail {
    private Integer id;

    private Integer uid;

    private String realname;

    private String sex;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String idNumber;
    private String idimage1;
    private String idimage2;

    public String getIdimage1() {
        return idimage1;
    }

    public void setIdimage1(String idimage1) {
        this.idimage1 = idimage1;
    }

    public String getIdimage2() {
        return idimage2;
    }

    public void setIdimage2(String idimage2) {
        this.idimage2 = idimage2;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    private Date birthdate;

    private Date createtime;

    private Integer flag;//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", uid=" + uid +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", idimage1='" + idimage1 + '\'' +
                ", idimage2='" + idimage2 + '\'' +
                ", birthdate=" + birthdate +
                ", createtime=" + createtime +
                ", flag=" + flag +
                '}';
    }
}