package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.VideoEvaluateBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.ThreadUtil
import com.lightrain.android.util.URLProviderUtils
import kotlinx.android.synthetic.main.view_video_evaluate_list.view.*

class VideoEvaluateListItem :RelativeLayout, ResponseHandler {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_video_evaluate_list,this)
    }
    fun setData(videoEvaluateBean:VideoEvaluateBean){
        ThreadUtil.runOnMainThread(Runnable {
            if (videoEvaluateBean.videoScore<5){
                //差评
                ImageUtil.loadImage(context,videoEvaluateStatus,R.mipmap.ic_bad)
                videoEvaluateStatusTitle.text="差评"
            }
            videoEvaluateBrief.text=videoEvaluateBean.videoEvaluateDetail
        })
        getUserInfo(videoEvaluateBean.username)
    }

    private fun getUserInfo(username: String) {
        HttpManager.manager.sendRequestByGet(URLProviderUtils.getUserInfo(username),ResponseStatus.USER_INFO,this)
    }

    override fun onError(msg: String?) {
        println("VideoEvaluateListItem error :$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        ThreadUtil.runOnMainThread(Runnable {
            val userInfo= result?.let { JsonUtil.getResultResponse(it,UserInfoBean::class.java).data }
            userInfo?.let {
                ImageUtil.loadImageRoundedCorners(context,videoEvaluateIcon,it.userIcon)
                videoEvaluateNickname.text=it.nickname


            }
        })

    }
}