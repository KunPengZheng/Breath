package com.zkp.breath.component.activity.mvx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityMvvmBinding
import com.zkp.breath.designpattern.mvvm.ViewModel

/**
 * DataBinding是 MVVM 模式在 Android 上的一种实现。
 */
class MvvmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        // 绑定view_model
        binding.vm = ViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}