package com.lightrain.android.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.ui.activity.*

object StartActivityUtil {

    /*
    * 跳转到VideoPlayActivity并且传递classBean
    * */
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

    /*
    * 跳转到VideoInfoActivity并且传递classBean
    * */
    fun toVideoInfoActivity(context: Context, videoBean: VideoBean){
        val intent= Intent(context, VideoInfoActivity::class.java)
        val bundle= Bundle()
        bundle.putParcelable("videoBean",videoBean)
        intent.putExtra("videoInfo",bundle)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getDataInVideoInfoActivity(intent: Intent):VideoBean?{
        return intent.getParcelableExtra<Bundle>("videoInfo")?.getParcelable<VideoBean>("videoBean")
    }


    /*
    * 跳转到SearchInfoActivity并且传递label来获取数据
    * */
    fun toSearchInfoActivity(context: Context, title:String,label:String){
        val intent= Intent(context, SearchInfoActivity::class.java)
        intent.putExtra("title",title)
        intent.putExtra("label",label)
        context.startActivity(intent)
    }
    //通过intent来获取传递的data
    fun getLabelInSearchInfoActivity(intent: Intent):String?{
        return intent.getStringExtra("label")
    }

    fun getTitleInSearchInfoActivity(intent: Intent):String?{
        return intent.getStringExtra("title")
    }

    /*
    * 跳转到PersonalSpace并且传递userInfoBean
    * */
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

    /*
    * 跳转到EvaluateActivity
    * */
    fun toEvaluateActivity(context: Context, videoBean: VideoBean){
        val intent= Intent(context, EvaluateActivity::class.java)
        val bundle= Bundle()
        bundle.putParcelable("evaluateData",videoBean)
        intent.putExtra("evaluate",bundle)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getDataInEvaluateActivity(intent: Intent):VideoBean?{
        return intent.getParcelableExtra<Bundle>("evaluate")?.getParcelable<VideoBean>("evaluateData")
    }
}