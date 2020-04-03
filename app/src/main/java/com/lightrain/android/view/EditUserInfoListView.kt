package com.lightrain.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import kotlinx.android.synthetic.main.view_edit_uer_info.view.*

class EditUserInfoListView :RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context, R.layout.view_edit_uer_info,this)
    }
    fun setData(title:String,subTitle:String){
        editUserInfoTitle.text=title
        editUserInfoSubtitle.text=subTitle
    }
}