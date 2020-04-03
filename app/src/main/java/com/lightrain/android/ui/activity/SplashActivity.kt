package com.lightrain.android.ui.activity

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity :BaseActivity(), ViewPropertyAnimatorListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        //动画处理
        ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f)
            .setListener(this).duration = 2000
    }

    override fun onAnimationEnd(view: View?) {
//进入主界面
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }

}