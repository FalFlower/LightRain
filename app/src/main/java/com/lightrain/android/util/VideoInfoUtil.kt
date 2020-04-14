package com.lightrain.android.util

import com.lightrain.android.model.VideoBean
import com.lightrain.android.model.VideoEvaluateBean
import okhttp3.FormBody

object VideoInfoUtil {

    //获取视频更新表单
    fun getUpdateUerInfoBody(videoBean: VideoBean): FormBody {
        val formBody = FormBody.Builder()
        formBody.add("videoId",videoBean.videoId)
        formBody.add("username",videoBean.username)
        formBody.add("videoTitle",videoBean.videoTitle)
        formBody.add("videoBrief",videoBean.videoBrief)
        formBody.add("videoScoreAva", videoBean.videoScoreAva.toString())
        formBody.add("videoTime",videoBean.videoTime.toString())
        formBody.add("videoPrice",videoBean.videoPrice.toString())
        formBody.add("videoLearningNum",videoBean.videoLearningNum.toString())
        formBody.add("videoIcon",videoBean.videoIcon)
        formBody.add("videoUrl",videoBean.videoUrl)
        formBody.add("videoLabel",videoBean.videoLabel)
        return formBody.build()
    }

    //获取视频更新表单
    fun getUpdateUerInfoBody(videoEvaluateBean: VideoEvaluateBean ): FormBody {
        val formBody = FormBody.Builder()
        formBody.add("videoId",videoEvaluateBean.videoId)
        formBody.add("username",videoEvaluateBean.username)
        formBody.add("videoScore",videoEvaluateBean.videoScore.toString())
        formBody.add("VideoEvaluateDetail",videoEvaluateBean.VideoEvaluateDetail)
        return formBody.build()
    }

}