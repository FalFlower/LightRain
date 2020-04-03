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
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity(), ResponseHandler {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initListener() {
        registerBtn.setOnClickListener {
            //待接入短信验证
            register()
        }
        registerBack.setOnClickListener { finish() }
    }

    private fun register(){
        val username=registerUsername.text.toString()
        val password=registerPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()){
            val md5Password=StringUtil.getMD5(password)
            md5Password?.let{
                HttpManager.manager
                    .sendRequestByPost(URLProviderUtils.getUserInfoRegister(),
                        UserInfoUtil.getDefaultUerInfoBody(username,it),
                        ResponseStatus.REGISTER,this)
            }
        }
        else{
            toast("账号或密码不应为空")
        }
    }

    override fun onError(msg: String?) {
        runOnUiThread { toast("${msg?.let { JsonUtil.getResponseException(it).message }}") }
        println("RegisterActivity register failed , causer is :$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        //注册成功

        when(status){
            ResponseStatus.REGISTER->{runOnUiThread { toast("注册成功") }
                val userInfo=result?.let { JsonUtil.getResultResponse(it, UserInfoBean::class.java).data }
                LightRainApplication.userInfoBean= userInfo
                userInfo?.let {
                    UserInfoUtil.loadSharedPreferences(this,it.username,it.password)
                }
                login()
            }

            ResponseStatus.LOGIN->{
                LoginActivity.app?.finish()
                finish()
            }
            else -> {}
        }

    }

     private fun login() {
         val username=LightRainApplication.userInfoBean?.username
         val password=LightRainApplication.userInfoBean?.password
         if (username!=null&&password!=null){
             HttpManager.manager
                 .sendRequestByGet(
                     URLProviderUtils.getUserInfoLoginUrl(username,password),
                     ResponseStatus.LOGIN,this)
         }
    }

}