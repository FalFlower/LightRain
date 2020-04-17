package com.lightrain.android.ui.activity

import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity

class SearchActivity :BaseActivity() {
    companion object{
        var app:SearchActivity?=null
    }
    override fun getLayoutId(): Int {
        app=this
        return R.layout.activity_search
    }

    override fun initData() {

    }

    override fun initListener() {


    }
}