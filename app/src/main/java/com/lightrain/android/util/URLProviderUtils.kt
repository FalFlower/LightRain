package com.lightrain.android.util

object URLProviderUtils {
    private const val urlIp="http://120.78.219.199:8080/lr"
    const val youPaiMessage="https://sms-api.upyun.com/api/messages"
    /*
    * 用户模块URL
    * */
    fun getUserInfoLoginUrl(username:String,password:String):String{
        return "$urlIp/user/login?username=$username&password=$password"
    }//登录

    fun getUserInfoLogoutUrl(username:String):String{
        return "$urlIp/user/logout?username=$username"
    }//注销

    fun getUserInfoRegister():String{
        return "$urlIp/user/register"
    }//注册

    fun getUserInfoUpdate():String{
        return "$urlIp/user/update"
    }//更新

    fun getUserInfo(username:String):String{
        return "$urlIp/user/info?username=$username"
    }//获取用户信息

    fun getUserRelationshipInfo(username:String):String{
        return "$urlIp/user/relationship/info?username=$username"
    }//获取用户关系（粉丝、关注、收藏列表）

    fun updateUserRelationship():String{
        return "$urlIp/user/relationship/update"
    }//更新用户关系（粉丝、关注、收藏列表）

    fun followUserRelationship():String{
        return "$urlIp/user/relationship/follow"
    }//关注
    fun unFollowUserRelationship():String{
        return "$urlIp/user/relationship/unFollow"
    }//取消关注

    /*
    * 视频模块URL
    * */

    fun getVideoInfo(videoId:String):String{
        return "$urlIp/video/info?videoId=$videoId"
    }//获取指定视频详细信息

    fun getAllVideoInfo():String{
        return "$urlIp/video/info/all"
    }//获取全部视频

    fun updateVideo(videoId:String):String{
        return "$urlIp/video/info/update"
    }//

    fun getUVInfo(videoId:String,username:String):String{
        return "$urlIp/video/uv/get?videoId=$videoId&username=$username"
    }//获取用户观看指定视频时长和最近观看时间

    fun updateUVInfo(videoId:String,username:String,videoProgress:Int):String{
        return "$urlIp/video/uv/update?videoId=$videoId&username=$username&videoProgress=$videoProgress"
    }//更新用户观看视频信息（增添或者修改）

    fun getUVListByUsername(username:String):String{
        return "$urlIp/video/uv/get/all/user?username=$username"
    }//获取指定用户的观看视频列表

    fun getUVListByVideoId(videoId:String):String{
        return "$urlIp/video/uv/get/all/video?videoId=$videoId"
    }//获取指定视频的观看用户列表

    fun updateVideoEvaluate(videoId:String):String{
        return "$urlIp/video/evaluate/update"
    }// 增加用户对指定视频的评分

    fun getVideoEvaluateByVideoId(videoId:String):String{
        return "$urlIp/video/evaluate/get/video?videoId=$videoId"
    }//获取指定视频的用户评价列表

    fun getVideoEvaluateByUsername(username:String):String{
        return "$urlIp/video/evaluate/get/user?username=$username"
    }//获取用户的评价过视频列表

//    fun matchLabel(videoLabel:String):String{
//        return "$urlIp/video/match/label?videoLabel=$videoLabel"
//    }//搜索匹配——标签
//
//    fun matchTeacher(username:String):String{
//        return "$urlIp/video/match/teacher?username=$username"
//    }//搜索匹配——教师ID

    fun videoMatch(search:String):String{
        return "$urlIp/video/match?search=$search"
    }// 搜索匹配——（标签、教师ID、标题）

    /*
    * 获取地区名称URL
    * */
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

    /*
    * 获取图片资源URL
    * */
    //获取轮播图图片
    fun getBannerImg():List<String>{
        val list=ArrayList<String>()
        list.add("http://zt-data.test.upcdn.net/banner/banner_1.jpg")
        list.add("http://zt-data.test.upcdn.net/banner/banner_2.png")
        list.add("http://zt-data.test.upcdn.net/banner/banner_3.png")
        list.add("http://zt-data.test.upcdn.net/banner/banner_4.png")
        list.add("http://zt-data.test.upcdn.net/banner/banner_5.png")
        return list
    }
    //获取次轮播图图片（品质差点）
    fun getBannerCImg():List<String>{
        val list=ArrayList<String>()
        list.add("http://zt-data.test.upcdn.net/banner/c/banner_c_1.jpg")
        list.add("http://zt-data.test.upcdn.net/banner/c/banner_c_2.jpg")
        list.add("http://zt-data.test.upcdn.net/banner/c/banner_c_3.jpg")
        return list
    }
    //获取视频自选优质封面
    fun getVideoIcon():List<String>{
        val list=ArrayList<String>()
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_1.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_2.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_3.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_4.png")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_5.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_6.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_7.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_8.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_9.jpg")
        list.add("http://zt-data.test.upcdn.net/video/icon/video_icon_10.jpg")

        return list
    }
}