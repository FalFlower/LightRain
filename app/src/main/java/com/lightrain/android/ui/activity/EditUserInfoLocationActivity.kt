package com.lightrain.android.ui.activity

import android.content.Context
import android.view.KeyEvent
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity

class EditUserInfoLocationActivity :BaseActivity() {
    companion object{
        var app:EditUserInfoLocationActivity?=null
    }
    override fun getLayoutId(): Int {
        app=this
        return R.layout.activity_edit_user_info_location
    }
    //禁用返回键
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }


}