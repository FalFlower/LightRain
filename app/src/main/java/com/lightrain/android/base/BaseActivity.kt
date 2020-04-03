package com.lightrain.android.base

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lightrain.android.util.WindowsUtil
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

abstract class BaseActivity :AppCompatActivity(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //WindowsUtil.setSystemUiVisibility(this)
        WindowsUtil.setSystemUiVisibility(this,Color.WHITE)
        initListener()
        initData()
    }
    //初始化数据
    protected open fun initData() {

    }

    //adapter Listener
    protected open fun initListener() {

    }

    abstract fun getLayoutId(): Int //获取布局Id

    protected fun myToast(msg:String){
        runOnUiThread{
            toast(msg)
        }
    }
    //开启activity并且finish当前界面   reified关键字是用来限定泛型T的类型。还需要在前面加上inline内联支持
    inline fun <reified T: Activity>startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}