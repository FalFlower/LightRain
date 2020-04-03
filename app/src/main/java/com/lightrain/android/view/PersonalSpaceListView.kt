package com.lightrain.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StringUtil
import kotlinx.android.synthetic.main.view_peronal_space_list.view.*

class PersonalSpaceListView :RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_peronal_space_list,this)
    }

    @SuppressLint("SetTextI18n")
    fun setData(url:String,title:String, num:Int, price:Double){
        ImageUtil.loadImageRoundedCornersDIY(context,personalSpaceListImage,url,25,5)
        personalSpaceListTitle.text=title
        personalSpaceListPeopleNum.text=num.toString()
        if (price==0.0)
            personalSpaceListIsFree.text="免费"
        else
            personalSpaceListIsFree.text="${StringUtil.PRICE_SYMBOL}$price"
    }
}