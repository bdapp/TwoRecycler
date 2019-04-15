package com.example.myapplication;

/**
 * @Info
 * @Auth Bello
 * @Time 2019/4/15 23:32
 * @Ver
 */
public class CateData {
    private String title;
    private int id;
    private boolean isSelect;

    public CateData() {
    }


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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
