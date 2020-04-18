package com.lightrain.android.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.GridLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.SearchHistoryAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.util.StartActivityUtil
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.apply

class SearchActivity :BaseActivity() {
    companion object{
        var app:SearchActivity?=null
    }
    override fun getLayoutId(): Int {
        app=this
        return R.layout.activity_search
    }

    override fun initData() {
        if (getLocalData()?.isNotEmpty()!!){
            searchHistoryLayout.visibility= VISIBLE
            searchHistoryRecyclerView.visibility= VISIBLE
            getLocalData()?.let { initRecycleView(it) }
        }
    }

    private fun initRecycleView(localData: String) {
        val list=localData.split(";")
        val adapter=SearchHistoryAdapter(this,list)
        val manager= GridLayoutManager(this,4)
        searchHistoryRecyclerView.layoutManager=manager
        searchHistoryRecyclerView.adapter=adapter
        adapter.notifyDataSetChanged()
    }

    override fun initListener() {
        searchHistoryBack.setOnClickListener { finish() }
        searchHistorySearch.setOnClickListener {
            if (searchHistoryEdit.text.isNotEmpty()){
                //将历史存储到本地
                loadLocalData(searchHistoryEdit.text.toString())
                //跳转到搜索详情界面
                StartActivityUtil.toSearchInfoActivity(this,searchHistoryEdit.text.toString(),searchHistoryEdit.text.toString())
                finish()
            }
        }
        if (getLocalData()?.isNotEmpty()!!){
            searchHistoryClean.setOnClickListener {
                //清空数据
                cleanLocalData()
                //隐藏界面
                searchHistoryLayout.visibility= GONE
                searchHistoryRecyclerView.visibility= GONE
            }
        }
    }

    private fun loadLocalData(data: String) {
        var newData=data
        val history=this.getSharedPreferences("search_history", Context.MODE_PRIVATE)
        val editor=history.edit()
        val oldData=getLocalData()
        if (oldData?.isNotEmpty()!!){
            newData="$oldData;$newData"
        }
        editor.putString("history_data",newData)
        editor.apply() //提交修改
    }

    private fun getLocalData(): String? {
        val data= getSharedPreferences("search_history", Context.MODE_PRIVATE)
        return data.getString("history_data","")
    }

    private fun cleanLocalData(){
        val history=this.getSharedPreferences("search_history", Context.MODE_PRIVATE)
        val editor=history.edit()
        editor.clear()
        editor.apply()
    }
}