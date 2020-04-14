package com.lightrain.android

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.UserRelationshipBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import org.jetbrains.anko.AnkoLogger

class LightRainApplication : Application(), ResponseHandler,AnkoLogger {
    var info:SharedPreferences?=null
    var username=""
    var password=""
    companion object{
        var userInfoBean:UserInfoBean?=null //保存用户个人信息
        var app:Context?=null
        var userRelationship:UserRelationshipBean?=null
    }

    //初始化
    override fun onCreate() {
        super.onCreate()
        app=this
        autoLogin()
    }

    private fun getUserRelationship() {
        HttpManager.
        manager.sendRequestByGet(
            URLProviderUtils.getUserRelationshipInfo(username),
            ResponseStatus.USER_RELATIONSHIP,this)

    }


    private fun autoLogin() {
        info=getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        info?.let {
            username=it.getString("username","").toString()
            password=it.getString("password","").toString()
            login(username,password)
            getUserRelationship()
        }
    }

    private fun login(username: String, password: String) {
        if (username!=""&&password!=""){
            //自动登录
            HttpManager.manager.sendRequestByGet(
                URLProviderUtils.
                getUserInfoLoginUrl(username,password),
                ResponseStatus.LOGIN,this)
        }
    }

    override fun onError(msg: String?) {
        //尚且有个Bug，自动注销问题
        if (msg.equals("已登录，请勿重复登陆")){
            HttpManager.
                manager.sendRequestByGet(URLProviderUtils.
                    getUserInfoLogoutUrl(username),ResponseStatus.LOGOUT,this)
        }
        println("LightRainApplication error :${msg} ")

    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.LOGIN->{
                userInfoBean= result?.let { JsonUtil.getResultResponse<UserInfoBean>(it,UserInfoBean::class.java).data }
            }
            ResponseStatus.LOGOUT->{
                println("LightRainApplication logout success")
                login(username,password)
            }
            ResponseStatus.USER_RELATIONSHIP->{
                userRelationship= result?.let { JsonUtil.getResultResponse(it,UserRelationshipBean::class.java).data }
            }
            else -> {}
        }
    }


}