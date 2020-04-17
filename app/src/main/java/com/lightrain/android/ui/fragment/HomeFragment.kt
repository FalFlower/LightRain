package com.lightrain.android.ui.fragment

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.lightrain.android.R
import com.lightrain.android.adapter.ImageAdapter
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.model.VideoBean
import com.lightrain.android.model.test.LocalData
import com.lightrain.android.presenter.impl.HomePresenterImpl
import com.lightrain.android.widget.ShowListView
import com.lightrain.android.view.HomeView
import com.lightrain.android.widget.SearchBarView
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(),HomeView{
    private val presenter by lazy { HomePresenterImpl(this) }
    override fun intView(): View? {
        return View.inflate(context, R.layout.fragment_home,null)
    }

    override fun initData() {
        presenter.loadData()
        homeSearchBar.setData(SearchBarView.STAUS_HOME)
    }

    override fun initListener() {
        homeRefreshLayout.setOnRefreshListener{
            homeRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        }

        homeRefreshLayout.setOnLoadMoreListener{
            homeRefreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        }
    }

    /*
    * Presenter层数据回调处理
    * */
    override fun onError(msg: String?) {
        println("In HomeFragment error:$msg")
    }

    override fun onSuccess(status:Int,dataList:List<VideoBean>) {
        when(status){
            HomePresenterImpl.STATUS_BANNER->{
                setBanner(dataList)
            }
            HomePresenterImpl.STATUS_PAGER->{
                setTabLayout(dataList)
            }
        }
    }

    private fun setTabLayout(dataList: List<VideoBean>) {
        val strs= arrayOf("默认","最新","销量","更新")
        val viewList=getViewData(dataList) //获取对应pagerView

        homeViewPager.adapter=object : PagerAdapter(){
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view==`object`
            }

            override fun getCount(): Int {
                return viewList.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view=viewList[position]
                container.addView(view)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as ShowListView?)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return strs[position]
            }
        }
        homeTabLayout.setupWithViewPager(homeViewPager)
    }

    private fun getViewData(dataList: List<VideoBean>): List<ShowListView> {
        val viewList=ArrayList<ShowListView>()
        var view: ShowListView?
        val status= arrayOf(
            ShowListView.STATUS_DEFAULT,
            ShowListView.STATUS_NEW,
            ShowListView.STATUS_HOT,
            ShowListView.STATUS_UPDATE)
        for (s in status) {
            view= ShowListView(context)
//            view.setData(s,dataList)
            view.setData(s,LocalData.getVideoTestData())
            viewList.add(view)
        }
        return viewList
    }

    private fun setBanner(dataList: List<VideoBean>) {
        homeBanner.adapter=ImageAdapter(dataList)
        homeBanner.setIndicator(CircleIndicator(context),true)
            .setIndicatorSelectedColor(resources.getColor(R.color.white))
            .setIndicatorNormalColor(resources.getColor(R.color.colorLine))
        homeBanner.start()
    }




}