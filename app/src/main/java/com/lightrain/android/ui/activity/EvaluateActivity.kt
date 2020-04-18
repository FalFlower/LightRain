package com.lightrain.android.ui.activity

import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.VideoEvaluateBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.StartActivityUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.util.UserInfoUtil
import com.lightrain.android.util.VideoInfoUtil
import kotlinx.android.synthetic.main.activity_evaluate.*

class EvaluateActivity :BaseActivity(), ResponseHandler {
    var score=10
    val videoBean by lazy { StartActivityUtil.getDataInEvaluateActivity(intent) }
    override fun getLayoutId(): Int {
        return R.layout.activity_evaluate
    }

    override fun initListener() {
        evaluateBack.setOnClickListener { finish() }
        evaluateSend.setOnClickListener {
            //发表评论
            sendEvaluate()
        }
        evaluateStar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            score= (rating*2).toInt()
        }


    }

    private fun sendEvaluate() {
        if (videoBean!=null&&LightRainApplication.userInfoBean!=null){
            HttpManager.manager.sendRequestByPost(URLProviderUtils.updateVideoEvaluate(),
                VideoInfoUtil.getUpdateVideoEvaluateBeanInfoBody(
                    VideoEvaluateBean(videoBean!!.videoId,
                        LightRainApplication.userInfoBean!!.username,score,evaluateEdit.text.toString())),ResponseStatus.VIDEO_EVALUATE,this)
        }

    }

    override fun onError(msg: String?) {
        println("EvaluateActivity error:msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        //更新成功
        runOnUiThread {
            myToast("发表成功")
            finish()
        }
    }
}