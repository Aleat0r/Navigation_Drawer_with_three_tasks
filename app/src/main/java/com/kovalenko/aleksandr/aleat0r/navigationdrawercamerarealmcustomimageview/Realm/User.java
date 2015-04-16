package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.Realm;

import io.realm.RealmObject;

// класс описывающий "ячейку" базы данных Realm
public class User extends RealmObject {

    private String name;
    private int age;
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
