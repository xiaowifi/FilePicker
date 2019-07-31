package com.ying.myfilepocker;

public class ActivityBean {
    Class a;
    String name;

    public ActivityBean(Class a, String name) {
        this.a = a;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
