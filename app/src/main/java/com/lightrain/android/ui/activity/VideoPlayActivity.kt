package com.lightrain.android.ui.activity

import android.os.Bundle
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.ClassBean
import com.lightrain.android.util.ClassBeanUtil

class VideoPlayActivity :BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initData() {
        //获取传递来的数据
        val data=ClassBeanUtil.getDataInVideoPlayActivity(intent)
        println(data?.classVideoUrl)
    }
}