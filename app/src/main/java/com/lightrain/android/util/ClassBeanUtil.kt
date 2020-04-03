package com.lightrain.android.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lightrain.android.model.ClassBean
import com.lightrain.android.ui.activity.VideoPlayActivity
import java.util.*

object ClassBeanUtil {
    //跳转到VideoPlayActivity并且传递classBean
    fun toVideoPlayActivity(context: Context,classBean: ClassBean){
        val intent= Intent(context, VideoPlayActivity::class.java)
        val bundle= Bundle()
        bundle.putParcelable("classBean",classBean)
        intent.putExtra("videoPlay",bundle)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getDataInVideoPlayActivity(intent: Intent):ClassBean?{
        return intent.getParcelableExtra<Bundle>("videoPlay")?.getParcelable<ClassBean>("classBean")
    }


}