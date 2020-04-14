package com.lightrain.android.ui.activity

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.widget.UserRelationshipView
import kotlinx.android.synthetic.main.activity_user_relationship.*

class UserRelationshipActivity :BaseActivity() {
    private val list=ArrayList<UserRelationshipView>()
    private val strs = arrayOf("关注", "粉丝")
    override fun getLayoutId(): Int {
        return R.layout.activity_user_relationship
    }

    override fun initData() {
        val fansView=UserRelationshipView(this)
        val followView=UserRelationshipView(this)
        fansView.setData(UserRelationshipView.LIST_FANS)
        followView.setData(UserRelationshipView.LIST_FOLLOWS)
        list.add(fansView)
        list.add(followView)


        urActivityViewPager.adapter=object :PagerAdapter(){
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view==`object`
            }

            override fun getCount(): Int {
                return list.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view=list[position]
                container.addView(view)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as UserRelationshipView?)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return strs[position]
            }
        }
        urActivityTabLayout.setupWithViewPager(urActivityViewPager)
    }

    override fun initListener() {
        urActivityBack.setOnClickListener { finish() }
        //urActivityTabLayout.addOnTabSelectedListener(object:TabLayout.BaseOnTabSelectedListener{})
    }


}