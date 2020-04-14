package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import kotlinx.android.synthetic.main.view_currency_item.view.*
/**
 * 用来复用的view（一个标题+右箭头）
 * */
class CurrencyItemView:RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context,R.layout.view_currency_item,this)
    }
    //设置View正、负标题
    fun setTitle(title:String,subTitle:String){
        currencyTitle.text=title
        if (subTitle.isEmpty())
            currencySubTitle.visibility= View.INVISIBLE
        else
            currencySubTitle.text=subTitle
    }
    //设置View负标题
    fun setSubTitle(string: String){
        currencySubTitle.text=string
    }

    fun hindLine(){
        currencyLine.visibility= View.GONE
    }
}