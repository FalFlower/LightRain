package com.lightrain.android.ui.activity

import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.util.StartActivityUtil

class VideoPlayActivity :BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initData() {
        //获取传递来的数据
        val data=StartActivityUtil.getDataInVideoPlayActivity(intent)
        println(data?.videoUrl)
    }
}