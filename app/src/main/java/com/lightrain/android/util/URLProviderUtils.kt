package com.lightrain.android.util

object URLProviderUtils {
    private const val urlIp="http://120.78.219.199:8080/lr"
    //用户模块URL
    fun getUserInfoLoginUrl(username:String,password:String):String{
        return "$urlIp/user/login?username=$username&password=$password"
    }

    fun getUserInfoLogoutUrl(username:String):String{
        return "$urlIp/user/logout?username=$username"
    }

    fun getUserInfoRegister():String{
        return "$urlIp/user/register"
    }

    fun getUserInfoUpdate():String{
        return "$urlIp/user/update"
    }
    //获取中国省份名字
    fun getProvinces():String{
        return "http://guolin.tech/api/china"
    }
    //获取中国地级市名字
    fun getCities(provinceId:Int):String{
        return "http://guolin.tech/api/china/$provinceId"
    }
    //获取中国县级市区名字
    fun getCounty(provinceId:Int,cityId:Int):String{
        return "http://guolin.tech/api/china/$provinceId/$cityId"
    }


}