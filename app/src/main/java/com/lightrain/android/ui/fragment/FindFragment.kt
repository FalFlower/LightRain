package com.lightrain.android.ui.fragment

import android.view.View
import com.lightrain.android.R
import com.lightrain.android.base.BaseFragment

class FindFragment :BaseFragment(){
    override fun intView(): View? {
        return View.inflate(context,R.layout.fragment_find,null)
    }


}