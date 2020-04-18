package com.lightrain.android.util

import android.content.Context
import org.jetbrains.anko.toast

object CartUtil {
    //加入购物车
    fun joinCart(context: Context, videoId:String){
        if (!isExit(context,videoId)){
            //如果尚未加入购物车
            var videoList=getCartList(context)
            val cartInfo=context.getSharedPreferences("cart_info", Context.MODE_PRIVATE)
            val editor=cartInfo.edit()
            if (videoList=="")
                videoList=videoId
            else
                videoList+=";$videoId"
            editor.putString("videoList",videoList)
            editor.apply() //提交修改
            context.toast("加入成功")
        }else{
            context.toast("商品已存在")
        }
    }

    //移除本地存储用户信息
    fun removeSharedPreferences(context: Context){
        val cartInfo=context.getSharedPreferences("cart_info", Context.MODE_PRIVATE)
        val editor=cartInfo.edit()
        editor.clear()
        editor.apply() //提交修改
    }

    fun getCartList(context: Context):String{
        val cartInfo=context.getSharedPreferences("cart_info", Context.MODE_PRIVATE)
        return cartInfo.getString("videoList","").toString()
    }

    fun getCartListArray(context: Context): List<String>? {
        val videoList=getCartList(context)
        return videoList.split(";")
    }

    fun isExit(context: Context,videoId:String):Boolean{
        val list= getCartListArray(context) ?: return false
        for (s in list){
            if (s == videoId)
                return true
        }
        return false
    }
}