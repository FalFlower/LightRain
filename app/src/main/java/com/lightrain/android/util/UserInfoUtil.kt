package com.lightrain.android.util

import android.content.Context
import android.content.SharedPreferences
import com.lightrain.android.LightRainApplication
import com.lightrain.android.model.UserInfoBean
import okhttp3.FormBody

import okhttp3.RequestBody

object UserInfoUtil {
    val USER_GENDER_WOMAN=0
    val USER_GENDER_MAN=1

    fun isLogin():Boolean{
        return LightRainApplication.userInfoBean != null
    }
    //获取默认用户表单，用于注册
    fun getDefaultUerInfoBody(username:String,password:String): FormBody {
        val formBody =FormBody.Builder()
        formBody.add("username",username)
        formBody.add("password",password)
        formBody.add("nickName","Rainner")
        formBody.add("userIcon","http://zt-data.test.upcdn.net/ic_login_no.png")
        formBody.add("userGender", USER_GENDER_MAN.toString())
        formBody.add("userBirthday","2020-1-1")
        formBody.add("userSchool","stayHome")
        return formBody.build()
    }

    //获取用户更新个人信息表单
    fun getUpdateUerInfoBody(userInfoBean: UserInfoBean): FormBody {
        val formBody =FormBody.Builder()
        formBody.add("username",userInfoBean.username)
        formBody.add("password",userInfoBean.password)
        formBody.add("nickName",userInfoBean.nickname)
        formBody.add("userIcon",userInfoBean.userIcon)
        formBody.add("userGender", userInfoBean.userGender.toString())
        formBody.add("userBirthday",userInfoBean.userBirthday)
        formBody.add("userSchool",userInfoBean.userSchool)
        return formBody.build()
    }

    //本地存储用户信息
    fun loadSharedPreferences(context: Context,username:String,password:String){
        val user=context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val editor=user.edit()
        editor.putString("username",username)
        editor.putString("password",password)
        editor.apply() //提交修改
    }

    //移除本地存储用户信息
    fun removeSharedPreferences(context: Context){
        val user=context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val editor=user.edit()
        editor.clear()
        editor.apply() //提交修改
    }
}