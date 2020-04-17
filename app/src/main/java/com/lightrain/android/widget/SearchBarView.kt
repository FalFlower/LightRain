package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.ui.activity.SearchActivity
import com.lightrain.android.util.ImageUtil
import kotlinx.android.synthetic.main.view_search_bar.view.*
import org.jetbrains.anko.startActivity

class SearchBarView :RelativeLayout{
    companion object{
        val STAUS_HOME=0
        val STATUS_CLASSIFY=1
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_search_bar,this)
        searchBar.setOnClickListener {
            //转到搜索界面
           context.startActivity<SearchActivity>()
        }
    }

    fun setData(status:Int){
        searchBarTitle.text
        searchBarPoint
        when(status){
            STAUS_HOME->{
                //转到邮件界面
                ImageUtil.loadImage(context,searchBarMessage,R.mipmap.ic_message)
                searchBarMessage.setOnClickListener {}
            }
            STATUS_CLASSIFY->{
                //转到购物车
                ImageUtil.loadImage(context,searchBarMessage,R.mipmap.ic_cart)
                searchBarMessage.setOnClickListener {}
            }
        }

    }
}