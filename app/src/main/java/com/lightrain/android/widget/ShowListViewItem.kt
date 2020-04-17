package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.model.VideoBean
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StartActivityUtil
import kotlinx.android.synthetic.main.view_list_item.view.*

class ShowListViewItem :RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context,R.layout.view_list_item,this)
    }
    fun setData(status: Int, videoBean: VideoBean){
        when(status){
            ShowListView.STATUS_DEFAULT->{
                //默认界面
                listItemTip.visibility= View.GONE
            }
            ShowListView.STATUS_NEW->{
                //最新界面
                ImageUtil.loadImage(context,listItemTip,R.mipmap.ic_new_red)
            }
            ShowListView.STATUS_HOT->{
                //销量界面
                ImageUtil.loadImage(context,listItemTip,R.mipmap.ic_up_red)
            }
            ShowListView.STATUS_UPDATE->{
                //更新
                listItemTip.visibility= View.GONE
            }
        }
        bindView(videoBean)
        initListener(videoBean)
    }

    private fun bindView(videoBean: VideoBean) {
        ImageUtil.loadImageRoundedCornersDIY(context,listItemImage,videoBean.videoIcon,25,10)
        listItemTitle.text=videoBean.videoTitle
        listItemPeopleNum.text=videoBean.videoLearningNum.toString()
        listItemIsFree.text=if (videoBean.videoPrice==0f) "免费" else "￥${videoBean.videoPrice}"
    }

    private fun initListener(videoBean: VideoBean) {
        listItemBac.setOnClickListener {
            //跳转到视频详情界面
            StartActivityUtil.toVideoInfoActivity(context, videoBean)
        }
    }
}