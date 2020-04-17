package com.lightrain.android.view

import com.lightrain.android.model.UVBean
import com.lightrain.android.model.VideoBean

interface LearningView {
    fun onError(msg:String?)
    fun onSuccess(uvList:List<UVBean>,videoList:List<VideoBean>)
}