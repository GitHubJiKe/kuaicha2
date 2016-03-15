package com.ypf.kuaicha.bean;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class UserDao {
    public static void save(User user) throws Exception {
        DBManager.getInstance().save(user);
        UserSaveModel saveModel = new UserSaveModel();
        saveModel.setIsSaveOK(true);
        EventBus.getDefault().post(saveModel);
    }

    public static User findUserByName(String name) throws Exception {
        return DBManager.getInstance().selector(User.class).where("name", "=", name).findFirst();
    }
}
