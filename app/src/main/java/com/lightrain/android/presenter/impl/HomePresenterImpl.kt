package com.lightrain.android.presenter.impl

import com.lightrain.android.model.test.LocalData
import com.lightrain.android.presenter.interf.HomePresenter
import com.lightrain.android.view.HomeView

/*
* 首页目前全都设置静态数据，所以不需要根据网络请求进行分类处理
* */
class HomePresenterImpl(var homeView:HomeView) :HomePresenter{
    companion object{
        val STATUS_BANNER=1
        val STATUS_PAGER=2
    }
    override fun loadData() {
        homeView.onSuccess(STATUS_BANNER, LocalData.getBannerData())
        homeView.onSuccess(STATUS_PAGER, LocalData.getVideoTestData())
    }

}