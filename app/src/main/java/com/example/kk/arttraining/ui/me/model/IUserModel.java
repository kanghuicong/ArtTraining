package com.example.kk.arttraining.ui.me.model;

/**
 * 作者：wschenyongyin on 2016/10/15 17:53
 * 说明:
 */
public interface IUserModel {

    public void login(String username, String password, OnLoginListener loginListener);
}
