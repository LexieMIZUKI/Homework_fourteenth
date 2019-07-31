package com.lexieluv.homeworkfourteenth2.bean;


import java.util.ArrayList;
import java.util.List;

public class Children {
    private int id;
    private String name;
    private String pType;

    public static final String TABLE_NAME = "dish_tb";
    public static final String DISH_ID = "dish_id";
    public static final String DISH_NAME = "dish_name";
    public static final String DISH_TYPE = "dish_type";

    List<Parent> list = new ArrayList<>();

    public Children() {
    }

    public Children(int id, String name) {
        this.id = id;
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

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

//    //添加父类
//    public void addParent(Parent p){
//
//        p.getName() =
//    }
    @Override
    public String toString() {
        return "Children{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pType=" + pType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Children children = (Children) o;

        if (id != children.id) return false;
        if (!name.equals(children.name)) return false;
        return list.equals(children.list);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + list.hashCode();
        return result;
    }
}
