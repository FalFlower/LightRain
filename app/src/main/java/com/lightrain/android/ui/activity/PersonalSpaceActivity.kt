package com.lightrain.android.ui.activity

import android.graphics.Color
import android.view.WindowManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.adapter.PersonalSpaceAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.TestData
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.UserInfoUtil
import com.lightrain.android.util.WindowsUtil
import kotlinx.android.synthetic.main.activity_personal_space.*

class PersonalSpaceActivity :BaseActivity() {
    var userInfo:UserInfoBean?=null
    var adapter:PersonalSpaceAdapter?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_personal_space
    }

    override fun initData() {
        //val list= ArrayList<ClassBean>()
        userInfo=LightRainApplication.userInfoBean
        adapter=PersonalSpaceAdapter(TestData.getTestClassBean())
        initView()
    }

    private fun initView() {
        //加载用户模块
        userInfo?.let{
            ImageUtil.loadImageBlurTransformation(this, personalSpaceIconBac, it.userIcon,80)
            ImageUtil.loadImageRoundedCorners(this, personalSpaceIcon,it.userIcon)
            //personalSpaceNickName.text=it.nickname
            if (it.userGender==UserInfoUtil.USER_GENDER_MAN)
                ImageUtil.loadImage(this,personalSpaceGender,R.mipmap.ic_gender_man)
            else
                ImageUtil.loadImage(this,personalSpaceGender,R.mipmap.ic_gender_woman)
            //加载签字（需要后台更改）  personalSpaceSign
            //personalSpaceFollowNum.text
            //personalSpaceFansNum.text
            //personalSpaceIntegralNum.text

            personalSpaceToolbar.title=it.nickname
            //设置toolbar
            setSupportActionBar(personalSpaceToolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            personalSpaceToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_nobar)
            personalSpaceToolbar.setNavigationOnClickListener {
                finish()
            }
        }
        //设置recycleView
        val manager=LinearLayoutManager(this)
        manager.orientation= LinearLayoutManager.VERTICAL
        personalSpaceRecyclerView.layoutManager=manager
        personalSpaceRecyclerView.adapter=adapter
        personalSpaceRecyclerView.itemAnimator=DefaultItemAnimator()
    }


}