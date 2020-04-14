package com.lightrain.android.util

import com.lightrain.android.R
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.ui.fragment.ClassificationFragment
import com.lightrain.android.ui.fragment.HomeFragment
import com.lightrain.android.ui.fragment.InfoFragment
import com.lightrain.android.ui.fragment.LearningFragment

class FragmentUtil private constructor(){

    companion object{//由于私有化了构造方法，所以要获取FragmentUtil只能通过fragmentUtil且只会构建一次
        val fragmentUtil by lazy { FragmentUtil() }
    }
    private val homeFragment by lazy { HomeFragment() }
    private val findFragment by lazy { ClassificationFragment() }
    private val learningFragment by lazy { LearningFragment() }
    private val infoFragment by lazy { InfoFragment() }

    //根据TabId获取对应的Fragment
    fun getFragment(tabId:Int): BaseFragment?{
        when(tabId){
            R.id.tab_home-> return homeFragment
            R.id.tab_classification->return findFragment
            R.id.tab_learning->return learningFragment
            R.id.tab_info->return infoFragment
        }
        return null
    }

}