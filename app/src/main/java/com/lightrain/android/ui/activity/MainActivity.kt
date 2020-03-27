package com.lightrain.android.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.lightrain.android.R
import com.lightrain.android.model.ResponseException
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.RequestBody


class MainActivity : AppCompatActivity(), ResponseHandler {
    val username="15244482896"
    val password="123456"
    val userInfoBean=getUserInfo()
    val formBody=FormBody.Builder()
        .add("username",userInfoBean.username)
        .add("password",userInfoBean.password)
        .add("nickName",userInfoBean.nickname)
        .add("userIcon",userInfoBean.userIcon)
        .add("userGender",userInfoBean.userGender.toString())
        .add("userBirthday",userInfoBean.userBirthday)
        .add("userSchool",userInfoBean.userSchool)
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login.setOnClickListener{
            HttpManager.manager.
            sendRequestByGet(URLProviderUtils.getUserInfoLoginUrl(username,password),
                ResponseStatus.LOGIN,this)

        }
        logout.setOnClickListener{
            HttpManager.manager.
            sendRequestByGet(URLProviderUtils.getUserInfoLogoutUrl(username),
                ResponseStatus.LOGIN,this)

        }
        update.setOnClickListener{
            HttpManager.manager.
            sendRequestByPost(URLProviderUtils.getUserInfoUpdate(),formBody,
                ResponseStatus.LOGIN,this)

        }
        register.setOnClickListener{
            HttpManager.manager.
            sendRequestByPost(URLProviderUtils.getUserInfoRegister(),formBody,
                ResponseStatus.LOGIN,this)

        }
    }

    override fun onError(msg: String?) {
        println("MainActivity onError: $msg")
        val exception= msg?.let { JsonUtil.getResponseException(it) }
        exception?.let {
            println("onError javaClass: ${exception.javaClass}")
            println("onError status: ${exception.status}")
            println("onError timestamp: ${exception.timestamp}")
            println("onError error: ${exception.error}")
            println("onError message: ${exception.message}")
            println("onError path: ${exception.path}")

        }
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        val data= result?.let { JsonUtil.getResultResponse(it,UserInfoBean::class.java)}
        data?.let {
            println("onSuccess javaClass: ${data.javaClass}")
            println("onSuccess code: ${data.code}")
            println("onSuccess msg: ${data.msg}")
            println("onSuccess data: ${data.data}")
        }

    }

    private fun getUserInfo():UserInfoBean{
        return UserInfoBean("13562663180","13562663180","dad",
            "http://zt-data.test.upcdn.net/icon.jpg",1,"1976-1-1",
            44,"StayHome",0)
    }


}
