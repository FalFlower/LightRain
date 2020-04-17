package com.lightrain.android.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.ui.activity.ClassifyInfoActivity
import com.lightrain.android.ui.activity.PersonalSpaceActivity
import com.lightrain.android.ui.activity.VideoInfoActivity
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

    //跳转到VideoInfoActivity并且传递classBean
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


    //跳转到ClassifyInfoActivity并且传递label来获取数据
    fun toClassifyInfoActivity(context: Context, position:Int,title:String,labelList:Array<String>){
        val intent= Intent(context, ClassifyInfoActivity::class.java)
        intent.putExtra("position",position)
        intent.putExtra("title",title)
        intent.putExtra("labelList",labelList)
        context.startActivity(intent)
    }
    //通过intent来获取传递的classBean
    fun getPositionInClassifyInfoActivity(intent: Intent):Int?{
        return intent.getIntExtra("position",0)
    }
    fun getLabelListInClassifyInfoActivity(intent: Intent):Array<String>?{
        return intent.getStringArrayExtra("labelList")
    }
    fun getTitleInClassifyInfoActivity(intent: Intent):String?{
        return intent.getStringExtra("title")
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