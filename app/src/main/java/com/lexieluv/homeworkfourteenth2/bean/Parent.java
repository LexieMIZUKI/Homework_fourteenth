package com.lexieluv.homeworkfourteenth2.bean;

import java.util.ArrayList;
import java.util.List;

public class Parent {
    private int id;
    private String name;

    public static final String TABLE_NAME = "type_tb";
    public static final String TYPE_ID = "type_id";
    public static final String TYPE_NAME = "type_name";

    private List<Children> cList = new ArrayList<>();

    public Parent() {
    }

    public Parent(String name) {
//        this.id = id;
        this.name = name;
    }

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

    public void setcList(List<Children> cList) {
        this.cList = cList;
    }

    public List<Children> getcList() {
        return cList;
    }

    //增加子项的两种方法
    public void addChild(Children c){
        c.setpType(getName());
        cList.add(c);
    }
    public void addChild(int id,String name){
        Children c = new Children(id,name);
        c.setpType(getName());
        cList.add(c);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cList=" + cList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parent parent = (Parent) o;

        if (name != parent.name) return false;//只判断名字即可
        return false;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
