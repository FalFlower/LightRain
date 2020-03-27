package com.lightrain.android.net

import android.widget.Toast
import com.google.gson.Gson
import com.lightrain.android.LightRainApplication
import com.lightrain.android.util.BmMatch
import com.lightrain.android.util.ThreadUtil
import okhttp3.*
import org.jetbrains.anko.toast
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class HttpManager private constructor(){
    private val client= OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
    companion object{
        val manager=HttpManager()
    }

    //发送网络请求-Get
    fun sendRequestByGet(url:String,responseStatus: ResponseStatus,handler: ResponseHandler){
        val request= Request.Builder().url(url).get().build()
        client.newCall(request).enqueue(object : Callback {
            //在子线程中调用
            override fun onFailure(call: Call, e: IOException) {handler.onError(e.message)}

            override fun onResponse(call: Call, response: Response) {
                analysisResponse(response,responseStatus,handler)
            }
        })
    }
    //发送网络请求-Post
    fun sendRequestByPost(url:String,formBody: FormBody,responseStatus: ResponseStatus,handler: ResponseHandler){
        val request= Request.Builder().url(url).post(formBody).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                analysisResponse(response,responseStatus,handler)
            }
            override fun onFailure(call: Call, e: IOException) {handler.onError(e.message)}

        })
    }

    private fun analysisResponse(response: Response,responseStatus:ResponseStatus, handler: ResponseHandler) {
        val msg=response.body?.string()
        println("onResponse: response.body?.string: $msg")
        val res= msg?.let { BmMatch("timestamp").match(it) }//判断是否为异常
        if (res?.isEmpty()!!){
            handler.onSuccess(responseStatus,msg)//返回正确的Json数据
        }else{
            handler.onError(msg)//返回异常Json数据
        }
    }

}