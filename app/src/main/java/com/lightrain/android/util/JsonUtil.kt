package com.lightrain.android.util

import com.google.gson.Gson
import com.lightrain.android.model.ResponseException
import com.lightrain.android.model.ResultResponse
import com.lightrain.android.model.UpLoadResponse
import com.lightrain.android.model.UpLoadResponseException
import com.lightrain.android.model.weather.City
import com.lightrain.android.model.weather.County
import com.lightrain.android.model.weather.Province
import ikidou.reflect.TypeBuilder
import org.json.JSONArray
import org.json.JSONObject

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

    //解析地址(省、市、区)
    fun getProvinceResponse(data: String):List<Province>{
        val list=ArrayList<Province>()
        val allProvinces=JSONArray(data)
        val num= 0 until allProvinces.length()
        for (i in num) {
            val provinceObject=allProvinces.getJSONObject(i)
            list.add(Province(provinceObject.getString("name"),provinceObject.getInt("id")))
        }
        return list
    }

    fun getCityResponse(data: String,provinceId:Int):List<City>{
        val list=ArrayList<City>()
        val allProvinces=JSONArray(data)
        val num= 0 until allProvinces.length()
        for (i in num) {
            val provinceObject=allProvinces.getJSONObject(i)
            list.add(City(provinceObject.getString("name"),provinceObject.getInt("id"),provinceId))
        }
        return list
    }

    fun getCountyResponse(data: String,cityId:Int):List<County>{
        val list=ArrayList<County>()
        val allProvinces=JSONArray(data)
        val num= 0 until allProvinces.length()
        for (i in num) {
            val provinceObject=allProvinces.getJSONObject(i)
            list.add(County(provinceObject.getString("name"),
                provinceObject.getString("weather_id"),
                cityId))
        }
        return list
    }

    //解析Upload（又拍云）返回正确数据
    fun getUploadResponse(data: String):UpLoadResponse{
        val type=TypeBuilder.newInstance(UpLoadResponse::class.java).build()
        return gson.fromJson(data, type)
    }

    fun getUploadResponseException(msg:String):UpLoadResponseException{
        val type=TypeBuilder.newInstance(UpLoadResponseException::class.java).build()
        return gson.fromJson(msg, type)
    }
}