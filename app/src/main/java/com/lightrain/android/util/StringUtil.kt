package com.lightrain.android.util

import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

//用于处理String类型的工具类
object StringUtil {
    const val PRICE_SYMBOL="¥"
    /**
     * 判断match是否在beMatch中
     * */
    fun isMatch(match:String,beMatch:String):Boolean{
        val res= BmMatch(match).match(beMatch)
        return res.isEmpty()
    }

    /**
     * 对字符串md5加密(小写+字母) 32位
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    fun getMD5(str: String): String? {
        return try {
            // 生成一个MD5加密计算摘要
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            // 计算md5函数
            md.update(str.toByteArray())
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            BigInteger(1, md.digest()).toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    //得到时间的HTTP-GMT格式字符串: (Wed, 15 Nov 1995 06:25:24 GMT)
    fun long2GMT(time: Long):String{
        val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(time)
    }
    //随机生成一个六位验证码
    fun getVerificationCode():String{
        val string= arrayOf("0","1","2","3","4","5","6","7","8","9")
        val num=0..5
        var code=""
        for (i in num) {
            code+=string[Random().nextInt(string.size)]
        }
        return code
    }

    //生成唯一主键(时间+随机数)  synchronized关键字防止重复出现同样的数字
    @Synchronized
    fun genUniqueKey(): String {
        val random = Random()
        val a = random.nextInt(900000) + 100000 //生成六位随机数
        return System.currentTimeMillis().toString() + a.toString()
    }
}