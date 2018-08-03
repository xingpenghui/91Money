package com.qfedu.activemq;

import java.io.Serializable;

/**
 *@Author feri
 *@Date Created in 2018/8/3 09:43
 *
 *
 */
public class Offer implements Serializable {
    private int id;
    private String name;
    private String company;
    private int money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Offer(int id, String name, String company, int money) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.money = money;
    }

    public Offer() {
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", money=" + money +
                '}';
    }
}
