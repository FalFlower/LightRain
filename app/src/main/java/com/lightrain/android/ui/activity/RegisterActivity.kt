package com.lightrain.android.ui.activity

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_code.view.*
import org.jetbrains.anko.toast
import kotlin.concurrent.thread


class RegisterActivity : BaseActivity(), ResponseHandler {
    var username=""
    var password=""
    var code=""
    var userCode=""
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initListener() {
        registerBtn.setOnClickListener {
            username=registerUsername.text.toString()
            password=registerPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()){
                //待接入短信验证
                verificationCode()
            }
            else{
                toast("账号或密码不应为空")
            }
        }
        registerBack.setOnClickListener { finish() }
    }

    private fun verificationCode() {
        sendMessage()//发送短信验证
        val title="注册"
        val dialog= AlertDialog.Builder(this)
        val dialogView=LayoutInflater.from(this).inflate(R.layout.dialog_code,null)
        dialogView.dialogCodeBtn.setOnClickListener {//重新发送验证码
            count(dialogView)
            sendMessage()
        }
        count(dialogView)
        dialog.setTitle(title)
        dialog.setView(dialogView)
        dialog.setPositiveButton("确定"
        ) { _, _ ->
            userCode=dialogView.dialogCodeEditText.text.toString()
            if (userCode.equals("")){
                myToast("验证码不应为空")
            }else{
                if (code.equals(userCode)){//验证码正确
                    register()
                }else{
                    myToast("验证码错误，请重新输入")
                }
            }

        }
        dialog.show()
    }
    fun count(dialogView:View) {
        dialogView.dialogCodeBtn.isClickable=false
        thread{
            val l= 10 downTo 1
            for (i in l) {
                try {
                    Thread.sleep(1000) // 线程休眠实现读秒功能
                    ThreadUtil.runOnMainThread(Runnable { dialogView.dialogCodeBtn.text="${i}S" })
                } catch (e: InterruptedException) {
                }
            }
            ThreadUtil.runOnMainThread(Runnable {
                dialogView.dialogCodeBtn.isClickable=true
                dialogView.dialogCodeBtn.text= "重新发送验证码"
            })
        }
    }
    private fun sendMessage() {
        code=StringUtil.getVerificationCode()
        println("!!!!code:$code")
        HttpManager.manager.sendYouPaiVerificationCode(code,username,object :ResponseHandler{
            override fun onError(msg: String?) {
                myToast("发送短信失败:$msg")
            }

            override fun onSuccess(status: ResponseStatus, result: String?) {
                //注册成功
                println("sendMessage success status:$status result:$result")
            }

        })
    }

    private fun register(){
        val md5Password=StringUtil.getMD5(password)
        md5Password?.let{
            HttpManager.manager
                .sendRequestByPost(URLProviderUtils.getUserInfoRegister(),
                    UserInfoUtil.getDefaultUerInfoBody(username,it),
                    ResponseStatus.REGISTER,this)
        }
    }

    override fun onError(msg: String?) {
        runOnUiThread {
            toast("注册失败：${msg}")
        }
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