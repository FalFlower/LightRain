package com.lightrain.android.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.ColorUtils
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.lightrain.android.base.BaseActivity
import kotlin.math.ceil
import kotlin.math.roundToInt


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

}