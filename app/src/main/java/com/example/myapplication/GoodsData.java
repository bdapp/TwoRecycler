package com.example.myapplication;

/**
 * @Info
 * @Auth Bello
 * @Time 2019/4/15 23:32
 * @Ver
 */
public class GoodsData {
    private String title;
    private int id;
    private int cate;

    public GoodsData() {
    }

    public GoodsData(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }
}
