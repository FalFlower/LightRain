package com.lightrain.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.ui.activity.LoginActivity
import com.lightrain.android.ui.activity.PersonalSpaceActivity
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.UserInfoUtil
import kotlinx.android.synthetic.main.view_login.view.*
import kotlinx.android.synthetic.main.view_no_login.view.*
import org.jetbrains.anko.startActivity

class InfoLoginView : RelativeLayout {
    private var userInfo:UserInfoBean?=null
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        initView()
    }

    fun initView() {

        if (!UserInfoUtil.isLogin()){
            View.inflate(context,R.layout.view_no_login,this) //如果没登陆，就加载未登录View
            ImageUtil.loadImageRoundedCorners(context,infoNoLoginImg, R.mipmap.ic_login_no)
            infoNoLoginImg.setOnClickListener { context.startActivity<LoginActivity>() }
            infoNoLoginTitle.setOnClickListener {  context.startActivity<LoginActivity>() }
        }

        else{
            View.inflate(context,R.layout.view_login,this)  //如果登陆，就加载登录view
            userInfo=LightRainApplication.userInfoBean
            infoLoginImg.setOnClickListener {  }//头像 点开头像详情
            //跳转到个人详情界面
            infoLoginTitle.setOnClickListener { context.startActivity<PersonalSpaceActivity>() }//昵称
            infoLoginTextLearning.setOnClickListener { context.startActivity<PersonalSpaceActivity>() }//学习时长
            infoLoginTextException.setOnClickListener {  context.startActivity<PersonalSpaceActivity>()}//经验
            //跳转到粉丝/关注界面
            infoLoginTextFollow.setOnClickListener {  }//关注
            infoLoginTextFans.setOnClickListener {  }//粉丝
            //跳转到积分详情
            infoLoginTextIntegral.setOnClickListener {  }//积分
            initData()
        }
    }

    private fun initData(){
        userInfo?.let {
            ImageUtil.loadImageRoundedCorners(context,infoLoginImg, it.userIcon)
            infoLoginTitle.text=it.nickname
            if (it.userGender==UserInfoUtil.USER_GENDER_MAN)
                ImageUtil.loadImage(context,infoUserGender,R.mipmap.ic_gender_man)
            else
                ImageUtil.loadImage(context,infoUserGender,R.mipmap.ic_gender_woman)
            infoLoginTextLearning.text="0 小时" //添加学习时长
            infoLoginTextException.text="0" //添加经验值
            infoLoginTextFollow.text="0" //关注数量
            infoLoginTextFans.text="0" //粉丝数量
            infoLoginTextIntegral.text="0" //积分数量
        }

    }
}