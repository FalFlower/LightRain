package com.lightrain.android.util

object URLProviderUtils {
    private const val urlIp="http://120.78.219.199:8080/lr"


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

}