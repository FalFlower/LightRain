package com.lightrain.android.util

import android.R.color
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import com.lightrain.android.base.BaseActivity


object WindowsUtil {
    //安卓5.0+
    fun setSystemUiVisibility(activity: BaseActivity,statusColor:Int) {
        val window = activity.window
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        window.statusBarColor = statusColor
        //设置系统状态栏处于可见状态
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        //让view不根据系统窗口来调整自己的布局
        val mContentView =
            window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false)
            ViewCompat.requestApplyInsets(mChildView)
        }
        if (ColorUtils.calculateLuminance(statusColor) >= 0.5) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
    }

    fun setNullSystemBar(activity: BaseActivity,view:View,statusColor:Int){
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val params: ViewGroup.LayoutParams = view.getLayoutParams()
//            val topMargin: Int = getStatusBarHeight(view.getContext())
//            params.height = params.height + topMargin
//            view.setPadding(
//                view.paddingLeft,
//                view.paddingTop + topMargin,
//                view.paddingRight,
//                view.paddingBottom
//            )
//            view.layoutParams = params
//        }
        if (ColorUtils.calculateLuminance(statusColor) >= 0.5) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
    }
    /*
    * 获取状态栏高度
    * */
    fun getStatusBarHeight(context: Context):Int{
        var statusBarHeight=0
        val resourceId=context.resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


}