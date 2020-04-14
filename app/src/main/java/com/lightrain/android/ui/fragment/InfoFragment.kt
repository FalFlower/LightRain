package com.lightrain.android.ui.fragment

import android.view.View
import com.lightrain.android.R
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.ui.activity.SettingActivity
import com.lightrain.android.widget.InfoLoginView
import kotlinx.android.synthetic.main.fragment_info.*
import org.jetbrains.anko.support.v4.startActivity

class InfoFragment : BaseFragment() {
    override fun intView(): View? {
        return View.inflate(context, R.layout.fragment_info,null)
    }

    override fun onResume() {
        super.onResume()
        infoLoginView.removeAllViews()
        infoLoginView.addView(InfoLoginView(context))

    }

    override fun initData() {
        initTitle()
    }

    override fun initListener() {
        infoScan.setOnClickListener { }//扫描
        infoMoon.setOnClickListener {  }//夜间模式

        infoLucky.setOnClickListener {  }//积分抽奖
        infoCart.setOnClickListener {  }//购物车
        infoOrder.setOnClickListener {  }//我的订单
        infoBalance.setOnClickListener {  }//我的余额
        infoExchangeCode.setOnClickListener {  }//电子兑换码
        infoCoupon.setOnClickListener {  }//优惠券
        infoFeedback.setOnClickListener {  }//意见反馈
        infoProblem.setOnClickListener {  }//常见问题
        infoSetting.setOnClickListener { startActivity<SettingActivity>() }//设置 跳转到设置界面
    }

    private fun initTitle() {
        infoLucky.setTitle("积分抽奖","好奖随心选")
        infoCart.setTitle("购物车","")
        infoOrder.setTitle("我的订单","")
        infoBalance.setTitle("我的余额","0.0")
        infoExchangeCode.setTitle("电子兑换码","")
        infoCoupon.setTitle("优惠券","")
        infoFeedback.setTitle("意见反馈","")
        infoProblem.setTitle("常见问题","")
        infoSetting.setTitle("设置","")
    }



}