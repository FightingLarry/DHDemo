package com.dh.demo.install;

/**
 * Created by DemonHunter on 2016/6/23.
 */

public interface ICheckMineDefault {

    void onPreload();

    void onSuccess(String path);

    void onError();

    void onComplete();
}
