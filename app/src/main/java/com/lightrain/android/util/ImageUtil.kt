package com.lightrain.android.util

import android.R.attr.radius
import android.content.Context
import android.graphics.BlurMaskFilter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lightrain.android.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


object ImageUtil {
    fun loadImage(context:Context,imageView:ImageView,url:String){
        Glide.with(context)
            .load(url)
            .into(imageView);
    }
    fun loadImage(context:Context,imageView:ImageView,id:Int){
        Glide.with(context)
            .load(id)
            .into(imageView);
    }

    fun loadImageRoundedCorners(context:Context,imageView:ImageView,url:String){
        Glide.with(context)
            .load(url)
            //.placeholder(R.drawable.img_loading) //设置等待时的图片【这个时候需要注释，否则这个会作为背景图】
            .error(R.mipmap.ic_login_no)//设置加载失败后的图片显示
            .centerCrop()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
            .priority(Priority.HIGH)//设置图片加载的优先级
            .circleCrop()//圆形
            .into(imageView);
    }
    fun loadImageRoundedCorners(context:Context,imageView:ImageView,id: Int){
        Glide.with(context)
            .load(id)
            //.placeholder(R.drawable.img_loading)//设置等待时的图片【这个时候需要注释，否则这个会作为背景图】
            .error(R.mipmap.ic_login_no)//设置加载失败后的图片显示
            .centerCrop()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
            .priority(Priority.HIGH) //设置图片加载的优先级
            .circleCrop() //圆形
            .into(imageView);
    }
    //实现高斯模糊
    fun loadImageBlurTransformation(context:Context,imageView:ImageView,url:String,radius:Int) {
        Glide.with(context)
            .load(url)
            .transform(BlurTransformation(radius)) // 模糊效果
            .into(imageView)
    }

    fun loadImageBlurTransformation(context:Context,imageView:ImageView,id: Int,radius:Int){
        Glide.with(context)
            .load(id)
            .transform(BlurTransformation(radius))// 模糊效果
            .into(imageView)
    }
    //加载椭圆（自定义圆角）
    fun loadImageRoundedCornersDIY(context:Context,imageView:ImageView,url: String,radius: Int,margin:Int){
        Glide.with(context)
            .load(url)
            // 圆角效果+模糊效果
            .transform(RoundedCornersTransformation(radius, margin))
            .into(imageView)
    }

    fun loadImageRoundedCornersDIY(context:Context,imageView:ImageView,id: Int,radius: Int,margin:Int){
        Glide.with(context)
            .load(id)
            // 圆角效果+模糊效果
            .transform(RoundedCornersTransformation(radius, margin))
            .into(imageView)
    }

}