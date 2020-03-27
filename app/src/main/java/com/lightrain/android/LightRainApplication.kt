package com.lightrain.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.jetbrains.anko.verbose

class LightRainApplication : Application(), ResponseHandler,AnkoLogger {
    companion object{
        var userInfoBean:UserInfoBean?=null //保存用户个人信息
        var app:Context?=null
    }

    //初始化
    override fun onCreate() {
        super.onCreate()
        toast("application create")
        app=this
        val info=getSharedPreferences("login", Context.MODE_PRIVATE)
        val username=info.getString("username","")
        val password=info.getString("password","")
        if (username!=""&&password!=""){
            //自动登录
            HttpManager.manager.sendRequestByGet(
                URLProviderUtils.
                getUserInfoLoginUrl(username.toString(),password.toString()),
                ResponseStatus.LOGIN,this)
        }
    }

    //application结束时调用
    override fun onTerminate() {
        super.onTerminate()
        //如果登录就需要注销登录
        //if (userInfoBean==null)

    }

    override fun onError(msg: String?) {
        println("LightRainApplication error :$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.LOGIN->{
                userInfoBean= result?.let { JsonUtil.getResultResponse<UserInfoBean>(it,UserInfoBean::class.java).data }
            }
            ResponseStatus.LOGOUT->{ println("LightRainApplication logout success")
            }
            else -> {}
        }
    }


}