package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityRxjava2Binding
import com.zkp.breath.rxjava2.RxJava2Demo

/**
 * Rxjava2的例子
 */
class RxJava2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityRxjava2Binding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)

        RxJava2Demo.backbressErrorInvok2(true /*false*/)
        inflate.btn.setOnClickListener { RxJava2Demo.request() }
    }

}