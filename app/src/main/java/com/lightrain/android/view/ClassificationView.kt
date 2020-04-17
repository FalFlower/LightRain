package com.lightrain.android.view

interface ClassificationView {
    fun onError(msg:String?)
    fun onSuccess(status:Int,msg: String?)
}