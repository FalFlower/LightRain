package com.lightrain.android.net

import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.StringUtil
import com.lightrain.android.util.URLProviderUtils
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

//用于统一管理网络请求
class HttpManager private constructor(){
    private val RESPONSE_SUCCESS=100
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
    //返回信息解析（区分成功数据与错误数据）
    private fun analysisResponse(response: Response,responseStatus:ResponseStatus, handler: ResponseHandler) {
        val msg=response.body?.string()
        //判断是否为异常
        msg?.let {
            val res=JsonUtil.getResultResponse(msg,Any::class.java)
            when (res.code) {
                RESPONSE_SUCCESS -> {
                    handler.onSuccess(responseStatus,it)//返回正确的Json数据
                }
                //                StringUtil.isMatch("timestamp",it) -> {
                //                    handler.onError(it)//返回异常Json数据
                //                }
                else -> {
                    println("analysisResponse msg:$msg")
                    handler.onError(res.msg)//返回异常信息提示
                }
            }
        }
    }


    fun sendYouPaiVerificationCode(code:String,mobile:String, handler: ResponseHandler){
        val token="TBd2ycP6olQqgiyvvPEgeY5lQuQdsS"
        val template_id="2518" //短信模板
        val formBody =FormBody.Builder()
        formBody.add("template_id",template_id)
        formBody.add("mobile",mobile)
        formBody.add("vars",code)
        val request= Request.Builder()
            .header("Authorization",token)
            .url(URLProviderUtils.youPaiMessage)
            .post(formBody.build()).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                println("!!!!!response.body.toString():${response.body.toString()}")
                handler.onSuccess(ResponseStatus.SEND_MESSAGE,response.body.toString());
            }
            override fun onFailure(call: Call, e: IOException) {
                println("!!!!!e.message:${e.message}")
                handler.onError(e.message)
            }

        })

    }

}