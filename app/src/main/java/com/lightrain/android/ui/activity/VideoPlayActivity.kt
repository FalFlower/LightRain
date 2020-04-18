package com.lightrain.android.ui.activity

import android.graphics.Color
import android.net.Uri
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jzvd.Jzvd
import com.lightrain.android.R
import com.lightrain.android.adapter.VideoEvaluateListAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.model.VideoEvaluateBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.activity_video_play.*
import org.jetbrains.anko.startActivity

class VideoPlayActivity :BaseActivity(), ResponseHandler {
    private val videoBean by lazy { StartActivityUtil.getDataInVideoPlayActivity(intent) }//获取传递来的数据
    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initData() {
        WindowsUtil.setSystemUiVisibility(this,Color.BLACK)
        videoBean?.let {
            videoPlayJzVideo.setUp(it.videoUrl, it.videoTitle)
            initView(it)//加载界面
            getTeacherData(it.username)//获取教师信息
            getVideoEvaluateData()
        }

    }

    //获取用户评论信息
    private fun getVideoEvaluateData() {
        videoBean?.videoId?.let { URLProviderUtils.getVideoEvaluateByVideoId(it) }?.let {
            HttpManager.manager.sendRequestByGet(it,ResponseStatus.VIDEO_EVALUATE,this)
        }
    }
    private fun getTeacherData(username: String) {
        HttpManager.manager.
        sendRequestByGet(URLProviderUtils.getUserInfo(username),ResponseStatus.USER_INFO,this)
    }

    private fun initView(data: VideoBean) {
        videoPlayTitle.text=data.videoTitle
        videoPlayBrief.text=data.videoBrief
        videoPlayEvaluateBarView.setScore(this,data.videoScoreAva)
    }

    private fun initRecycleView(evaluate: List<VideoEvaluateBean>){
        //设置RecycleView
        val manager= LinearLayoutManager(this)
        manager.orientation=LinearLayoutManager.VERTICAL
        videoPlayRecyclerView.layoutManager=manager
        val adapter= VideoEvaluateListAdapter(this,evaluate)
        videoPlayRecyclerView.adapter=adapter
        videoPlayRecyclerView
    }
    override fun initListener() {
        //videoPlayBack.setOnClickListener { finish() }
        videoPlayPoints.setOnClickListener {  }  //省略号点击事件
        videoPlayEvaluate.setOnClickListener {
            //跳转到评论界面
            videoBean?.let { it1 -> StartActivityUtil.toEvaluateActivity(this, it1) }
        }
        videoPlayUpDown.setOnClickListener {  }//下载
        videoPlayShare.setOnClickListener {  } //分享
    }

    override fun onError(msg: String?) {
        println("VideoPlayActivity error $msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.USER_INFO->{
                runOnUiThread {
                    val userInfo= result?.let { JsonUtil.getResultResponse(it,UserInfoBean::class.java).data }
                    userInfo?.let {
                        initTeacherView(it)
                    }
                }
            }
            ResponseStatus.VIDEO_EVALUATE->{
                //获取到视频的评论列表,加载RecycleView或者加载隐藏界面
                val evaluate= result?.let { JsonUtil.getArrayResultResponse(it,VideoEvaluateBean::class.java).data }
                runOnUiThread{
                    if (evaluate?.isEmpty()!!){
                        videoPlayListNull.visibility= VISIBLE
                    }else{
                        initRecycleView(evaluate)
                    }
                }
            }
            else->{}
        }
    }
    //加载教师模块View
    private fun initTeacherView(userInfo: UserInfoBean) {
        ImageUtil.loadImageRoundedCorners(this,videoPlayTeacherIcon,userInfo.userIcon)
        videoPlayTeacherNickname.text=userInfo.nickname
        videoPlayTeacherSign.text=userInfo.userSign
        videoPlayTeacherLayout.setOnClickListener {
            //跳转到用户详情界面
            StartActivityUtil.toPersonalSpaceActivity(this,userInfo)
        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}