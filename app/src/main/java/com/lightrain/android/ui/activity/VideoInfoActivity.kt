package com.lightrain.android.ui.activity

import android.graphics.Color
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.adapter.VideoEvaluateListAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.UserRelationshipBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.model.VideoEvaluateBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.activity_video_info.*
import org.jetbrains.anko.startActivity

/*
* 写的太复杂，等有时间要提取一下控件
* */
class VideoInfoActivity :BaseActivity(), ResponseHandler {
    private var videoBean:VideoBean?=null
    private var userRelationship:UserRelationshipBean?=null
    override fun getLayoutId(): Int {
        videoBean=StartActivityUtil.getDataInVideoInfoActivity(intent)
        userRelationship=LightRainApplication.userRelationship
        return R.layout.activity_video_info
    }

    override fun initData() {
        if (videoBean?.videoPrice!=0f){
            //如果课程是非免费的
            videoInfoFreeBar.visibility=GONE //免费控件隐藏
            videoInfoNoFreeBar.visibility= VISIBLE //付费控件显示
        }else{
            videoInfoWatch.visibility= GONE
        }
        if (isFavorite()){
            //已经被收藏
            doFavoriteView()
        }
        WindowsUtil.setSystemUiVisibility(this, Color.BLACK)
        initView()
    }

    private fun doFavoriteView(){
        //未收藏->收藏
        ImageUtil.loadImage(this,videoInfoFavoriteIcon,R.mipmap.ic_favourite_done)
        videoInfoFavoriteTitle.text="取消收藏"

    }

    private fun unFavoriteView(){
        //收藏->未收藏
        ImageUtil.loadImage(this,videoInfoFavoriteIcon,R.mipmap.ic_favourite)
        videoInfoFavoriteTitle.text="收藏"
    }

    private fun initView() {
        videoInfoBac.alpha=0.5f
        videoBean?.let {
            ImageUtil.loadImageBlurTransformation(this,videoInfoIconBac,it.videoIcon,80)
            videoInfoTitle.text=it.videoTitle
            videoInfoBriefTitle.text=
                "${it.videoLabel.split(";")[0]} 初中" +
                        "·${it.videoTime/60}分钟·${it.videoLearningNum}人学·${it.videoScoreAva}分"
            if (it.videoPrice==0f){
                videoInfoPrice.text="￥免费"
            }else{
                videoInfoPrice.text="￥${it.videoPrice}"
            }
            videoInfoBrief.text=it.videoBrief
            videoInfoEvaluateBarView.setScore(this,it.videoScoreAva)
            getVideoEvaluateData()
        }
    }
    //获取用户评论信息
    private fun getVideoEvaluateData() {
        videoBean?.videoId?.let { URLProviderUtils.getVideoEvaluateByVideoId(it) }?.let {
            HttpManager.manager.sendRequestByGet(it,ResponseStatus.VIDEO_EVALUATE,this)
        }
    }

    //配置RecycleView
    private fun initRecycleView(evaluate: List<VideoEvaluateBean>) {
        val manager=LinearLayoutManager(this)
        manager.orientation=LinearLayoutManager.VERTICAL
        videoInfoRecyclerView.layoutManager=manager
        val adapter=VideoEvaluateListAdapter(this,evaluate)
        videoInfoRecyclerView.adapter=adapter
    }
    //判断该视频是否已经收藏
    private fun isFavorite():Boolean{
        if (userRelationship==null)
            return false
        else{
            val favoriteList= userRelationship!!.userFavourites.split(";")
            for (s in favoriteList) {
                if (s==videoBean?.videoId)
                    return true
            }
        }
        return false
    }

