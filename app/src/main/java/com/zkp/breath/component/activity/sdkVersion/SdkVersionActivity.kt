package com.zkp.breath.component.activity.sdkVersion

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import kotlinx.android.synthetic.main.activity_entrance.*

class SdkVersionActivity : BaseActivity(R.layout.activity_entrance) {

    private val listOf = mutableListOf(
            "ScopedStorage", "Toast", "HttpClearText"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.setBackgroundColor(ColorUtils.getColor(R.color.colorFF009688))
        initRcv()
    }

    private fun initRcv() {
        rcv.addItemDecoration(EntranceItemDecoration())
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val gridLayoutManager = GridLayoutManager(rcv.context, 2)
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = EntranceAdapter(listOf)
        gridAdapter.setOnItemClickListener(onItemChildClickListener)
        rcv.adapter = gridAdapter
    }

    private val onItemChildClickListener = OnItemClickListener { _, _, position ->
        when (val type = listOf[position]) {
            "ScopedStorage" -> {
                ActivityUtils.startActivity(ScopedStorageActivity::class.java)
                return@OnItemClickListener
            }
            "Toast" -> {
                ActivityUtils.startActivity(ToastR11Activity::class.java)
                return@OnItemClickListener
            }
            "HttpClearText" -> {
                ActivityUtils.startActivity(HttpClearTextActivity::class.java)
                return@OnItemClickListener
            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

}