package com.lightrain.android.util

import com.google.gson.Gson
import com.lightrain.android.model.ResponseException
import com.lightrain.android.model.ResultResponse
import ikidou.reflect.TypeBuilder

object JsonUtil {
    private val gson = Gson()

    //解析Object类型的返回数据
    fun <T>getResultResponse(data:String,clazz:Class<T>):ResultResponse<T>{
        val type=TypeBuilder.newInstance(ResultResponse::class.java)//TypeBuilder来解决java中泛型擦除问题
            .addTypeParam(clazz)
            .build()
        return gson.fromJson(data, type)
    }
    //用来解析异常
    fun getResponseException(data:String): ResponseException {
        val type=TypeBuilder.newInstance(ResponseException::class.java).build()
        return gson.fromJson(data, type)
    }

}