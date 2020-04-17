package com.lightrain.android.view

import com.lightrain.android.model.VideoBean

interface HomeView {
    fun onError(msg:String?)
    fun onSuccess(status:Int,dataList:List<VideoBean>)
}