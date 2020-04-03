package com.lightrain.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

abstract class BaseFragment : Fragment(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return intView()
    }
    //初始化
    open fun init() {

    }
    //获取布局View
    abstract fun intView(): View?

    //初始化数据
    protected open fun initData() {

    }

    //adapter Listener
    protected open fun initListener() {

    }

    protected fun myToast(msg:String) {
        context?.runOnUiThread {
            toast(msg)
        }
    }
}