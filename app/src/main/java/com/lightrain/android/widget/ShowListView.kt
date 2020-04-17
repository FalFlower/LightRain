package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.ListViewAdapter
import com.lightrain.android.model.VideoBean
import kotlinx.android.synthetic.main.view_list.view.*
import kotlinx.android.synthetic.main.view_list_item.view.*

class ShowListView :RelativeLayout {
    companion object{
        val STATUS_DEFAULT=0
        val STATUS_NEW=1
        val STATUS_HOT=2
        val STATUS_UPDATE=3
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_list,this)
    }



    fun setData(status:Int,dataList:List<VideoBean>){
        val manager= LinearLayoutManager(context)
        manager.orientation= LinearLayoutManager.VERTICAL
        listViewRecyclerView.layoutManager=manager
        listViewRecyclerView.adapter=ListViewAdapter(status,dataList)
        listViewRecyclerView.itemAnimator= DefaultItemAnimator()
    }
}