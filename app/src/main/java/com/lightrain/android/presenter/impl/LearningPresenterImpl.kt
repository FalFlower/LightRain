package com.lightrain.android.presenter.impl

import com.lightrain.android.LightRainApplication
import com.lightrain.android.model.UVBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.presenter.interf.LearningPresenter
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.ThreadUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.view.LearningView

class LearningPresenterImpl(private var view:LearningView) :LearningPresenter, ResponseHandler {
    private val userInfoBean=LightRainApplication.userInfoBean
    private var videoList=ArrayList<VideoBean>()
    private var uvList:List<UVBean>?=null
    private var count=0
    private var size=0
    override fun loadUVData() {
        //获取用户个人学习视频列表
        userInfoBean?.let {
            HttpManager.manager.
            sendRequestByGet(URLProviderUtils.getUVListByUsername(it.username),ResponseStatus.UV,this)
        }
    }

    override fun onError(msg: String?) {
        view.onError(msg)
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.UV->{
                //获取个人观看视频列表
                result?.let{
                    uvList=JsonUtil.getArrayResultResponse(it,UVBean::class.java).data
                    uvList?.let {
                        size=it.size
                        for (uv in it){//通过VideoID获取具体的Video信息
                            //list.add(getVideo(uv.videoId))
                            getVideo(uv.videoId)
                        }
                    }

                }
            }
            ResponseStatus.VIDEO->{
                count++
                val video=result?.let { JsonUtil.getResultResponse(it,VideoBean::class.java).data }
                video?.let { videoList.add(it) }
                if (count>=size){
                    uvList?.let {
                        ThreadUtil.runOnMainThread(Runnable {
                            view.onSuccess(it,videoList)
                        })
                    }
                }
            }
            else->{}
        }

    }

    private fun getVideo(videoId: String) {
        HttpManager.manager.sendRequestByGet(URLProviderUtils.getVideoInfo(videoId),
            ResponseStatus.VIDEO,this)
    }
}