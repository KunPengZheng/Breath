package com.zkp.breath.designpattern.adapter.classadapter;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class ClassAdapter extends Adaptee implements ITarget {

    @Override
    public void request(String s) {
        Log.i("ITarget", "request: " + filter(s));
    }

}