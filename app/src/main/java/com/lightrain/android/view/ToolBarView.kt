package com.lightrain.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolBarView :RelativeLayout {
    var app:BaseActivity?=null
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context,R.layout.view_toolbar,this)
        toolbarBack.setOnClickListener { app?.finish() }  //点击
    }
    //必须设置以下两个方法！！
    fun initData(activity:BaseActivity) {
        app=activity
    }

    fun setTitle(title:String){
        toolbarTitle.text=title
    }
}