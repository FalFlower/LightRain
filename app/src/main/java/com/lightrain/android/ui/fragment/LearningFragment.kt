package com.lightrain.android.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.LearningListAdapter
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.model.UVBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.presenter.impl.LearningPresenterImpl
import com.lightrain.android.util.ThreadUtil
import com.lightrain.android.view.LearningView
import kotlinx.android.synthetic.main.fragment_learning.*


class LearningFragment : BaseFragment(), LearningView {
    private var uvList:List<UVBean>?=null
    private var videoList: List<VideoBean>?=null
    private val presenter by lazy { LearningPresenterImpl(this) }
    override fun intView(): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_learning,null)
    }

    override fun initData() {
        presenter.loadUVData()
    }

    override fun initListener() {
        learningDownload.setOnClickListener {  }//跳转到下载界面
        learningRegisterOnes.setOnClickListener {  }//跳转到签到界面
        learningFavourite.setOnClickListener {  }//跳转到收藏界面
        learningQA.setOnClickListener {  }//跳转到问答界面
        learningNote.setOnClickListener {  }//跳转到笔记界面
        learningHandWrite.setOnClickListener {  }//跳转到手记界面
        learningOnlyShow.setOnClickListener {  }//跳转到专栏界面
        learningSelect.setOnClickListener {  }//进行筛选
        learningRefreshLayout.setOnRefreshListener{
            presenter.loadUVData()
            learningRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }
        learningRefreshLayout.setOnLoadMoreListener{
            presenter.loadUVData()
            learningRefreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
    }
    /*
    * 处理P层返回数据
    * */
    override fun onError(msg: String?) {
        println("LearningFragment error:$msg")
    }

    override fun onSuccess(uvList:List<UVBean>, videoList: List<VideoBean>) {
        //println("!!!!!LearningFragment onSuccess uvList:$uvList,videoList:$videoList")
        ThreadUtil.runOnMainThread(Runnable {
            if (uvList.isNotEmpty() && videoList.isNotEmpty()){
                this.uvList=uvList
                this.videoList=videoList
                bindView(uvList,videoList)
            }else{
                learningListNull.visibility= VISIBLE
            }
        })
    }

    private fun bindView(
        uvList: List<UVBean>,
        videoList: List<VideoBean>
    ) {
        val manager=LinearLayoutManager(context)
        val adapter= LearningListAdapter(uvList,videoList)
        manager.orientation=LinearLayoutManager.VERTICAL
        learningRecyclerView.layoutManager=manager
        learningRecyclerView.adapter=adapter
        learningRecyclerView.itemAnimator= DefaultItemAnimator()
    }

    override fun onResume() {
        super.onResume()
        uvList?.let { videoList?.let { it1 -> bindView(it, it1) } }
    }
}