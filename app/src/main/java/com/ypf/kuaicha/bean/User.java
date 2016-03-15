package com.ypf.kuaicha.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
@Table(name = "user")
public class User implements Serializable {
    @Column(name = "name", isId = true)
    public int name;
    @Column(name = "password")
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
