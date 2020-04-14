package com.lightrain.android.util

import android.content.Context
import com.lightrain.android.LightRainApplication
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.UserRelationshipBean
import okhttp3.FormBody

object UserInfoUtil {
    const val USER_GENDER_WOMAN=0
    const val USER_GENDER_MAN=1
    const val USER_IDENTITY_STUDENT=2
    const val USER_IDENTITY_TEACHER=3

    fun isLogin():Boolean{
        return LightRainApplication.userInfoBean != null
    }

    fun isFollow(follow:String):Boolean{
        val id=LightRainApplication.userInfoBean?.username
        return id?.let { StringUtil.isMatch(it,follow) }!!
    }


    //获取默认用户表单，用于注册
    fun getDefaultUerInfoBody(username:String,password:String): FormBody {
        val formBody =FormBody.Builder()
        formBody.add("username",username)
        formBody.add("password",password)
        formBody.add("nickName","昵称")
        formBody.add("userIcon","http://zt-data.test.upcdn.net/ic_login_no.png")
        formBody.add("userGender", USER_GENDER_MAN.toString())
        formBody.add("userBirthday","2020-1-1")
        formBody.add("userSchool","stayHome")
        formBody.add("userSign","这位童鞋很懒，木有签名的说~")
        formBody.add("userLocation","地球")
        formBody.add("userIdentity",USER_IDENTITY_STUDENT.toString())
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
        formBody.add("userSign",userInfoBean.userSign)
        formBody.add("userLocation",userInfoBean.userLocation)
        formBody.add("userIdentity",userInfoBean.userIdentity.toString())
        return formBody.build()
    }

    //获取用户更新个人关系信息表单
    fun getUpdateUerRelationshipBody(userRelationshipBean: UserRelationshipBean): FormBody {
        val formBody =FormBody.Builder()
        formBody.add("username",userRelationshipBean.username)
        formBody.add("userFans",userRelationshipBean.userFans)
        formBody.add("userFansNum",userRelationshipBean.userFansNum.toString())
        formBody.add("userFollows",userRelationshipBean.userFollows)
        formBody.add("userFollowsNum", userRelationshipBean.userFollowsNum.toString())
        formBody.add("userIntegral",userRelationshipBean.userIntegral.toString())
        formBody.add("userFavourites",userRelationshipBean.userFavourites)
        return formBody.build()
    }

    //获取用户更新个人关系信息表单
    fun getFollowOrUnFollowBody(username:String,userFollow:String): FormBody {
        val formBody =FormBody.Builder()
        formBody.add("username",username)
        formBody.add("userFollow",userFollow)
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

    //解析用户关系中粉丝、关注、收藏字段
    fun analysisUserRelationshipList(msg:String):List<String>{
        val regex = ";"
        return msg.split(regex)
    }
//
//    //合并用户关系中粉丝、关注、收藏字段
//    fun mergeUserRelationshipList(relationship:String):String{
//        val regex = ";"
//        return LightRainApplication.userRelationship?.userFollows+regex+relationship
//    }
//
//    //移除用户关系中粉丝、关注、收藏字段
//    fun removeUserRelationshipList(data:String,remove:String):String{
//        val regex = ";"
//        val list=analysisUserRelationshipList(data)
//        var msg=""
//        for (s in list) {
//            if (s != remove)
//                msg+=s+regex
//        }
//        return msg
//    }
//
//
//    //更新用户关系中粉丝、关注、收藏字段
//    fun addUserRelationshipList(data:String,add:String):String{
//        val regex = ";"
//        return data+regex+add
//    }


}