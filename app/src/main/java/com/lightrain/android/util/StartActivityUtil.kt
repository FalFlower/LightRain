package com.lightrain.android.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.ui.activity.PersonalSpaceActivity
import com.lightrain.android.ui.activity.VideoPlayActivity

object StartActivityUtil {
    //跳转到VideoPlayActivity并且传递classBean
    fun toVideoPlayActivity(context: Context, videoBean: VideoBean){
        val intent= Intent(context, VideoPlayActivity::class.java)
        val bundle= Bundle()
        bundle.putParcelable("videoBean",videoBean)
        intent.putExtra("videoPlay",bundle)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getDataInVideoPlayActivity(intent: Intent):VideoBean?{
        return intent.getParcelableExtra<Bundle>("videoPlay")?.getParcelable<VideoBean>("videoBean")
    }

    //跳转到VideoPlayActivity并且传递classBean
    fun toPersonalSpaceActivity(context: Context, userInfoBean: UserInfoBean){
        val intent= Intent(context, PersonalSpaceActivity::class.java)
        val bundle= Bundle()
        bundle.putParcelable("userInfoBean",userInfoBean)
        intent.putExtra("userInfo",bundle)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getDataInPersonalSpaceActivity(intent: Intent):UserInfoBean?{
        return intent.getParcelableExtra<Bundle>("userInfo")?.getParcelable<UserInfoBean>("userInfoBean")
    }


}