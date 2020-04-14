package com.lightrain.android.ui.activity

import android.graphics.Color
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.adapter.PersonalSpaceAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.*
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.activity_personal_space.*

class PersonalSpaceActivity :BaseActivity(), ResponseHandler {
    var userInfo:UserInfoBean?=null
    var adapter:PersonalSpaceAdapter?=null
    private var userRelationship=LightRainApplication.userRelationship
    private var otherUserInfo:UserInfoBean?=null
    var list=ArrayList<VideoBean>()
    var count=0
    var size=0
    override fun getLayoutId(): Int {
        return R.layout.activity_personal_space
    }

    override fun initData() {
        userInfo=LightRainApplication.userInfoBean
        initTitleView()
        getVideoList()
        WindowsUtil.setSystemUiVisibility(this, Color.BLACK)
    }

    override fun initListener() {
        otherUserInfo=StartActivityUtil.getDataInPersonalSpaceActivity(intent)
        if (otherUserInfo==null){
            //自己查看
            personalSpaceFollow.visibility=GONE
            personalSpaceUnFollow.visibility= GONE
        }else{//查看别人
            userRelationship?.let {
                if (UserInfoUtil.isFollow(it.userFollows)){//如果已关注该用户
                    personalSpaceFollow.visibility=GONE
                }else{//如果未关注
                    personalSpaceUnFollow.visibility= GONE
                }
            }

        }
        personalSpaceUnFollow.setOnClickListener {
            doUnFollow()//取消关注
        }
        personalSpaceFollow.setOnClickListener {
            doFollow()//关注别人
        }
    }

    private fun doUnFollow() {
        otherUserInfo?.let {
            userInfo?.username?.let { it1 -> UserInfoUtil.getFollowOrUnFollowBody(it1,it.username) }?.let { it2 ->
                HttpManager.manager.sendRequestByPost(URLProviderUtils.unFollowUserRelationship(),
                    it2,ResponseStatus.UN_FOLLOW,this)
            }
        }
    }

    private fun doFollow() {
        otherUserInfo?.let {
            userInfo?.username?.let { it1 -> UserInfoUtil.getFollowOrUnFollowBody(it1,it.username) }?.let { it2 ->
                HttpManager.manager.sendRequestByPost(URLProviderUtils.followUserRelationship(),
                    it2,ResponseStatus.FOLLOW,this)
            }
        }
    }

    private fun getVideoList() {
        userInfo?.let {
            HttpManager.
            manager.sendRequestByGet(
                URLProviderUtils.getUVListByUsername(it.username),
                ResponseStatus.UV,this)
        }
    }


    private fun initTitleView() {
        personalSpaceBac.alpha=0.5f
        //加载用户模块
        userInfo?.let{
            ImageUtil.loadImageBlurTransformation(this, personalSpaceIconBac, it.userIcon,80)
            ImageUtil.loadImageRoundedCorners(this, personalSpaceIcon,it.userIcon)
            if (it.userGender==UserInfoUtil.USER_GENDER_MAN)
                ImageUtil.loadImage(this,personalSpaceGender,R.mipmap.ic_gender_man)
            else
                ImageUtil.loadImage(this,personalSpaceGender,R.mipmap.ic_gender_woman)
            //加载签字（需要后台更改）
            personalSpaceSign.text=it.userSign
            userRelationship?.let {relationship->
                personalSpaceFollowNum.text=relationship.userFollowsNum.toString()
                personalSpaceFansNum.text=relationship.userFansNum.toString()
                personalSpaceIntegralNum.text=relationship.userIntegral.toString()
            }

            personalSpaceToolbar.title=it.nickname
            //设置toolbar
            setSupportActionBar(personalSpaceToolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            personalSpaceToolbar.setNavigationIcon(R.mipmap.ic_arrow_left_white)
            personalSpaceToolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (LightRainApplication.userRelationship==null){
            userRelationship?.let {
                it.userFollowsNum++
            }
            LightRainApplication.userRelationship=userRelationship
        }
    }

    override fun onError(msg: String?) {
        runOnUiThread { msg?.let { myToast(it) } }
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        runOnUiThread {
            when(status){
                ResponseStatus.UN_FOLLOW->{
                    //取消关注成功
                    runOnUiThread {
                        personalSpaceFollow.visibility=VISIBLE
                        personalSpaceUnFollow.visibility= GONE//隐藏关注控件
                        LightRainApplication.userRelationship=
                            result?.let { JsonUtil.getResultResponse(it,UserRelationshipBean::class.java).data }

                    }
                }
                ResponseStatus.FOLLOW->{
                    //关注成功
                    runOnUiThread {
                        personalSpaceFollow.visibility=GONE //隐藏关注控件
                        personalSpaceUnFollow.visibility=VISIBLE
                        LightRainApplication.userRelationship=
                            result?.let { JsonUtil.getResultResponse(it,UserRelationshipBean::class.java).data }

                    }
                }
                ResponseStatus.UV->{
                    result?.let {
                        //获取用户观看视频列表
                        val uvList: List<UVBean>? =JsonUtil.getArrayResultResponse(it,UVBean::class.java).data
                        if (uvList != null) {
                            size=uvList.size
                            for (uv in uvList){//通过VideoID获取具体的Video信息
                                //list.add(getVideo(uv.videoId))
                                getVideo(uv.videoId)
                            }
                        }
                    }
                }
                ResponseStatus.VIDEO->{
                    count++
                    val video=result?.let { JsonUtil.getResultResponse(it,VideoBean::class.java).data }
                    video?.let { list.add(it) }
                    if (count==size-1){
                        adapter= PersonalSpaceAdapter(list)
                        initRecycleView()
                    }
                }
                else -> {}
            }

        }

    }

    private fun getVideo(videoId: String){
        HttpManager.manager.sendRequestByGet(URLProviderUtils.getVideoInfo(videoId),
            ResponseStatus.VIDEO,this)
    }

    private fun initRecycleView() {
        adapter= PersonalSpaceAdapter(list)
        //设置recycleView
        val manager=LinearLayoutManager(this)
        manager.orientation= LinearLayoutManager.VERTICAL
        personalSpaceRecyclerView.layoutManager=manager
        personalSpaceRecyclerView.adapter=adapter
        personalSpaceRecyclerView.itemAnimator=DefaultItemAnimator()
    }


}