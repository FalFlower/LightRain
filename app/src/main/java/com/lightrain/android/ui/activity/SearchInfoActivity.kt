package com.lightrain.android.ui.activity

import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.ListViewAdapter
import com.lightrain.android.adapter.SearchInfoAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.VideoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.StartActivityUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.widget.ShowListView
import com.lightrain.android.widget.ShowListViewItem
import kotlinx.android.synthetic.main.activity_search_info.*
import org.jetbrains.anko.startActivity

class SearchInfoActivity :BaseActivity(), ResponseHandler {
    var title=""
    var label=""
    override fun getLayoutId(): Int {
        return R.layout.activity_search_info
    }

    override fun initData() {
        title= StartActivityUtil.getTitleInSearchInfoActivity(intent).toString()
        label= StartActivityUtil.getLabelInSearchInfoActivity(intent).toString()
        searchInfoTitle.text=title
        getData()
    }

    private fun getData() {
        HttpManager.manager.sendRequestByGet(URLProviderUtils.videoMatch(label),ResponseStatus.MATCH,this)
    }

    override fun initListener() {
        searchInfoBack.setOnClickListener { finish() }
        searchInfoSearch.setOnClickListener {
            //跳转到搜索界面
            startActivity<SearchActivity>()
        }
        searchInfoCart.setOnClickListener {
            //跳转到购物车界面
            startActivity<CartActivity>()
        }
    }

    override fun onError(msg: String?) {
        println("SearchInfoActivity error:$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        runOnUiThread {
            result?.let {
                val list=JsonUtil.getArrayResultResponse(it,VideoBean::class.java).data
                if (list?.isNotEmpty()!!){
                    searchInfoListNull.visibility=GONE
                    initRecycleView(list)
                }


            }
        }
    }

    private fun initRecycleView(list: List<VideoBean>?) {
        val manager=LinearLayoutManager(this)
        manager.orientation=LinearLayoutManager.VERTICAL
        searchInfoRecyclerView.layoutManager=manager
        //val adapter= list?.let { SearchInfoAdapter(this, it) }
        val adapter= list?.let { ListViewAdapter(ShowListView.STATUS_DEFAULT, it) }
        searchInfoRecyclerView.adapter=adapter
    }
}