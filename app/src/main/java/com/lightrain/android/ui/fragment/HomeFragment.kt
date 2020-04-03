package com.lightrain.android.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.lightrain.android.R
import com.lightrain.android.base.BaseFragment

class HomeFragment : BaseFragment(){
    override fun intView(): View? {
//        val tv=TextView(context)
//        tv.gravity=Gravity.CENTER
//        tv.setTextColor(Color.RED)
//        tv.text=javaClass.simpleName
//        return tv
        return View.inflate(context, R.layout.fragment_home,null)
    }


}