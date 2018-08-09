package com.qfedu.elasticsearch;

/**
 *@Author feri
 *@Date Created in 2018/8/8 10:02
 */
public class Loan {
    private int id;
    private String title;
    private int money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Loan(int id, String title, int money) {
        this.id = id;
        this.title = title;
        this.money = money;
    }
}
