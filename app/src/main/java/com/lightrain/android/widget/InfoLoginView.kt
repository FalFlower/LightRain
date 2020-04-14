package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.model.UVBean
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.UserRelationshipBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.ui.activity.LoginActivity
import com.lightrain.android.ui.activity.PersonalSpaceActivity
import com.lightrain.android.ui.activity.UserRelationshipActivity
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.view_login.view.*
import kotlinx.android.synthetic.main.view_no_login.view.*
import org.jetbrains.anko.startActivity

class InfoLoginView : RelativeLayout, ResponseHandler {
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

    private fun initView() {
        if (!UserInfoUtil.isLogin()){
            View.inflate(context,R.layout.view_no_login,this) //如果没登陆，就加载未登录View
            ImageUtil.loadImageRoundedCorners(context,infoNoLoginImg, R.mipmap.ic_login_no)
            infoNoLoginImg.setOnClickListener { context.startActivity<LoginActivity>() }
            infoNoLoginTitle.setOnClickListener {  context.startActivity<LoginActivity>() }
        }

        else{
            View.inflate(context,R.layout.view_login,this)  //如果登陆，就加载登录view
            initData()
            initListener()
        }
    }

    private fun loadView(userRelationship: UserRelationshipBean?) {
        userInfo?.let {
            ImageUtil.loadImageRoundedCorners(context,infoLoginImg, it.userIcon)
            infoLoginTitle.text=it.nickname
            if (it.userGender==UserInfoUtil.USER_GENDER_MAN)
                ImageUtil.loadImage(context,infoUserGender,R.mipmap.ic_gender_man)
            else
                ImageUtil.loadImage(context,infoUserGender,R.mipmap.ic_gender_woman)
        }
        userRelationship?.let {
            infoLoginTextFollow.text=it.userFollowsNum.toString() //关注数量
            infoLoginTextFans.text=it.userFansNum.toString() //粉丝数量
            infoLoginTextIntegral.text=it.userIntegral.toString() //积分数量
        }
    }

    private fun initListener() {
        infoLoginImg.setOnClickListener {  }//头像 点开头像详情
        //跳转到个人详情界面
        infoLoginTitle.setOnClickListener { context.startActivity<PersonalSpaceActivity>() }//昵称
        infoLoginTextLearning.setOnClickListener { context.startActivity<PersonalSpaceActivity>() }//学习时长
        infoLoginTextException.setOnClickListener {  context.startActivity<PersonalSpaceActivity>()}//经验
        //跳转到粉丝/关注界面
        infoLoginFollowLayout.setOnClickListener { context.startActivity<UserRelationshipActivity>() }//关注
        infoLoginFansLayout.setOnClickListener {  context.startActivity<UserRelationshipActivity>()}//粉丝
        //跳转到积分详情
        infoLoginIntegralLayout.setOnClickListener {  }//积分
    }

    private fun initData(){
        userInfo=LightRainApplication.userInfoBean
        userInfo?.let {
            HttpManager.
                manager.sendRequestByGet(
                    URLProviderUtils.getUserRelationshipInfo(it.username),
                    ResponseStatus.USER_RELATIONSHIP,this)
            HttpManager.
                manager.sendRequestByGet(
                    URLProviderUtils.getUVListByUsername(it.username),
                    ResponseStatus.UV,this)
        }

    }

    override fun onError(msg: String?) {
        println("InfoLoginView error:$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.USER_RELATIONSHIP->{
                val userRelationship= result?.let { JsonUtil.getResultResponse(it, UserRelationshipBean::class.java).data }
                ThreadUtil.runOnMainThread(Runnable { loadView(userRelationship) })
            }
            ResponseStatus.UV->{
                val uvList: List<UVBean>? = result?.let { JsonUtil.getArrayResultResponse(it, UVBean::class.java).data }
                var learningTime=0
                uvList?.let {
                    for (uvBean in it) {
                        learningTime+=uvBean.videoProgress
                    }
                }
                ThreadUtil.runOnMainThread(Runnable {
                    infoLoginTextLearning.text="${(learningTime/60)} 小时"//添加学习时长
                    infoLoginTextException.text=(learningTime/3).toString() //添加经验值
                })
            }
            else -> {}
        }

    }
}