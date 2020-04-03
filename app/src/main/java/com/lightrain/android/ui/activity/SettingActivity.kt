package com.lightrain.android.ui.activity

import android.view.View
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.util.UserInfoUtil
import com.lightrain.android.view.SettingInfoView
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity :BaseActivity(), ResponseHandler {
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun onResume() {
        super.onResume()
        settingInfoView.removeAllViews()
        settingInfoView.addView(SettingInfoView(this))
    }

    override fun initData() {
        //设置toolbar
        settingToolBar.initData(this)
        settingToolBar.setTitle("设置")
        settingPush.setTitle("推送设置","")
        settingPush.hindLine()
        settingPlay.setTitle("播放设置","")
        settingVersion.setTitle("版本更新","")
        settingMark.setTitle("给我评分","")
        settingAgreement.setTitle("用户协议","")
        settingPrivacy.setTitle("隐私协议","")
        settingAbout.setTitle("关于慕课","")
    }

    override fun initListener() {
        if (UserInfoUtil.isLogin()){
            settingQuitLogin.setOnClickListener {
                //退出登录
                LightRainApplication.userInfoBean?.username?.let { it1 ->
                    URLProviderUtils.getUserInfoLogoutUrl(
                        it1
                    )
                }?.let { it2 ->
                    HttpManager.manager
                        .sendRequestByGet(
                            it2,
                            ResponseStatus.LOGOUT,this)
                }
            }
        }else //如果用户未登录，隐藏掉注销按钮
            settingQuitLogin.visibility=View.GONE


        settingPush.setOnClickListener {  } //推送设置
        settingPlay.setOnClickListener {  } //播放设置
        settingVersion.setOnClickListener {  } //版本更新
        settingMark.setOnClickListener {  } //给我评分
        settingAgreement.setOnClickListener {  } //用户协议
        settingPrivacy.setOnClickListener {  } //隐私协议
        settingAbout.setOnClickListener {  } //关于慕课

    }

    override fun onError(msg: String?) {
        msg?.let { JsonUtil.getResponseException(it).message }?.let { myToast(it) }
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        //成功退出登录
        myToast("注销成功")
        //清空用户个人信息数据
        LightRainApplication.userInfoBean=null
        UserInfoUtil.removeSharedPreferences(this)
        finish()
    }
}