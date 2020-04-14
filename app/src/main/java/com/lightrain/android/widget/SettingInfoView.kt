package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.ui.activity.EditUserInfoActivity
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.UserInfoUtil
import kotlinx.android.synthetic.main.view_setting_info.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SettingInfoView :RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_setting_info,this)
        initView()
    }

    private fun initView() {
        if (UserInfoUtil.isLogin()){
            //用户已登录
            val userInfo=LightRainApplication.userInfoBean
            settingInfo.setOnClickListener { context.startActivity<EditUserInfoActivity>() } //跳转到用户个人信息修改界面
            userInfo?.userIcon?.let {
                ImageUtil.loadImageRoundedCorners(
                    context, settingInfoIcon, it)
            }
            settingInfoName.text=userInfo?.nickname
            settingInfoSubTitle.visibility= View.GONE
        }else{
            //未登录
            settingInfo.setOnClickListener { context.toast("请先登录") } //跳转到登陆界面
        }
    }


}