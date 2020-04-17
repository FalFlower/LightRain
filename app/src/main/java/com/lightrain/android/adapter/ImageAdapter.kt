package com.lightrain.android.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.model.VideoBean
import com.lightrain.android.ui.activity.VideoInfoActivity
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StartActivityUtil
import com.youth.banner.adapter.BannerAdapter


//dataList设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
class ImageAdapter(dataList: List<VideoBean>) :
    BannerAdapter<VideoBean, ImageAdapter.BannerViewHolder>(dataList) {

    private var context:Context?=null

    class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        context=parent?.context
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.setPadding(40,0,40,0)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: VideoBean?, position: Int, size: Int) {
        if (context!=null&&holder!=null&&data!=null){
            ImageUtil.loadImageRoundedCornersDIY(context!!,holder.imageView,data.videoIcon,25,10)
            holder.imageView.setOnClickListener {
                //跳转到视频详情界面
                StartActivityUtil.toVideoInfoActivity(context!!,data)
            }
        }
    }
}