package com.lightrain.android.base

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.lightrain.android.R

interface ToolbarManager {
    val toolbar: Toolbar

    //初始化MainActivity中的ToolBar
    fun initMainToolbar(){
        //toolbar.setTitle()
//        toolbar.inflateMenu(R.menu.main)
//        //如果java接口中只有一个未实现的方法，可以省略接口的对象 直接用{}来表示这个方法 item为捕捉到的参数（默认为it）
//        toolbar.setOnMenuItemClickListener { item ->
//            when(item?.itemId){
//                //跳转到设置界面
//                R.id.setting->{
//                    //toolbar.context.startActivity(Intent(toolbar.context,SettingActivity::class.java))
//                }
//            }
//            true
//        }
    }

    fun initSettingToolBar(){
        toolbar.setTitle("设置界面")
    }
}