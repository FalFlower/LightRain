package com.lightrain.android.ui.activity

import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.StringUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.util.UserInfoUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity :BaseActivity(), ResponseHandler {
    companion object{
        var app:LoginActivity?=null
    }
    override fun getLayoutId(): Int {
        app=this
        return R.layout.activity_login
    }



    override fun initListener() {
        loginBtn.setOnClickListener { login() }
        loginToPhone.setOnClickListener { startActivity<RegisterActivity>() }
        loginProblem.setOnClickListener {  }//跳转忘记密码界面
        loginWeChat.setOnClickListener {  }//微信登录
        loginQQ.setOnClickListener {  } //qq登录
        loginWeiBo.setOnClickListener {  } //微博登录
        loginBack.setOnClickListener { finish() }
    }

    private fun login(){
        val username=loginUsername.text.toString()
        val password=loginPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()){
            val md5Password=StringUtil.getMD5(password)
            md5Password?.let {
                HttpManager.manager
                    .sendRequestByGet(URLProviderUtils.getUserInfoLoginUrl(username,it),
                        ResponseStatus.LOGIN,this)
            }
        }
        else{
            toast("账号或密码不应为空")
        }

    }

    override fun onError(msg: String?) {
        runOnUiThread {
            toast("登陆失败：$msg")
//            toast("${runOnUiThread { toast("${msg?.let { JsonUtil.getResponseException(it).message }}") }}")
        }
        println("LoginActivity login failed,cause is $msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        runOnUiThread {
            toast("登陆成功")
            //登陆成功
            val userInfo=result?.let {
                JsonUtil.getResultResponse(it,UserInfoBean::class.java).data
            }
            LightRainApplication.userInfoBean= userInfo
            userInfo?.let {
                UserInfoUtil.loadSharedPreferences(this,it.username,it.password)
            }

        }
        finish()
    }
}