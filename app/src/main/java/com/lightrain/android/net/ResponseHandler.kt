package com.lightrain.android.net

interface ResponseHandler {
    fun onError(msg:String?)
    fun onSuccess(status:ResponseStatus,result: String?)
}