    override fun initListener() {
        videoInfoBack.setOnClickListener { finish() }
        videoInfoCart.setOnClickListener {
            //跳转到购物车界面
            startActivity<CartActivity>()
        }
        videoInfoShare.setOnClickListener {  }//设置点击事件
        videoInfoFavorite.setOnClickListener {
            //收藏
            if(UserInfoUtil.isLogin()){
                userRelationship?.let {
                    if (isFavorite()){
                        //已收藏,再次点击为取消收藏
                        it.userFavourites=removeFavorite(it.userFavourites)
                    }else{
                        //未收藏,再次点击为收藏
                        videoBean?.let {i->
                            if (it.userFavourites==" ")
                                it.userFavourites=i.videoId
                            else
                                it.userFavourites+=";${i.videoId}"
                        }
                    }
                    uploadFavorite(it)
                }
            }else{
                myToast("请先登录~")
            }

        }
        //根据当前价格进行决定是否加载试看
        videoInfoWatch.setOnClickListener {
            videoBean?.let { it1 -> StartActivityUtil.toVideoPlayActivity(this, it1) }
        }
        videoInfoAdviceFree.setOnClickListener {  }//咨询
        videoInfoAdviceNoFree.setOnClickListener {  }//咨询

        videoInfoLearningFree.setOnClickListener {
            if(UserInfoUtil.isLogin()){
                //免费课程直接加入学习记录并且进入视频播放界面
                updateLearningClass()
                videoBean?.let { it1 -> StartActivityUtil.toVideoPlayActivity(this, it1) }
            }else{
                myToast("请先登录~")
            }

        }

        videoInfoJoinCart.setOnClickListener {
            if(UserInfoUtil.isLogin()){
                //加入购物车
                videoBean?.videoId?.let { it1 -> CartUtil.joinCart(this, it1) }
            }else{
                myToast("请先登录~")
            }

        }
        videoInfoBuy.setOnClickListener {
            if(UserInfoUtil.isLogin()){
                //跳转到购买页面
                startActivity<CartActivity>()
            }else{
                myToast("请先登录~")
            }
        }
    }

    private fun removeFavorite(userFavourites: String): String {
        var favourite=""
        videoBean?.let {
            val list=userFavourites.split(";")
            for (s in list) {
                if (s!=it.videoId&&s.isNotEmpty()){
                    favourite+="$s;"
                }
            }
        }
        if (favourite=="")
            favourite+=" "
        return favourite
    }


    //更新个人学习视频
    private fun updateLearningClass() {
        val userInfo=LightRainApplication.userInfoBean
        if (videoBean!=null&&userInfo!=null){
            HttpManager.manager.
            sendRequestByPost(URLProviderUtils.
                updateUVInfo(), UserInfoUtil.
            getUpdateUVBody(videoBean!!.videoId,userInfo.username,0),ResponseStatus.UV,this)
        }
    }

    //上传收藏
    private fun uploadFavorite(u: UserRelationshipBean) {
        HttpManager.manager.sendRequestByPost(URLProviderUtils.updateUserRelationship(),
        UserInfoUtil.getUpdateUerRelationshipBody(u),ResponseStatus.USER_RELATIONSHIP,this)
    }

    override fun onError(msg: String?) {
        println("VideoInfoActivity error:$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.VIDEO_EVALUATE->{
                //获取到视频的评论列表,加载RecycleView或者加载隐藏界面
                val evaluate= result?.let { JsonUtil.getArrayResultResponse(it,VideoEvaluateBean::class.java).data }
                runOnUiThread{
                    if (evaluate?.isEmpty()!!){
                        videoInfoListNull.visibility= VISIBLE
                    }else{
                        initRecycleView(evaluate)
                    }
                }
            }
            ResponseStatus.USER_RELATIONSHIP->{
                runOnUiThread {
                    //更新收藏成功
                    val userRelationshipBean=
                        result?.let { JsonUtil.getResultResponse(it,UserRelationshipBean::class.java).data }
                    userRelationshipBean?.let {
                        LightRainApplication.userRelationship=it
                        userRelationship=it
                        if (isFavorite()){
                            doFavoriteView()
                        }else{
                            unFavoriteView()
                        }
                    }
                }
            }
            ResponseStatus.UV->{
                //更新用户观看视频成功
            }
            else->{}
        }
    }
}